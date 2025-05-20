package com.java.reto.repository;

import com.java.reto.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByClientId(Long clientId);
	Optional<Account> findByAccountNumber(String accountNumber);
	boolean existsByAccountNumber(String accountNumber);
}