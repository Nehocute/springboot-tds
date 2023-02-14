package edu.spring.td2.controller

import edu.spring.td2.entities.Organization
import edu.spring.td2.entities.User
import edu.spring.td2.exceptions.ElementNotFoundException
import edu.spring.td2.repositories.OrgaRepository
import edu.spring.td2.services.OrgaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/orgas")
class OrgaController {

    @Autowired
    lateinit var orgaRepository:OrgaRepository

    @Autowired
    lateinit var orgaService: OrgaService

    @RequestMapping(path = ["","/index", "/"])
    fun indexAction(model:ModelMap):String{
        model["orgas"]=orgaRepository.findAll()
        return "orgas/index"
    }
    @GetMapping("/new")
    fun newAction():ModelAndView{
        val mv = ModelAndView("/orgas/form")
        mv.addObject("orga",Organization(""))
        return mv
    }

    @PostMapping("/new")
    fun newSubmitAction(@ModelAttribute orga:Organization, @ModelAttribute("users") users:String):RedirectView{
        orgaService.addUsersToOrga(orga, users)
        orgaRepository.save(orga)
        return RedirectView("/orgas")
    }

    @GetMapping("/display/{id}")
    fun displayAction(@PathVariable("id") id:Int, model:ModelMap):String {
        val option = orgaRepository.findById(id)
        if (option.isPresent) {
            model["orga"] = option.get()
            return "/orgas/display"
        } else {
            throw ElementNotFoundException("Organisation $id non trouvable")
        }
    }

    @GetMapping("/delete/{id}")
    fun deleteAction(@PathVariable("id") id:Int):RedirectView{
        val option = orgaRepository.findById(id)
        if (option.isPresent) {
            orgaRepository.delete(option.get())
        } else {
            throw ElementNotFoundException("Organisation $id non trouvable")
        }
        return RedirectView("/orgas")
    }

    @GetMapping("/edit/{id}")
    fun editAction(@PathVariable("id") id:Int, model:ModelMap):String{
        val option = orgaRepository.findById(id)
        if (option.isPresent) {
            model["orga"] = option.get()
            return "/orgas/form"
        } else {
            throw ElementNotFoundException("Organisation $id non trouvable")
        }
    }




}