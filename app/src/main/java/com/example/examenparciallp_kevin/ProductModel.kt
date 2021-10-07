package com.example.examenparciallp_kevin


import java.util.*;

data class ProductModel(
    var id : Int = getAutoId(),
    var nomprod : String = "",
    var price : Double,
    var stock : Double,
    var category : String = ""



){
    companion object{
        fun getAutoId() : Int {
            val random = Random()
            return random.nextInt(100);
        }

    }

    override fun toString(): String {
        return nomprod
    }
};