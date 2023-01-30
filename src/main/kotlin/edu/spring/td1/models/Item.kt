package edu.spring.td1.models

class Item(var nom: String) {

    var evaluation:Int=0

    override fun equals(other: Any?): Boolean {
        if(other==null) return false;
        if(this===other) return true;
        if(javaClass != other.javaClass) return false
        var otherItem = other as Item
        return nom==otherItem.nom && evaluation==otherItem.evaluation
    }

    override fun hashCode(): Int {
        var result = nom?.hashCode() ?: 0
        result = 31 * result + evaluation
        return result
    }

}