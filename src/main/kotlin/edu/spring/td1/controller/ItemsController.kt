package edu.spring.td1.controller

import edu.spring.td1.models.Categorie
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

    @get:ModelAttribute("categories")
    val cat: Set<Categorie>
        get(){
            var categories= HashSet<Categorie>()
            categories.add(Categorie("Amis"))
            categories.add(Categorie("Famille"))
            categories.add(Categorie("Professionnels"))
            return categories
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

    @GetMapping("/inc/{nom}")
    fun incrementAction(@ModelAttribute("nom")nom:String, @SessionAttribute("items")items: HashSet<Item>): RedirectView{
        var item = items.find{it.nom == nom}
        item?.evaluation = item?.evaluation!! + 1
        return RedirectView("/")
    }

    @GetMapping("/dec/{nom}")
    fun decrementAction(@ModelAttribute("nom")nom:String, @SessionAttribute("items")items: HashSet<Item>): RedirectView{
        var item = items.find{it.nom == nom}
        if(item?.evaluation!! >0) {
            item?.evaluation = item?.evaluation!! - 1
        }
        return RedirectView("/")
    }

    @GetMapping("/items/delete/{nom}")
    fun deleteAction(@PathVariable("nom")nom:String, @SessionAttribute("items")items: HashSet<Item>): RedirectView{
        items.removeIf {it.nom ==nom}
        return RedirectView(("/"))
    }

    @GetMapping("/items/edit/{nom}")
    fun modifyAction(@PathVariable("nom")nom:String):String{
        return "edit"
    }

    @PostMapping("/editItem/{nom}")
    fun editItemAction(@ModelAttribute("name")nom:String, @PathVariable("nom")oldNom:String,@SessionAttribute("items")items: HashSet<Item>, attrs:RedirectAttributes): RedirectView{

        var item = items.find{it.nom==oldNom}
        if (item != null) {
            item.nom=nom
        }

        attrs.addFlashAttribute("msg",UIMessage.message("Modification d'item","$oldNom modifié en $nom dans les items"))

        return RedirectView("/")
    }

}