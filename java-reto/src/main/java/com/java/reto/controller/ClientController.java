package com.java.reto.controller;

import com.java.reto.dto.ClientDTO;
import com.java.reto.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
	private final ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService) {
	    this.clientService = clientService;
	}

	@GetMapping
	public ResponseEntity<List<ClientDTO>> getAllClients() {
	    List<ClientDTO> clients = clientService.getAllClients();
	    return ResponseEntity.ok(clients);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
	    ClientDTO client = clientService.getClientById(id);
	    return ResponseEntity.ok(client);
	}

	@PostMapping
	public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
	    ClientDTO createdClient = clientService.createClient(clientDTO);
	    return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> updateClient(
	    @PathVariable("id") Long id,
	    @Valid @RequestBody ClientDTO clientDTO) {
	    ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
	    return ResponseEntity.ok(updatedClient);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
	    clientService.deleteClient(id);
	    return ResponseEntity.noContent().build();
	}
}