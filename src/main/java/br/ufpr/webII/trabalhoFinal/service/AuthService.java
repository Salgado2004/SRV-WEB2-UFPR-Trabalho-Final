package br.ufpr.webII.trabalhoFinal.service;

import br.ufpr.webII.trabalhoFinal.dto.CustomerDTO;
import br.ufpr.webII.trabalhoFinal.model.Customer;
import br.ufpr.webII.trabalhoFinal.model.Employee;
import br.ufpr.webII.trabalhoFinal.repository.CustomerRepository;
import br.ufpr.webII.trabalhoFinal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public Customer registerCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCpf(customerDTO.getCpf());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhone(customerDTO.getPhone());

        // Gera uma senha aleatória de 4 números
        String password = generateRandomPassword();
        customer.encryptPassword(password);

        // Aqui você pode adicionar lógica para enviar o e-mail com a senha

        return customer;
    }

    public Customer loginCustomer(String email, String password) {
        if(!isValidEmail(email)){
            return null;
        }

        Customer customer = customerRepository.findByEmail(email);
        if (customer != null && customer.checkPassword(password)) {
            return customer;
        }
        return null;
    }

    public Employee loginEmployee(String email, String password) {
        if(!isValidEmail(email)){
            return null;
        }


        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null && employee.checkPassword(password)) {
            return employee;
        }
        return null;
    }

    private String generateRandomPassword() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // Senha de 4 dígitos
    }

    // Função para validação de CPF
    static public boolean isValidCpf(String cpf) {
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
    static public boolean isValidEmail(String email) {
        // Verifica se o e-mail não é nulo e se contém o símbolo "@" seguido de um domínio.
        return email != null && email.contains("@") && email.matches("[^@\\s]+@[^@\\s]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)?");
    }

}
