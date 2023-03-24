package edu.spring.btp.controllers

import edu.spring.btp.repositories.ComplaintRepository
import edu.spring.btp.repositories.DomainRepository
import edu.spring.btp.repositories.ProviderRepository
import edu.spring.btp.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

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

    @RequestMapping(path = ["/", "/index", ""])
    fun index(model: ModelMap): String {
        val domain = domainRepository.findByName("Root")
        model["domain"] = domain
        model["children"] = domainRepository.findByParentName("Root")
        model["complaints"] = complaintRepository.findAll()
        return "index"
    }

    @GetMapping("/domain/{name}")
    fun getDomainsByParentName(@PathVariable name: String) = domainRepository.findByParentName(name)


    @GetMapping("complaints/{domain}")
    fun getComplaintsByDomain(@PathVariable domain: String){

    }

    @GetMapping("complaints/{domain}/sub")
    fun getComplaintsAndChildByDomain(@PathVariable domain: String){

    }

    @GetMapping("complaints/{domain}/new")
    fun addNewComplaint(@PathVariable domain: String){

    }
}