package com.example.playgroundapp.design_pattern.structural

/***
 * Adapter provides compatibility between the incompatible interfaces.
 * ***/
class AdapterPattern {

    interface Car {
        fun drive()
    }

    class CarDriver(private val car: Car) {
        fun startDriving() {
            car.drive()
        }
    }

    class CarAdapter : Car {
        override fun drive() {

        }

    }
}