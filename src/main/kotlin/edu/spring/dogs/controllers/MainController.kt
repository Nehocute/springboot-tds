package edu.spring.dogs.controllers

import edu.spring.dogs.entities.Dog
import edu.spring.dogs.entities.Master
import edu.spring.dogs.repositories.DogRepository
import edu.spring.dogs.repositories.MasterRepository
import edu.spring.dogs.repositories.ToyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
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
    fun index(model: ModelMap): String {
        model["dogs"] = dogRepository.findAll()
        model["masters"] = masterRepository.findAll()
        model["toys"] = toyRepository.findAll()
        return "index"
    }

    @PostMapping("/master/add")
    fun addMaster(@ModelAttribute firstname: String,@ModelAttribute lastname: String): RedirectView {
        masterRepository.save(Master(firstname, lastname))
        return RedirectView("/")
    }

    @PostMapping("/master/{id}/dog")
    fun masterAction(@PathVariable id: Int, @ModelAttribute dog: Dog, @RequestParam("dog-action") dogAction: String): RedirectView {
        val master = masterRepository.findById(id).orElse(null)
        if (master != null) {
            when (dogAction) {
                "add" -> {
                    dog.master = master
                    dogRepository.save(dog)
                }
                "give-up" -> {
                    if(dogRepository.findByNameAndMasterId(dog.name, id) != null){
                        dog.master = null
                        dogRepository.save(dog)
                    }
                }
            }
        }
        return RedirectView("/")
    }

    @GetMapping("/master/{id}/delete")
    fun deleteMaster(@PathVariable id: Int): RedirectView {
        masterRepository.deleteById(id)
        return RedirectView("/")
    }

    @PostMapping("/dog/{id}/action")
    fun dogAction(@PathVariable id: Int): RedirectView {

        return RedirectView("/")
    }

}