package com.java.reto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)

public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String identification;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String gender;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String phone;
}