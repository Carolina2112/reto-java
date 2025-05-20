package com.java.reto.service;

import com.java.reto.dto.TransactionDTO;
import com.java.reto.dto.TransactionRequestDTO;
import com.java.reto.exception.InsufficientBalanceException;
import com.java.reto.model.Account;
import com.java.reto.model.Transaction;
import com.java.reto.repository.AccountRepository;
import com.java.reto.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
	    this.transactionRepository = transactionRepository;
	    this.accountRepository = accountRepository;
	}

	@Transactional(readOnly = true)
	public List<TransactionDTO> getAllTransactions() {
	    List<Transaction> transactions = transactionRepository.findAll();
	    return transactions.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public TransactionDTO getTransactionById(Long id) {
	    Transaction transaction = transactionRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
	    return convertToDTO(transaction);
	}

	@Transactional(readOnly = true)
	public List<TransactionDTO> getTransactionsByAccountId(Long accountId) {
	    List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
	    return transactions.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) {
	    List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);
	    return transactions.stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	}

	@Transactional
	public TransactionDTO createTransaction(TransactionRequestDTO requestDTO) {
	    Account account = accountRepository.findByAccountNumber(requestDTO.getAccountNumber())
	            .orElseThrow(() -> new EntityNotFoundException("Account not found with number: " + requestDTO.getAccountNumber()));

	    // Verify if the account is active
	    if (!account.getStatus()) {
	        throw new IllegalStateException("The account is inactive");
	    }

	    // Get current balance
	    BigDecimal currentBalance = account.getInitialBalance();

	    // Transaction amount (positive for deposit, negative for withdrawal)
	    BigDecimal amount = requestDTO.getAmount();

	    // Transaction type
	    String transactionType;
	    if (amount.compareTo(BigDecimal.ZERO) > 0) {
	        transactionType = "Deposit";
	       
	        BigDecimal newBalance = currentBalance.add(amount);

	        // Create the transaction
	        Transaction transaction = new Transaction();
	        transaction.setDate(LocalDate.now());
	        transaction.setTransactionType(transactionType);
	        transaction.setAmount(amount);
	        transaction.setBalance(newBalance);
	        transaction.setAccount(account);

	        // Save the transaction
	        Transaction savedTransaction = transactionRepository.save(transaction);

	        // Update account balance
	        account.setInitialBalance(newBalance);
	        accountRepository.save(account);

	        return convertToDTO(savedTransaction);

	    } else if (amount.compareTo(BigDecimal.ZERO) < 0) {
	        transactionType = "Withdrawal";
	        
	        BigDecimal newBalance = currentBalance.add(amount);

	        // Verify if there is enough balance for the withdrawal
	        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
	            throw new InsufficientBalanceException("Insufficient balance");
	        }

	        // Create the transaction
	        Transaction transaction = new Transaction();
	        transaction.setDate(LocalDate.now());
	        transaction.setTransactionType(transactionType);
	        transaction.setAmount(amount);
	        transaction.setBalance(newBalance);
	        transaction.setAccount(account);

	        // Save the transaction
	        Transaction savedTransaction = transactionRepository.save(transaction);

	        // Update account balance
	        account.setInitialBalance(newBalance);
	        accountRepository.save(account);

	        return convertToDTO(savedTransaction);

	    } else {
	        throw new IllegalArgumentException("Transaction amount cannot be zero");
	    }
	}


	@Transactional
	public void deleteTransaction(Long id) {
	    if (!transactionRepository.existsById(id)) {
	        throw new EntityNotFoundException("Transaction not found with ID: " + id);
	    }
	    transactionRepository.deleteById(id);
	}

	// Conversion methods
	private TransactionDTO convertToDTO(Transaction transaction) {
	    TransactionDTO dto = new TransactionDTO();
	    dto.setId(transaction.getId());
	    dto.setDate(transaction.getDate());
	    dto.setTransactionType(transaction.getTransactionType());
	    dto.setAmount(transaction.getAmount());
	    dto.setBalance(transaction.getBalance());
	    dto.setAccountId(transaction.getAccount().getId());
	    dto.setAccountNumber(transaction.getAccount().getAccountNumber());
	    dto.setClientName(transaction.getAccount().getClient().getName());
	    return dto;
	}

}