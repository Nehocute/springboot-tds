package edu.spring.td1.controller

import edu.spring.td1.models.Item
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.SessionAttributes


@Controller
@SessionAttributes("items")
class ItemsController {

    @get:ModelAttribute("items")
    val items: Set<Item>
        get() {
            var items=HashSet<Item>()
            items.add(Item("item"))
            return items
        }



    @RequestMapping("/")
    fun indexAction():String{
        return "index"
    }

}