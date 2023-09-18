package com.loan.loanapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.loan.loanapp.dao.CustomerRepository;

import com.loan.loanapp.entity.Customer;

import com.loan.loanapp.exception.CustomerException;

import com.loan.loanapp.service.CustomerServiceImpl;

class CustomerServiceImplTest {

	@Mock

	private CustomerRepository customerRepo;

	@InjectMocks

	private CustomerServiceImpl customerService;

	@SuppressWarnings("deprecation")

	@BeforeEach

	public void setUp() {

		MockitoAnnotations.initMocks(this);

	}

	@Test

	void testAddCustomer_PositiveCase() throws CustomerException {

		Customer newCustomer = new Customer(null, "test@example.com", "Test User", "password", null, null, null, null,
				null, null, null, null, null, null, null, null);

		when(customerRepo.findByCustomerEmail(newCustomer.getCustomerEmail())).thenReturn(Optional.empty());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String encodedPassword = passwordEncoder.encode(newCustomer.getCustomerPassword());

		newCustomer.setCustomerPassword(encodedPassword);

		when(customerRepo.save(newCustomer)).thenReturn(newCustomer);

		Customer savedCustomer = customerService.addCustomer(newCustomer);

		assertEquals(newCustomer, savedCustomer);

	}

	//@Test

	void testAddCustomer_CustomerAlreadyExists() {

		String existingEmail = "existing@example.com";

		Customer existingCustomer = new Customer(null, existingEmail, "Existing User", "password", null, null, null,
				null, null, null, null, null, null, null, null, null);

		CustomerRepository customerRepo = mock(CustomerRepository.class);

		CustomerServiceImpl customerService = new CustomerServiceImpl();

		try {

			customerService.addCustomer(existingCustomer);

			fail("Expected an exception, but it wasn't thrown.");

		} catch (Exception e) {

			assertTrue(e instanceof CustomerException,
					"Expected CustomerException, but got " + e.getClass().getSimpleName());

			assertEquals("Customer with email " + existingEmail + " already exists", e.getMessage());

		}

		verify(customerRepo, times(1)).findByCustomerEmail(existingEmail);

	}

	//@Test

	void testAddCustomer_MissingCustomerEmail() throws CustomerException {

		Customer customer = new Customer(null, "User", "password", null, null, null, null, null, null, null, null, null,
				null, null, null, null);

		when(customerRepo.findByCustomerEmail(null)).thenReturn(Optional.empty());

		assertThrows(CustomerException.class, () -> {

			customerService.addCustomer(customer);

		});

	}

	//@Test

	void testAddCustomer_MissingCustomerName() {

		Customer customer = new Customer(null, "test@example.com", null, "password", null, null, null, null, null, null,
				null, null, null, null, null, null);

		when(customerRepo.findByCustomerEmail(customer.getCustomerName())).thenReturn(Optional.empty());

		assertThrows(CustomerException.class, () -> {

			customerService.addCustomer(customer);

		});

	}

	//@Test

	void testAddCustomer_MissingCustomerPassword() throws CustomerException {

		Customer customer = new Customer(null, "test@example.com", "User", null, null, null, null, null, null, null,
				null, null, null, null, null, null);

		when(customerRepo.findByCustomerEmail(customer.getCustomerPassword())).thenReturn(Optional.empty());

		assertThrows(CustomerException.class, () -> {

			customerService.addCustomer(customer);

		});

	}

	//@Test

	void testAddCustomer_InvalidCustomerEmail() throws CustomerException {

		Customer customer = new Customer(null, "invalidemail", "User", "password", null, null, null, null, null, null,
				null, null, null, null, null, null);

		customerService.addCustomer(customer);

	}

	//@Test

	void testAddCustomer_InvalidCustomerPassword() throws CustomerException {

		Customer customer = new Customer(null, "test@example.com", "User", "pass", null, null, null, null, null, null,
				null, null, null, null, null, null);

		customerService.addCustomer(customer);

	}

}
