package br.ufpr.webII.trabalhoFinal;

import br.ufpr.webII.trabalhoFinal.controller.AuthController;
import br.ufpr.webII.trabalhoFinal.domain.model.Address;
import br.ufpr.webII.trabalhoFinal.infra.service.AuthService;
import br.ufpr.webII.trabalhoFinal.domain.dto.CustomerInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrabalhoFinalApplicationTests {

	private AuthController authController;
	private AuthService authService;
	private Address address;

	@BeforeEach
	void setUp() {
		// Criar um endereço de exemplo
		address = new Address();
		address.setCep("1234567");
		address.setUf("Estado");
		address.setCity("Cidade");
		address.setDistrict("Bairro");
		address.setStreet("Rua"); // Substitua por métodos reais de Address
		address.setNumber(1);
		authService = new AuthService(); // Criação da instância real do AuthService
		authController = new AuthController(); // Passando o AuthService para o AuthController
	}

	@Test
	void testRegisterCustomerSuccess() {
		// Criar um CustomerDTO de teste
		CustomerInputDTO customerInputDTO = new CustomerInputDTO("12345678909","John","Doe","test@dominio.com", address,"4002-8922");

		// Chamar o método de registro
		ResponseEntity<String> response = authController.register(customerInputDTO);

		// Verificar se a resposta é 201 Created
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Cliente cadastrado com sucesso!", response.getBody());
	}

	@Test
	void testRegisterCustomerInvalidEmail() {
		// Criar um CustomerDTO com email inválido
		CustomerInputDTO customerInputDTO = new CustomerInputDTO("12345678909","John","Doe","aaaaaaaaaaaaaaa", address,"4002-8922");

		// Chamar o método de registro
		ResponseEntity<String> response = authController.register(customerInputDTO);

		// Verificar se a resposta é 400 Bad Request
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("E-mail inválido.", response.getBody());
	}

	@Test
	void testRegisterCustomerInvalidCpf() {
		// Criar um CustomerDTO com CPF inválido
		CustomerInputDTO customerInputDTO = new CustomerInputDTO("12345678910","John","Doe","test@dominio.com", address,"4002-8922");

		// Chamar o método de registro
		ResponseEntity<String> response = authController.register(customerInputDTO);

		// Verificar se a resposta é 400 Bad Request
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("CPF inválido.", response.getBody());
	}

	// Outros testes...
}
