package edu.spring.dogs.controllers

import edu.spring.dogs.entities.Dog
import edu.spring.dogs.entities.Master
import edu.spring.dogs.repositories.DogRepository
import edu.spring.dogs.repositories.MasterRepository
import edu.spring.dogs.repositories.ToyRepository
import edu.spring.dogs.services.UIMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/")
class MainController {

    @Autowired
    lateinit var dogRepository: DogRepository

    @Autowired
    lateinit var masterRepository: MasterRepository

    @Autowired
    lateinit var toyRepository: ToyRepository

    @RequestMapping(path = ["", "/"])
    fun index(model: ModelMap, attrs: RedirectAttributes): String {
        val dogs = dogRepository.findAll()
        val masters = masterRepository.findAll()
        val toys = toyRepository.findAll()
        model["dogs"] = dogs
        model["masters"] = masters
        model["toys"] = toys

        attrs.addFlashAttribute("msg", UIMessage.message("Pas de chien", "Il n'y a pas de chien à l'adoption dans la base de données."))

        return "index"
    }

    @PostMapping("/master/add")
    fun addMaster(@ModelAttribute("firstname") firstname: String,@ModelAttribute("lastname") lastname: String): RedirectView {
        masterRepository.save(Master(firstname, lastname))
        return RedirectView("/")
    }

    @PostMapping("/master/{id}/dog")
    fun masterAction(@PathVariable id: Int, @ModelAttribute("dogname") dogname: String, @RequestParam("dog-action") dogAction: String): RedirectView {
        val master = masterRepository.findById(id).get()
        var dog : Dog? = dogRepository.findByNameAndMasterId(dogname, id)
        when (dogAction) {
            "add" -> {
                if(dog == null) {
                    dog = Dog(dogname)
                    master.addDog(dog)
                }
            }
            "give-up" -> {
                if(dog != null) {
                    master.giveUpDog(dog)
                }
            }
        }
        if (dog != null) {
            dogRepository.save(dog)
            masterRepository.save(master)
        }
        return RedirectView("/")
    }

    @GetMapping("/master/{id}/delete")
    fun deleteMaster(@PathVariable id: Int): RedirectView {
        masterRepository.deleteById(id)
        return RedirectView("/")
    }

    @PostMapping("/dog/{id}/action")
    fun dogAction(@PathVariable id: Int, @ModelAttribute("dogname") dogname: String, @RequestParam("dog-action") dogAction: String): RedirectView {
        val master = masterRepository.findByDogsName(dogname).firstOrNull()
        val dog = dogRepository.findById(id).orElse(null)
        if (dog != null) {
            when (dogAction) {
                "adopt" -> {
                    dog.master = master
                    dogRepository.save(dog)
                }
                "remove" -> {
                    master?.giveUpDog(dog)
                    dog.master = null
                    dogRepository.deleteById(id)
                }
            }
        }
        return RedirectView("/")
    }

}