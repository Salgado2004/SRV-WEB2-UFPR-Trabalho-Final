package br.ufpr.webII.trabalhoFinal;

import br.ufpr.webII.trabalhoFinal.controller.AuthController;
import br.ufpr.webII.trabalhoFinal.dto.CustomerDTO;
import br.ufpr.webII.trabalhoFinal.model.Address;
import br.ufpr.webII.trabalhoFinal.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrabalhoFinalApplicationTests {

	private AuthController authController;
	private AuthService authService;

	@BeforeEach
	void setUp() {
		authService = new AuthService(); // Criação da instância real do AuthService
		authController = new AuthController(authService); // Passando o AuthService para o AuthController
	}

	@Test
	void testRegisterCustomerSuccess() {
		// Criar um CustomerDTO de teste
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setEmail("test@dominio.com");
		customerDTO.setCpf("12345678909");
		customerDTO.setName("John");
		customerDTO.setSurname("Doe");

		// Criar um endereço de exemplo
		Address address = new Address();
		address.setCep("83414180");
		address.setUf("Paraná");
		address.setCity("Colombo");
		address.setDistrict("Centro");
		address.setStreet("Travessa Alfredo Lazaroto");
		address.setNumber(65);
		customerDTO.setAddress(address);

		// Chamar o método de registro
		ResponseEntity<String> response = authController.register(customerDTO);

		// Verificar se a resposta é 201 Created
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Cliente cadastrado com sucesso!", response.getBody());
	}

	@Test
	void testRegisterCustomerInvalidEmail() {
		// Criar um CustomerDTO com email inválido
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setEmail("invalid-email");
		customerDTO.setCpf("12345678909");
		customerDTO.setName("John");
		customerDTO.setSurname("Doe");

		// Criar um endereço de exemplo
		Address address = new Address();
		address.setStreet("123 Street"); // Substitua por métodos reais de Address
		customerDTO.setAddress(address);

		// Chamar o método de registro
		ResponseEntity<String> response = authController.register(customerDTO);

		// Verificar se a resposta é 400 Bad Request
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("E-mail inválido.", response.getBody());
	}

	@Test
	void testRegisterCustomerInvalidCpf() {
		// Criar um CustomerDTO com CPF inválido
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setEmail("test@dominio.com");
		customerDTO.setCpf("invalid-cpf");
		customerDTO.setName("John");
		customerDTO.setSurname("Doe");

		// Criar um endereço de exemplo
		Address address = new Address();
		address.setStreet("123 Street"); // Substitua por métodos reais de Address
		customerDTO.setAddress(address);

		// Chamar o método de registro
		ResponseEntity<String> response = authController.register(customerDTO);

		// Verificar se a resposta é 400 Bad Request
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("CPF inválido.", response.getBody());
	}

	// Outros testes...
}
