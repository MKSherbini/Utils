package com.mksherbini.cars.repos

import com.mksherbini.cars.models.Car
import com.mksherbini.cars.models.PriceHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PriceHistoryRepository : JpaRepository<PriceHistory, Long> {
    fun findTopByCarOrderByScrapedAtDesc(car: Car): PriceHistory?
    fun findByCarOrderByScrapedAtDesc(car: Car): List<PriceHistory>
}
