package com.example.playgroundapp.design_pattern.creational

/**
 *
 * BuilderPattern allows you to produce different types and representations of an object using the same construction code.
 * ***/
fun main() {
    BuilderPattern()
}

class BuilderPattern {


    class Laptop private constructor(
        private val processor: String,
        private val ram: String,
        private val battery: String,
        private val screenSize: String,
    ) {


        class Builder(
            private val processor: String
        ) {

            // optional features
            private val data = DataHolder()

            fun setRam(ram: String): Builder {
                data.ram = ram
                return this
            }

            fun setBattery(battery: String): Builder {
                data.battery = battery
                return this
            }

            fun setScreenSize(screenSize: String): Builder {
                data.screenSize = screenSize
                return this
            }

            fun create(): Laptop {
                return Laptop(
                    processor = processor,
                    ram = data.ram,
                    battery = data.battery,
                    screenSize = data.screenSize,
                )
            }

            private data class DataHolder(
                var ram: String = "2GB",
                var battery: String = "5000MAH",
                var screenSize: String = "15inch",
            )
        }

        fun info(): String {
            return "processor $processor, ram $ram, battery $battery, screenSize $screenSize"
        }
    }


    init {
        val laptop = Laptop.Builder("intel core i3").setBattery("5000MAH").setRam("32GB").create()
        println(laptop.info())
    }

}

