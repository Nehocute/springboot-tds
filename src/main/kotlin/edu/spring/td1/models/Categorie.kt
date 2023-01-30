package edu.spring.td1.models

data class Categorie(var libelle: String) {

    val items = HashSet<Item>()

    fun add(item:Item){
        items.add(item)
    }

    fun remove(item:Item){
        items.remove(item)
    }

    fun get():HashSet<Item>{
        return items
    }

}