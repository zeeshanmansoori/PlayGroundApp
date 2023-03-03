package com.example.playgroundapp.design_pattern.architecture.mvc

import java.util.*


class MVCModel:Observable() {
    private val list = mutableListOf<Int>()

    fun getValueAtIndex(the_index: Int): Int {
        return list[the_index]
    }

    fun setValueAtIndex(the_index: Int) {
        list[the_index] = the_index + 1
        setChanged()
        notifyObservers()
    }
}