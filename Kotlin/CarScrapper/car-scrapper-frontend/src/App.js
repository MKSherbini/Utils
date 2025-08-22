import React, {useState, useEffect} from "react";
import axios from "axios";
import {Input} from "./components/ui/Input";
import {Select} from "./components/ui/Select";
import {Checkbox} from "./components/ui/Checkbox";
import {Card, CardContent} from "./components/ui/card";

function CarList() {
    const [cars, setCars] = useState([]);
    const [filteredCars, setFilteredCars] = useState([]);
    const [filters, setFilters] = useState({
        model: "",
        year: "",
        transmissionType: "",
        abs: false,
        sunroof: false,
        bluetooth: false,
    });

    useEffect(() => {
        axios.get("/api/cars")
            .then(response => {
                setCars(response.data);
                setFilteredCars(response.data);
            })
            .catch(error => console.error("Error fetching cars:", error));
    }, []);

    useEffect(() => {
        let filtered = cars.filter(car =>
            (!filters.model || car.model.toLowerCase().includes(filters.model.toLowerCase())) &&
            (!filters.year || car.year.toString() === filters.year) &&
            (!filters.transmissionType || car.transmissionType === filters.transmissionType) &&
            (!filters.abs || car.abs) &&
            (!filters.sunroof || car.sunroof) &&
            (!filters.bluetooth || car.bluetooth)
        );
        setFilteredCars(filtered);
    }, [filters, cars]);

    return (
        <div className="p-6">
            <h1 className="text-xl font-bold mb-4">Car List</h1>
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
                <Input
                    placeholder="Search Model"
                    value={filters.model}
                    onChange={(e) => setFilters({...filters, model: e.target.value})}
                />
                <Input
                    type="number"
                    placeholder="Year"
                    value={filters.year}
                    onChange={(e) => setFilters({...filters, year: e.target.value})}
                />
                <Select
                    value={filters.transmissionType}
                    onChange={(e) => setFilters({...filters, transmissionType: e.target.value})}
                >
                    <option value="">All Transmission</option>
                    <option value="automatic">Automatic</option>
                    <option value="manual">Manual</option>
                </Select>
                <Checkbox
                    checked={filters.abs}
                    onChange={(e) => setFilters({...filters, abs: e.target.checked})}
                >ABS</Checkbox>
                <Checkbox
                    checked={filters.sunroof}
                    onChange={(e) => setFilters({...filters, sunroof: e.target.checked})}
                >Sunroof</Checkbox>
                <Checkbox
                    checked={filters.bluetooth}
                    onChange={(e) => setFilters({...filters, bluetooth: e.target.checked})}
                >Bluetooth</Checkbox>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {filteredCars.map((car) => (
                    <Card key={car.id}>
                        <CardContent>
                            <h2 className="text-lg font-semibold">{car.model} ({car.year})</h2>
                            <p>Class: {car.modelClass}</p>
                            <p>Price: {car.officialPrice} EGP</p>
                            <p>Transmission: {car.transmissionType}</p>
                            <p>ABS: {car.abs ? "Yes" : "No"}</p>
                            <p>Sunroof: {car.sunroof ? "Yes" : "No"}</p>
                            <p>Bluetooth: {car.bluetooth ? "Yes" : "No"}</p>
                        </CardContent>
                    </Card>
                ))}
            </div>
        </div>
    );
}

export default CarList;
