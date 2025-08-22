package com.mksherbini.cars.models

import jakarta.persistence.*

@Entity
@Table(name = "cars")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "model")
    var model: String = "",

    @Column(name = "class")
    var modelClass: String = "",

    @Column(name = "year")
    var year: Int = 0,

    @Column(name = "official_price")
    var officialPrice: Int = 0,

    @OneToMany(mappedBy = "car", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true, fetch = FetchType.LAZY)
    var priceHistory: MutableList<PriceHistory> = mutableListOf(),

    @Column(name = "min_installment")
    var minInstallment: Int = 0,

    @Column(name = "min_deposit")
    var minDeposit: Int = 0,

    @Column(name = "engine_capacity")
    var engineCapacity: Int = 0,

    @Column(name = "horse_power")
    var horsePower: Int = 0,

    @Column(name = "maximum_speed")
    var maximumSpeed: Int = 0,

    @Column(name = "acceleration")
    var acceleration: Double = 0.0,

    @Column(name = "speeds")
    var speeds: Int = 0,

    @Column(name = "transmission_type")
    var transmissionType: String = "",

    @Column(name = "fuel")
    var fuel: Int = 0,

    @Column(name = "liter_per_100km")
    var literPer100Km: Double = 0.0,

    @Column(name = "origin_country")
    var originCountry: String = "",

    @Column(name = "assembly_country")
    var assemblyCountry: String = "",

    @Column(name = "length_mm")
    var lengthMm: Int = 0,

    @Column(name = "width_mm")
    var widthMm: Int = 0,

    @Column(name = "height_mm")
    var heightMm: Int = 0,

    @Column(name = "ground_clearance")
    var groundClearance: Int = 0,

    @Column(name = "wheel_base")
    var wheelBase: Int = 0,

    @Column(name = "trunk_size")
    var trunkSize: Int = 0,

    @Column(name = "seats")
    var seats: Int = 0,

    @Column(name = "traction_type")
    var tractionType: String = "",

    @Column(name = "number_of_cylinders")
    var numberOfCylinders: Int = 0,

    @Column(name = "fuel_tank_capacity")
    var fuelTankCapacity: Int = 0,

    @Column(name = "torque_of_newton")
    var torqueOfNewton: Int = 0,

    @Column(name = "alloy_wheels")
    var alloyWheels: Boolean = false,

    @Column(name = "abs")
    var abs: Boolean = false,

    @Column(name = "air_conditioning")
    var airConditioning: Boolean = false,

    @Column(name = "remote_keyless")
    var remoteKeyless: Boolean = false,

    @Column(name = "driver_airbag")
    var driverAirbag: Boolean = false,

    @Column(name = "passenger_airbag")
    var passengerAirbag: Boolean = false,

    @Column(name = "side_airbag")
    var sideAirbag: Boolean = false,

    @Column(name = "front_power_windows")
    var frontPowerWindows: Boolean = false,

    @Column(name = "back_power_windows")
    var backPowerWindows: Boolean = false,

    @Column(name = "tinted_glass")
    var tintedGlass: Boolean = false,

    @Column(name = "power_seats")
    var powerSeats: Boolean = false,

    @Column(name = "cassette_radio")
    var cassetteRadio: Boolean = false,

    @Column(name = "cd_changer")
    var cdChanger: Boolean = false,

    @Column(name = "dvd_player")
    var dvdPlayer: Boolean = false,

    @Column(name = "cd_player")
    var cdPlayer: Boolean = false,

    @Column(name = "alarm")
    var alarm: Boolean = false,

    @Column(name = "sunroof")
    var sunroof: Boolean = false,

    @Column(name = "ebd")
    var ebd: Boolean = false,

    @Column(name = "sensors")
    var sensors: Boolean = false,

    @Column(name = "electric_mirrors")
    var electricMirrors: Boolean = false,

    @Column(name = "closing_mirrors")
    var closingMirrors: Boolean = false,

    @Column(name = "leather_seats")
    var leatherSeats: Boolean = false,

    @Column(name = "fabric_brushes")
    var fabricBrushes: Boolean = false,

    @Column(name = "fog_light")
    var fogLight: Boolean = false,

    @Column(name = "power_steering")
    var powerSteering: Boolean = false,

    @Column(name = "aux")
    var aux: Boolean = false,

    @Column(name = "usb_port")
    var usbPort: Boolean = false,

    @Column(name = "bluetooth")
    var bluetooth: Boolean = false,

    @Column(name = "eps")
    var eps: Boolean = false,

    @Column(name = "rear_camera")
    var rearCamera: Boolean = false,

    @Column(name = "gps")
    var gps: Boolean = false,

    @Column(name = "cruise_control")
    var cruiseControl: Boolean = false,

    @Column(name = "front_sensors")
    var frontSensors: Boolean = false,

    @Column(name = "rear_sensors")
    var rearSensors: Boolean = false,

    @Column(name = "central_lock")
    var centralLock: Boolean = false,

    @Column(name = "intelligent_parking_system")
    var intelligentParkingSystem: Boolean = false,

    @Column(name = "rear_spoiler")
    var rearSpoiler: Boolean = false,

    @Column(name = "electric_chairs")
    var electricChairs: Boolean = false,

    @Column(name = "anti_theft_system")
    var antiTheftSystem: Boolean = false,

    @Column(name = "multifunction")
    var multifunction: Boolean = false,

    @Column(name = "start_engine")
    var startEngine: Boolean = false,

    @Column(name = "esp")
    var esp: Boolean = false,

    @Column(name = "steptronic")
    var steptronic: Boolean = false,

    @Column(name = "panoramic_sunroof")
    var panoramicSunroof: Boolean = false,

    @Column(name = "multimedia_touch_screen")
    var multimediaTouchScreen: Boolean = false,

    @Column(name = "touch_activated_door_lock")
    var touchActivatedDoorLock: Boolean = false
)
