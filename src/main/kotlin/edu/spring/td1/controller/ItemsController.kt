package edu.spring.td1.controller

import edu.spring.td1.models.Item
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView


@Controller
@SessionAttributes("items")
class ItemsController {

    @get:ModelAttribute("items")
    val items: Set<Item>
        get() {
            var items=HashSet<Item>()
            items.add(Item("itm"))
            return items
        }

    @RequestMapping("/")
    fun indexAction():String{
        return "index"
    }

    @GetMapping("/new")
    fun newAction():String{
        return "newForm"
    }

    @PostMapping("/addNew")
    fun addNewAction(@ModelAttribute("nom")nom:String, @SessionAttribute("items")items: HashSet<Item>): RedirectView {
        items.add(Item(nom))
        return RedirectView("/")
    }

}