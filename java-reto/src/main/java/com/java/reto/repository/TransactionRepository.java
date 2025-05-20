package com.java.reto.repository;

import com.java.reto.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByAccountId(Long accountId);
	@Query("SELECT t FROM Transaction t WHERE t.account.accountNumber = :accountNumber")
	List<Transaction> findByAccountNumber(@Param("accountNumber") String accountNumber);

	@Query("SELECT t FROM Transaction t " +
	       "WHERE t.account.client.id = :clientId " +
	       "AND t.date BETWEEN :startDate AND :endDate " +
	       "ORDER BY t.date ASC")
	List<Transaction> findByClientIdAndDateBetween(
	        @Param("clientId") Long clientId,
	        @Param("startDate") LocalDate startDate,
	        @Param("endDate") LocalDate endDate);
}
	