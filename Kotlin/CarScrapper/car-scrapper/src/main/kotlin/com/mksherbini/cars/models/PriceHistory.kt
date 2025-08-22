package com.mksherbini.cars.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "price_history")
data class PriceHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnore
    var car: Car? = null,

    @Column(name = "price")
    var price: Int = 0,

    @Column(name = "scraped_at", nullable = false)
    var scrapedAt: LocalDateTime = LocalDateTime.now()
)

