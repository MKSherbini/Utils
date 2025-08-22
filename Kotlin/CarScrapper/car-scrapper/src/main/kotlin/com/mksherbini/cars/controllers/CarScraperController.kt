package com.mksherbini.cars.controllers

import com.mksherbini.cars.services.CarScraperService
import org.springframework.web.bind.annotation.RestController

@RestController
class CarScraperController(private val carScraperService: CarScraperService) {

//    @GetMapping("/scrape-car")
//    fun scrapeCar(@RequestParam url: String): Map<String, Any> {
////        return try {
//          return  carScraperService.scrapeCarDetails(url)
////        } catch (e: Exception) {
////            mapOf("error" to e.message)
////        } finally {
////            carScraperService.closeDriver()
////        }
//    }
}
