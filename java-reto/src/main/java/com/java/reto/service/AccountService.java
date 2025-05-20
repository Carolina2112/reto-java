package com.java.reto.service;

import com.java.reto.dto.AccountDTO;
import com.java.reto.model.Account;
import com.java.reto.model.Client;
import com.java.reto.repository.AccountRepository;
import com.java.reto.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
	private final AccountRepository accountRepository;
	private final ClientRepository clientRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
	    this.accountRepository = accountRepository;
	    this.clientRepository = clientRepository;
	}

	@Transactional(readOnly = true)
	public List<AccountDTO> getAllAccounts() {
	    List<Account> accounts = accountRepository.findAll();
	    return accounts.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public AccountDTO getAccountById(Long id) {
	    Account account = accountRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));
	    return convertToDTO(account);
	}

	@Transactional(readOnly = true)
	public AccountDTO getAccountByNumber(String accountNumber) {
	    Account account = accountRepository.findByAccountNumber(accountNumber)
	            .orElseThrow(() -> new EntityNotFoundException("Account not found with number: " + accountNumber));
	    return convertToDTO(account);
	}

	@Transactional(readOnly = true)
	public List<AccountDTO> getAccountsByClientId(Long clientId) {
	    List<Account> accounts = accountRepository.findByClientId(clientId);
	    return accounts.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	}

	@Transactional
	public AccountDTO createAccount(AccountDTO accountDTO) {
	    // Validate that no other account exists with the same number
	    if (accountRepository.existsByAccountNumber(accountDTO.getAccountNumber())) {
	        throw new IllegalArgumentException("An account already exists with number: " + accountDTO.getAccountNumber());
	    }

	    // Find the client
	    Client client = clientRepository.findById(accountDTO.getClientId())
	            .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + accountDTO.getClientId()));

	    Account account = convertToEntity(accountDTO, client);
	    Account savedAccount = accountRepository.save(account);
	    return convertToDTO(savedAccount);
	}

	@Transactional
	public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
	    Account existingAccount = accountRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id));
	    

	    // Find the client if updated
	    Client client = existingAccount.getClient();
	    if (accountDTO.getClientId() != null && !accountDTO.getClientId().equals(client.getId())) {
	        client = clientRepository.findById(accountDTO.getClientId())
	                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + accountDTO.getClientId()));
	    }

	    // Update data
	    existingAccount.setType(accountDTO.getType());
	    existingAccount.setInitialBalance(accountDTO.getInitialBalance());
	    existingAccount.setStatus(accountDTO.getStatus());
	    existingAccount.setClient(client);

	    Account updatedAccount = accountRepository.save(existingAccount);
	    return convertToDTO(updatedAccount);
	}

	@Transactional
	public void deleteAccount(Long id) {
	    if (!accountRepository.existsById(id)) {
	        throw new EntityNotFoundException("Account not found with ID: " + id);
	    }
	    accountRepository.deleteById(id);
	}

	// Conversion methods
	private AccountDTO convertToDTO(Account account) {
	    AccountDTO dto = new AccountDTO();
	    dto.setId(account.getId());
	    dto.setAccountNumber(account.getAccountNumber());
	    dto.setType(account.getType());
	    dto.setInitialBalance(account.getInitialBalance());
	    dto.setStatus(account.getStatus());
	    dto.setClientId(account.getClient().getId());
	    dto.setClientName(account.getClient().getName());
	    return dto;
	}

	private Account convertToEntity(AccountDTO dto, Client client) {
	    Account account = new Account();
	    // Don't set ID for new accounts
	    if (dto.getId() != null) {
	        account.setId(dto.getId());
	    }
	    account.setAccountNumber(dto.getAccountNumber());
	    account.setType(dto.getType());
	    account.setInitialBalance(dto.getInitialBalance());
	    account.setStatus(dto.getStatus());
	    account.setClient(client);
	    return account;
	}
}