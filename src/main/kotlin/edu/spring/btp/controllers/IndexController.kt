package edu.spring.btp.controllers

import edu.spring.btp.entities.Complaint
import edu.spring.btp.repositories.ComplaintRepository
import edu.spring.btp.repositories.DomainRepository
import edu.spring.btp.repositories.ProviderRepository
import edu.spring.btp.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

@Controller
class IndexController {

    @Autowired
    lateinit var domainRepository: DomainRepository

    @Autowired
    lateinit var complaintRepository: ComplaintRepository

    @Autowired
    lateinit var providerRepository: ProviderRepository

    @Autowired
    lateinit var userRepository: UserRepository

    var authentication: UserDetails? = null

    @RequestMapping(path = ["/", "/index", ""])
    fun index(model: ModelMap, @AuthenticationPrincipal auth: UserDetails?): String {
        val domain = domainRepository.findByName("Root")
        model["domain"] = domain
        model["children"] = domainRepository.findByParentName("Root")
        model["user"] = auth
        if(auth != null) authentication = auth!!
        return "index"
    }

    @GetMapping("/domain/{name}")
    fun getDomainsByParentName(@PathVariable name: String, model: ModelMap): String{
        val domain = domainRepository.findByName(name)
        model["domain"] = domain
        model["children"] = domainRepository.findByParentName(name)
        if(authentication != null) model["user"] = authentication
        return "index"
    }


    @GetMapping("complaints/{domain}")
    fun getComplaintsByDomain(@PathVariable domain: String, model: ModelMap): String{
        val domain = domainRepository.findByName(domain)
        model["domain"] = domain
        model["complaints"] = domain.complaints
        model["user"] = authentication
        return "complaints"
    }

    @GetMapping("complaints/{domain}/sub")
    fun getComplaintsAndChildByDomain(@PathVariable domain: String, model: ModelMap): String{
        val domain = domainRepository.findByName(domain)
        model["domain"] = domain
        model["user"] = authentication
        val complaints = domain.complaints
        val children = domain.children
        for(child in children){
            complaints.addAll(child.complaints)
        }
        model["complaints"] = complaints
        return "complaints"
    }

    @GetMapping("complaints/{domain}/new")
    fun addNewComplaint(@PathVariable domain: String, model: ModelMap): String {
        val dom = domainRepository.findByName(domain)
        model["domain"] = dom
        model["providers"] = dom.providers
        model["user"] = authentication
        return "forms/complaint"
    }

    @PostMapping("complaints/{domain}/new")
    fun addNewComplaint(@PathVariable domain: String, model: ModelMap, @ModelAttribute("title") title: String,
                        @ModelAttribute("description") description: String,
                        @ModelAttribute("provider") provider: Int): RedirectView {
        model["user"] = authentication
        val dom = domainRepository.findByName(domain)
        val complaint = Complaint(title, description, userRepository.findByUsernameOrEmail(authentication?.username),
                providerRepository.getReferenceById(provider), dom)
        complaintRepository.save(complaint)
        return RedirectView("/complaints/$domain")
    }


}