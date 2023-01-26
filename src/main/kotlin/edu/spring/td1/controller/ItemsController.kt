package edu.spring.td1.controller

import edu.spring.td1.models.Item
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
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
    fun indexAction(@RequestAttribute("msg") msg:String?):String{
        return "index"
    }

    @GetMapping("/new")
    fun newAction():String{
        return "newForm"
    }

    @PostMapping("/addNew")
    fun addNewAction(@ModelAttribute("nom")nom:String, @SessionAttribute("items")items: HashSet<Item>, attrs:RedirectAttributes): RedirectView {
        items.add(Item(nom))
        attrs.addFlashAttribute("msg","$nom ajouté dans les items")
        return RedirectView("/")
    }

}