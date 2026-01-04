package com.example.clientservice.controllers;

import com.example.clientservice.entities.Client;
import com.example.clientservice.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Retourne la liste de tous les clients
     * GET /api/client
     */
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.findAll();
    }

    /**
     * Retourne un client par son identifiant
     * GET /api/client/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        try {
            Client client = clientService.findById(id);
            return ResponseEntity.ok(client);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Erreur : " + ex.getMessage());
        }
    }

    /**
     * Crée un nouveau client
     * POST /api/client
     */
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.addClient(client);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdClient);
    }
}
