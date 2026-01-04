package com.example.clientservice.services;


import com.example.clientservice.entities.Client;
import com.example.clientservice.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) throws Exception {
        return clientRepository.findById(id)
                .orElseThrow(() -> new Exception("Client non trouvé avec l'ID: " + id));
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }
}