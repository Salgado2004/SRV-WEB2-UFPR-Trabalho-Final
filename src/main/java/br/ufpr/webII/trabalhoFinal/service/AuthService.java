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

    private String generateRandomPassword() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // Senha de 4 dígitos
    }
}
