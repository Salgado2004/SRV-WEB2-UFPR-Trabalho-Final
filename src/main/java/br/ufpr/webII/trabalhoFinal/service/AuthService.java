package br.ufpr.webII.trabalhoFinal.service;

import br.ufpr.webII.trabalhoFinal.model.dto.CustomerInputDTO;
import br.ufpr.webII.trabalhoFinal.model.Customer;
import br.ufpr.webII.trabalhoFinal.model.User;
import br.ufpr.webII.trabalhoFinal.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserDao userDao;

    /*@Autowired
    private UserRepository userRepository;*/

    public Customer registerCustomer(CustomerInputDTO customerInputDTO) {
        Customer customer = new Customer(customerInputDTO);

        // Gera uma senha aleatória de 4 números
        String password = generateRandomPassword();
        System.out.println(password);
        customer.encryptPassword(password); // Aqui você deve hash a senha antes de armazená-la
        userDao.save(customer);

        // Aqui você pode adicionar lógica para enviar o e-mail com a senha

        return customer;
    }


    public <T extends User> User login(String email, String password) {
        if(isInvalidEmail(email)){
            return null;
        }

        T user = (T) userDao.findByEmail(email);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
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

    private String generateRandomPassword() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // Senha de 4 dígitos
    }

    // Função para validação de e-mail
    public boolean isInvalidEmail(String email) {
        // Verifica se o e-mail não é nulo e se contém o símbolo "@" seguido de um domínio.
        return email == null || !email.contains("@") || !email.matches("[^@\\s]+@[^@\\s]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)?");
    }

}
