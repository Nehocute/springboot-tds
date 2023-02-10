package edu.spring.td2.controller

import edu.spring.td2.repositories.OrgaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/orgas")
class OrgaController {

    @Autowired
    lateinit var orgaRepository:OrgaRepository
    @RequestMapping(path = ["","/index", "/"])
    fun indexAction(model:ModelMap):String{
        model["orgas"]=orgaRepository.findAll()
        return "orgas/index"
    }
}