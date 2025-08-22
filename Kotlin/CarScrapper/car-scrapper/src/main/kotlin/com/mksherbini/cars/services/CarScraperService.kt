package com.mksherbini.cars.services

import com.mksherbini.cars.models.Car
import com.mksherbini.cars.models.PriceHistory
import com.mksherbini.cars.repos.CarRepository
import com.mksherbini.cars.repos.PriceHistoryRepository
import io.github.bonigarcia.wdm.WebDriverManager
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CarScraperService(
    private val carRepository: CarRepository,
    private val priceHistoryRepository: PriceHistoryRepository,
) {
    private val logger = LoggerFactory.getLogger(CarScraperService::class.java)
    private val driver: WebDriver

    init {
        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions()
        options.addArguments("--headless") // Run Chrome in headless mode (no UI)
        driver = ChromeDriver(options)
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    }

    @PostConstruct
    fun init() {
        println("CarScraperService initialized")
        try {
//            $(".newCarPricesItemBody h3")[0].parentElement.nextElementSibling
            scrapCarPage("https://eg.hatla2ee.com/en/new-car/geely/GX3-pro")
//            scrapCarPage("https://eg.hatla2ee.com/en/new-car/nissan/sunny")
//            scrapCarPage("https://eg.hatla2ee.com/en/new-car/moris-garage/zs")
//            scrapCarPage("https://eg.hatla2ee.com/en/new-car/hyundai/Accent-RB")
//            scrapCarPage("https://eg.hatla2ee.com/en/new-car/geely/Gelly-Emgrand")

//            updateCar("48648")
//            updateCar("48226")
//            updateCar("48228")
//            carRepository.findAll().forEach {
//                println(it)
//            }
        } finally {
            driver.quit()
        }
    }

    private fun scrapCarPage(url: String) {
        driver.get(url)
        val priceHeaders = driver.findElements(By.cssSelector(".newCarPricesItemBody h3"))
        val urls = priceHeaders.asSequence().map {
            it.findElement(By.xpath("../following-sibling::*"))
                .findElements(By.cssSelector("a"))
        }.flatten().mapNotNull {
            it.getAttribute("href")
        }.filter {
            !it.contains("imported")
        }.map {
            it.substringAfterLast("/")
        }.toList()
        logger.info("{}", urls)

        urls.forEach {
            logger.info("{}", updateCar(it))
        }
    }


    fun updateCar(code: String) {
        val car = scrapeCarDetails(code)
        carRepository.findByModelAndModelClassAndYear(car.model, car.modelClass, car.year)?.let {
            car.id = it.id
            car.priceHistory = it.priceHistory
        }

        if (car.id == 0L) {
            logger.info("New car recorded: ${car.model} ${car.modelClass} ${car.year}")
        } else {
            logger.info("Car exists: ${car.model} ${car.modelClass} ${car.year}")
        }

        carRepository.save(car)

        val lastPriceRecord = priceHistoryRepository.findTopByCarOrderByScrapedAtDesc(car)
        logger.info(car.id.toString())
        logger.info(lastPriceRecord?.price.toString())
        logger.info(car.officialPrice.toString())
        if (lastPriceRecord == null || lastPriceRecord.price != car.officialPrice) {
            val priceHistory = PriceHistory(car = car, price = car.officialPrice)
            priceHistoryRepository.save(priceHistory)
            logger.info("New price recorded: ${car.officialPrice}")
        } else {
            logger.info("Price has not changed. No new record added.")
        }
    }

    fun scrapeCarDetails(code: String): Car {
        driver.get(getCompareUrl(code))
        val tbody = driver.findElement(By.cssSelector("tbody"))

        val rows = tbody.findElements(By.tagName("tr"))

        val data = mutableMapOf<String, String>()

        for (row in rows) {
            val key = row.findElement(By.cssSelector("td.nPricesTableFtd")).text
            val value = row.findElement(By.cssSelector("td:not(.nPricesTableFtd)")).text
            data[key] = value
        }

//        for ((key, value) in data) {
//            println("$key: $value")
//        }

        val car = Car()
        car.model = data["MODEL"] ?: ""
        car.modelClass = data["CLASS"] ?: ""
        car.year = data["YEAR"]?.toIntOrNull() ?: 0
        car.officialPrice = data["OFFICIAL PRICES"]?.replace(",", "")?.toIntOrNull() ?: 0
        car.minInstallment = data["MIN.INSTALLMENT"]?.replace(",", "")?.toIntOrNull() ?: 0
        car.minDeposit = data["MIN.DEPOSIT"]?.replace(",", "")?.toIntOrNull() ?: 0
        car.engineCapacity = data["ENGINE CAPACITY"]?.replace(",", "")?.toIntOrNull() ?: 0
        car.horsePower = data["HORSE POWER"]?.toIntOrNull() ?: 0
        car.maximumSpeed = data["MAXIMUM SPEED"]?.toIntOrNull() ?: 0
        car.acceleration = data["ACCELERATION"]?.toDoubleOrNull() ?: 0.0
        car.speeds = data["SPEEDS"]?.toIntOrNull() ?: 0
        car.transmissionType = data["TRANSMISSION TYPE"] ?: ""
        car.fuel = data["FUEL"]?.toIntOrNull() ?: 0
        car.literPer100Km = data["LITER/100KM"]?.toDoubleOrNull() ?: 0.0
        car.originCountry = data["ORIGIN COUNTRY"] ?: ""
        car.assemblyCountry = data["ASSEMBLY COUNTRY"] ?: ""
        car.lengthMm = data["LENGTH (MM)"]?.replace(",", "")?.toIntOrNull() ?: 0
        car.widthMm = data["WIDTH (MM)"]?.replace(",", "")?.toIntOrNull() ?: 0
        car.heightMm = data["HEIGHT (MM)"]?.replace(",", "")?.toIntOrNull() ?: 0
        car.groundClearance = data["GROUND CLEARANCE"]?.toIntOrNull() ?: 0
        car.wheelBase = data["WHEEL BASE"]?.replace(",", "")?.toIntOrNull() ?: 0
        car.trunkSize = data["TRUNK SIZE"]?.toIntOrNull() ?: 0
        car.seats = data["SEATS"]?.toIntOrNull() ?: 0
        car.tractionType = data["TRACTION TYPE"] ?: ""
        car.numberOfCylinders = data["NUMBER OF CYLINDER"]?.toIntOrNull() ?: 0
        car.fuelTankCapacity = data["FUEL TANK CAPACITY"]?.toIntOrNull() ?: 0
        car.torqueOfNewton = data["TORQUE OF NEWTON"]?.toIntOrNull() ?: 0
        car.alloyWheels = data["ALLOY WHEELS"] == "done"
        car.abs = data["ABS"] == "done"
        car.airConditioning = data["AIR CONDITIONING"] == "done"
        car.remoteKeyless = data["REMOTE KEYLESS"] == "done"
        car.driverAirbag = data["DRIVER AIRBAG"] == "done"
        car.passengerAirbag = data["PASSENGER AIRBAG"] == "done"
        car.sideAirbag = data["SIDE AIRBAG"] == "done"
        car.frontPowerWindows = data["FRONT POWER WINDOWS"] == "done"
        car.backPowerWindows = data["BACK POWER WINDOWS"] == "done"
        car.tintedGlass = data["TINTED GLASS"] == "done"
        car.powerSeats = data["POWER SEATS"] == "done"
        car.cassetteRadio = data["CASSETTE RADIO"] == "done"
        car.cdChanger = data["CD CHANGER"] == "done"
        car.dvdPlayer = data["DVD PLAYER"] == "done"
        car.cdPlayer = data["CD PLAYER"] == "done"
        car.alarm = data["ALARM"] == "done"
        car.sunroof = data["SUNROOF"] == "done"
        car.ebd = data["EBD"] == "done"
        car.sensors = data["SENSORS"] == "done"
        car.electricMirrors = data["ELECTRIC MIRRORS"] == "done"
        car.closingMirrors = data["CLOSING MIRRORS"] == "done"
        car.leatherSeats = data["LEATHER SEATS"] == "done"
        car.fabricBrushes = data["FABRIC BRUSHES"] == "done"
        car.fogLight = data["FOG LIGHT"] == "done"
        car.powerSteering = data["POWER STEERING"] == "done"
        car.aux = data["AUX"] == "done"
        car.usbPort = data["USB PORT"] == "done"
        car.bluetooth = data["BLUETOOTH"] == "done"
        car.eps = data["EPS"] == "done"
        car.rearCamera = data["REAR CAMERA"] == "done"
        car.gps = data["GPS"] == "done"
        car.cruiseControl = data["CRUISE CONTROL"] == "done"
        car.frontSensors = data["FRONT SENSORS"] == "done"
        car.rearSensors = data["REAR SENSORS"] == "done"
        car.centralLock = data["CENTRAL LOCK"] == "done"
        car.intelligentParkingSystem = data["INTELLIGENT PARKING SYSTEM"] == "done"
        car.rearSpoiler = data["REAR SPOILER"] == "done"
        car.electricChairs = data["ELECTRIC CHAIRS"] == "done"
        car.antiTheftSystem = data["ANTI - THEFT SYSTEM"] == "done"
        car.multifunction = data["MULTIFUNCTION"] == "done"
        car.startEngine = data["START ENGINE"] == "done"
        car.esp = data["ESP"] == "done"
        car.steptronic = data["STEPTRONIC"] == "done"
        car.panoramicSunroof = data["PANORAMIC SUNROOF"] == "done"
        car.multimediaTouchScreen = data["MULTIMEDIA TOUCH SCREEN"] == "done"
        car.touchActivatedDoorLock = data["TOUCH ACTIVATED DOOR LOCK"] == "done"

        return car
    }


    fun getCompareUrl(code: String): String {
        return "https://eg.hatla2ee.com/en/new-car/compare/terazes/$code/0"
    }


    @PreDestroy
    fun cleanup() {
        println("CarScraperService cleanup")
        closeDriver()
    }

    fun closeDriver() {
        driver.quit()
    }
}
