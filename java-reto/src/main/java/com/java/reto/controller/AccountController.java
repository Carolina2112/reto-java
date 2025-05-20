package com.java.reto.controller;

import com.java.reto.dto.AccountDTO;
import com.java.reto.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	private final AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
	    this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
	    List<AccountDTO> accounts = accountService.getAllAccounts();
	    return ResponseEntity.ok(accounts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
	    AccountDTO account = accountService.getAccountById(id);
	    return ResponseEntity.ok(account);
	}

	@GetMapping("/number/{accountNumber}")
	public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable String accountNumber) {
	    AccountDTO account = accountService.getAccountByNumber(accountNumber);
	    return ResponseEntity.ok(account);
	}

	@GetMapping("/client/{clientId}")
	public ResponseEntity<List<AccountDTO>> getAccountsByClientId(@PathVariable Long clientId) {
	    List<AccountDTO> accounts = accountService.getAccountsByClientId(clientId);
	    return ResponseEntity.ok(accounts);
	}

	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
	    AccountDTO createdAccount = accountService.createAccount(accountDTO);
	    return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AccountDTO> updateAccount(@PathVariable("id") Long id, @Valid @RequestBody AccountDTO accountDTO) {
	    AccountDTO updatedAccount = accountService.updateAccount(id, accountDTO);
	    return ResponseEntity.ok(updatedAccount);
	}

	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
	    accountService.deleteAccount(id);
	    return ResponseEntity.noContent().build();
	}

}