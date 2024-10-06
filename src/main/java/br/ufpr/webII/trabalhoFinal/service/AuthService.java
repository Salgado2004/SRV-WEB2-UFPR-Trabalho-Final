package br.ufpr.webII.trabalhoFinal.service;

import br.ufpr.webII.trabalhoFinal.dto.CustomerDTO;
import br.ufpr.webII.trabalhoFinal.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    public Customer registerCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCpf(customerDTO.getCpf());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhone(customerDTO.getPhone());

        // Gera uma senha aleatória de 4 números
        String password = generateRandomPassword();
        customer.setPassword(password); // Aqui você deve hash a senha antes de armazená-la

        // Aqui você pode adicionar lógica para enviar o e-mail com a senha

        return customer;
    }

    // Função para validação de CPF
    public boolean isValidCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        int[] cpfDigits = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfDigits[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += cpfDigits[i] * (10 - i);
        }
        int firstDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += cpfDigits[i] * (11 - i);
        }
        int secondDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        return cpfDigits[9] == firstDigit && cpfDigits[10] == secondDigit;
    }

    // Função para validação de e-mail
    public boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.matches("[^@\\s]+@[^@\\s]+\\.com");
    }

    private String generateRandomPassword() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // Senha de 4 dígitos
    }
}
