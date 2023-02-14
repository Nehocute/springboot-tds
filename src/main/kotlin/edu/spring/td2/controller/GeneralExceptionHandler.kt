package edu.spring.td2.controller

import edu.spring.td2.exceptions.ElementNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class GeneralExceptionHandler {
    @ExceptionHandler(value = [
        ElementNotFoundException::class,
        MethodArgumentTypeMismatchException::class,
    ])
    fun handleException(e:Exception): ModelAndView {
        return message("Erreur", e.message?:"Une erreur est survenue")
    }

    @ExceptionHandler(value = [
        NoHandlerFoundException::class
    ])
    fun notFoundException(e:NoHandlerFoundException): ModelAndView {
        return message("Page non trouvable", "La page ${e.requestURL} n'existe pas")
    }

    @ExceptionHandler(value = [
        DataIntegrityViolationException::class
    ])
    fun dataIntegrityException(e:DataIntegrityViolationException): ModelAndView {
        return message("Requête non conforme", "Les paramètres de la requête ne sont pas conforme à la base de données")
    }

    private fun message(title:String, message:String):ModelAndView {
        val mv = ModelAndView("/main/error")
        mv.addObject("title", title)
        mv.addObject("content", message)
        return mv
    }
}