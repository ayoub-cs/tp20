package com.example.car.services;

import com.example.car.entities.Car;
import com.example.car.models.CarResponse;
import com.example.car.models.Client;
import com.example.car.models.ClientDTO;
import com.example.car.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final RestTemplate restTemplate;

    /**
     * URL du service Client exposé via la Gateway
     * (à externaliser en configuration en environnement réel)
     */
    private static final String CLIENT_SERVICE_URL =
            "http://localhost:8888/SERVICE-CLIENT/api/client/";

    public CarService(CarRepository carRepository, RestTemplate restTemplate) {
        this.carRepository = carRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Retourne toutes les voitures avec les informations du client associé
     */
    public List<CarResponse> findAll() {
        return carRepository.findAll()
                .stream()
                .map(this::toCarResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retourne une voiture par son identifiant avec les détails du client
     */
    public CarResponse findById(Long id) throws Exception {
        Car car = carRepository.findById(id)
                .orElseThrow(() ->
                        new Exception("Aucune voiture trouvée avec l'identifiant : " + id)
                );

        return toCarResponse(car);
    }

    /**
     * Transforme une entité Car en CarResponse
     * en enrichissant la réponse avec les données du client
     */
    private CarResponse toCarResponse(Car car) {

        Client client = recupererClient(car.getClient_id());

        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .matricule(car.getMatricule())
                .client(client)
                .build();
    }

    /**
     * Appelle le service Client et effectue le mapping DTO → modèle métier
     */
    private Client recupererClient(Long clientId) {
        try {
            ClientDTO dto = restTemplate.getForObject(
                    CLIENT_SERVICE_URL + clientId,
                    ClientDTO.class
            );

            if (dto == null) {
                return null;
            }

            return new Client(
                    dto.getId(),
                    dto.getNom(),
                    dto.getAge() != null ? dto.getAge().intValue() : null
            );

        } catch (Exception ex) {
            // Tolérance aux erreurs du service distant
            System.err.println("Erreur lors de l'appel au service Client : " + ex.getMessage());
            return null;
        }
    }
}
