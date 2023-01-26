package edu.spring.td1.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class ItemsController {

    @RequestMapping("/")
    fun indexAction():String{
        return "index"
    }

}