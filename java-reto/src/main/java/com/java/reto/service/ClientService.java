package com.java.reto.service;

import com.java.reto.dto.ClientDTO;
import com.java.reto.model.Client;
import com.java.reto.repository.ClientRepository;
import com.java.reto.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
	private final ClientRepository clientRepository;
	private final PersonRepository personRepository;

	@Autowired
	public ClientService(ClientRepository clientRepository, PersonRepository personRepository) {
	    this.clientRepository = clientRepository;
	    this.personRepository = personRepository;
	}

	@Transactional(readOnly = true)
	public List<ClientDTO> getAllClients() {
	    List<Client> clients = clientRepository.findAll();
	    return clients.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ClientDTO getClientById(Long id) {
	    Client client = clientRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + id));
	    return convertToDTO(client);
	}

	@Transactional
	public ClientDTO createClient(ClientDTO clientDTO) {
	    // Validate that no other client exists with the same ID
	    if (clientRepository.existsByClientId(clientDTO.getClientId())) {
	        throw new IllegalArgumentException("A client already exists with ID: " + clientDTO.getClientId());
	    }

	    // Validate that no other person exists with the same identification
	    if (personRepository.existsByIdentification(clientDTO.getIdentification())) {
	        throw new IllegalArgumentException("A person already exists with identification: " + clientDTO.getIdentification());
	    }

	    Client client = convertToEntity(clientDTO);
	    Client savedClient = clientRepository.save(client);
	    return convertToDTO(savedClient);
	}

	@Transactional
	public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
	    Client existingClient = clientRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + id));

	    // Update data
	    existingClient.setName(clientDTO.getName());
	    existingClient.setGender(clientDTO.getGender());
	    existingClient.setAge(clientDTO.getAge());
	    existingClient.setIdentification(clientDTO.getIdentification());
	    existingClient.setAddress(clientDTO.getAddress());
	    existingClient.setPhone(clientDTO.getPhone());
	    existingClient.setClientId(clientDTO.getClientId());
	    existingClient.setPassword(clientDTO.getPassword());
	    existingClient.setStatus(clientDTO.getStatus());

	    Client updatedClient = clientRepository.save(existingClient);
	    return convertToDTO(updatedClient);
	}

	@Transactional
	public void deleteClient(Long id) {
	    if (!clientRepository.existsById(id)) {
	        throw new EntityNotFoundException("Client not found with ID: " + id);
	    }
	    clientRepository.deleteById(id);
	}

	// Conversion methods
	private ClientDTO convertToDTO(Client client) {
	    ClientDTO dto = new ClientDTO();
	    dto.setId(client.getId());
	    dto.setName(client.getName());
	    dto.setGender(client.getGender());
	    dto.setAge(client.getAge());
	    dto.setIdentification(client.getIdentification());
	    dto.setAddress(client.getAddress());
	    dto.setPhone(client.getPhone());
	    dto.setClientId(client.getClientId());
	    dto.setPassword(client.getPassword());
	    dto.setStatus(client.getStatus());
	    return dto;
	}

	private Client convertToEntity(ClientDTO dto) {
	    Client client = new Client();
	    // Don't set ID for new clients
	    if (dto.getId() != null) {
	        client.setId(dto.getId());
	    }
	    client.setName(dto.getName());
	    client.setGender(dto.getGender());
	    client.setAge(dto.getAge());
	    client.setIdentification(dto.getIdentification());
	    client.setAddress(dto.getAddress());
	    client.setPhone(dto.getPhone());
	    client.setClientId(dto.getClientId());
	    client.setPassword(dto.getPassword());
	    client.setStatus(dto.getStatus());
	    return client;
	}

}