package com.mksherbini.cars.controllers

import com.mksherbini.cars.models.Car
import com.mksherbini.cars.repos.CarRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cars")
class CarController(private val carRepository: CarRepository) {

    @GetMapping
    fun getAllCars(): List<Car> {
        return carRepository.findAll()
    }
}
