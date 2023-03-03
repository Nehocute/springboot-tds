package edu.spring.dogs.controllers

import edu.spring.dogs.entities.Master
import edu.spring.dogs.repositories.DogRepository
import edu.spring.dogs.repositories.MasterRepository
import edu.spring.dogs.repositories.ToyRepository
import jakarta.persistence.Id
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
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
    fun index(): String {
        return "index"
    }

    @PostMapping("/master/add")
    fun addMaster(@ModelAttribute firstname: String,@ModelAttribute lastname: String): RedirectView {
        masterRepository.save(Master(firstname, lastname))
        return RedirectView("/")
    }

    @PostMapping("/master/{id}/dog")
    fun addDogToMaster(@PathVariable id: Int, @ModelAttribute master: Master): RedirectView {
        master.addDog(dogRepository.findById(id).get())
        return RedirectView("/")
    }

    @GetMapping("/master/{id}/delete")
    fun deleteMaster(@PathVariable id: Int): RedirectView {
        masterRepository.deleteById(id)
        return RedirectView("/")
    }

    @GetMapping("/dog/{id}/action")
    fun action(@PathVariable id: Int): RedirectView {

        return RedirectView("/")
    }

}