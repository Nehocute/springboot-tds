package edu.spring.dogs.controllers

import edu.spring.dogs.repositories.DogRepository
import edu.spring.dogs.repositories.MasterRepository
import io.github.jeemv.springboot.vuejs.VueJS
import io.github.jeemv.springboot.vuejs.utilities.Http
import io.github.jeemv.springboot.vuejs.utilities.JsArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/spa")
class SPAController {

    @Autowired
    lateinit var vue: VueJS

    @Autowired
    lateinit var dogRepository: DogRepository

    @ModelAttribute("vue")
    fun getVueInstance(): VueJS = vue

    @GetMapping(path = ["/","","/index"])
    fun index(): String {
        vue.addDataRaw("masters", "[]")
        vue.addDataRaw("newMaster", "{}")
        vue.addData("dogs", dogRepository.findByMasterIsNull())

        vue.onMounted(
                Http.get("/masters",
                        Http.responseArrayToArray("this.masters","masters"),
                        "console.log('Erreur sur chargement des données!');"
                )
        )

        vue.addMethod("remove",
                Http.delete("'/masters/'+master.id",
                        JsArray.remove("this.masters","master")+ JsArray.addAll("this.dogs","master.dogs")+
                                "console.log(`Maître \${master.firstname} supprimé!`);", "console.log('Erreur sur suppression de master!');"
                ), "master")
        vue.addMethod("addMaster",
                Http.post("/masters",
                        "master",
                        JsArray.add("this.masters","master")+"this.newMaster={}",
                        "console.log('Erreur')"),
                "master")
        return "/spa/index"
    }
}