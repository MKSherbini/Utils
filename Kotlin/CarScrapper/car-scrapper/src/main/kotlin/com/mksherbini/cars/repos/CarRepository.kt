package com.mksherbini.cars.repos

import com.mksherbini.cars.models.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository : JpaRepository<Car, Long> {
    fun findByModelAndModelClassAndYear(model: String, modelClass: String, year: Int): Car?
}
