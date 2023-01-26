package edu.spring.td1.controller

import edu.spring.td1.models.Item
import edu.spring.td1.services.UIMessage
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
    fun indexAction(@RequestAttribute("msg") msg:UIMessage.Message?):String{
        return "index"
    }

    @GetMapping("/new")
    fun newAction():String{
        return "newForm"
    }

    @PostMapping("/addNew")
    fun addNewAction(@ModelAttribute("nom")nom:String, @SessionAttribute("items")items: HashSet<Item>, attrs:RedirectAttributes): RedirectView {
        if(items.add(Item(nom))){
            attrs.addFlashAttribute("msg",UIMessage.message("Ajout d'item","$nom ajouté dans les items"))
        } else {
            attrs.addFlashAttribute("msg",UIMessage.message("Ajout d'item","$nom est déjà dans la liste,<br>Il n'a pas été ajouté","warning","warning circle"))
        }
        return RedirectView("/")
    }

}