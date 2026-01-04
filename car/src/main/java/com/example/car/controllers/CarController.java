package com.example.car.controllers;

import com.example.car.models.CarResponse;
import com.example.car.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Retourne la liste de toutes les voitures avec les informations clients associées
     * GET /api/car
     */
    @GetMapping
    public List<CarResponse> getAllCars() {
        return carService.findAll();
    }

    /**
     * Retourne une voiture par son identifiant avec les détails du client
     * GET /api/car/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id) {
        try {
            CarResponse response = carService.findById(id);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Erreur : " + ex.getMessage());
        }
    }
}
