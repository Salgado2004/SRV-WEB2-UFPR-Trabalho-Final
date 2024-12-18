package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.email.MessageDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.CustomerInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.domain.user.User;
import br.ufpr.webII.trabalhoFinal.domain.user.UserLoginDTO;
import br.ufpr.webII.trabalhoFinal.infra.connection.CustomerDao;
import br.ufpr.webII.trabalhoFinal.infra.connection.DaoFactory;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.LoginException;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RegisteringException;
import br.ufpr.webII.trabalhoFinal.infra.connection.UserDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private DaoFactory daoFactory;

    @Autowired TokenService tokenService;
    
    @Autowired
    EmailService smailService;

    public Customer registerCustomer(@Valid CustomerInputDTO customerInputDTO) {
        Customer customer = new Customer(customerInputDTO);

        // Gera uma senha aleatória de 4 números
        String password = generateRandomPassword();
        customer.encryptPassword(password); // Aqui você deve hash a senha antes de armazená-la

        try {
            CustomerDao customerDao = daoFactory.getCustomerDao();
            customerDao.insert(customer);
        } catch (IllegalArgumentException e){
            throw new RegisteringException(e.getMessage());
        } catch (Exception e){
            throw new RegisteringException("Erro ao salvar cliente", e);
        }

        smailService.setSenha(password);
        smailService.sendEmail(new MessageDTO(customerInputDTO.name(), customerInputDTO.email()));
        return customer;
    }


    public UserLoginDTO login(String email, String password) {
        this.isValidEmail(email);

        UserDao userDao = daoFactory.getUserDao();
        User user = userDao.findByEmail(email);
        if (user == null || !user.checkPassword(password)) {
            throw new LoginException("Credenciais inválidas", 1);
        }
        return new UserLoginDTO(tokenService.getToken(user));
    }

    // Função para validação de CPF
    public void isValidCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new RegisteringException("CPF inválido");
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

        if(!(cpfDigits[9] == firstDigit && cpfDigits[10] == secondDigit)){
            throw new RegisteringException("CPF inválido");
        }
    }

    // Função para validação de e-mail
    public void isValidEmail(String email) {
        // Verifica se o e-mail não é nulo e se contém o símbolo "@" seguido de um domínio.
        if (email == null || !email.contains("@") || !email.matches("[^@\\s]+@[^@\\s]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)?")){
            throw new RegisteringException("E-mail inválido");
        }
    }

    private String generateRandomPassword() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // Senha de 4 dígitos
    }

}
