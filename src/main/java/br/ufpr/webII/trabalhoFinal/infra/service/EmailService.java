package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.email.MessageDTO;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.CustomerInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private String senha;
    
    public void setSenha(String senha){
        this.senha = senha;
    }

    private final JavaMailSender sender;
    
    String emailBody(String senha){
        String text ="Olá!\n\n"
                + "Seja bem vindo ao melhor local para sua manutenção. Agradecemos o cadastro e estamos ansiosos para lhe atender.\n\n"
                + "A sua senha se encontra abaixo (não a perca ou o acesso ao nosso serviço será totalmente perdido para esse email)\n\n"
                + senha
                + "\n\nAtenciosamente,\nTime ManuTADS";
        
        return text;
    }

    public void sendEmail(MessageDTO messageDTO){
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("manutads.noreply@gmail.com");
            message.setTo(messageDTO.getTo());
            message.setSubject("Novo usuário e senha gerados!");
            message.setText(emailBody(senha));

            sender.send(message);

        } catch (MailException ex){
            System.out.println(("Erro no envio do email!"+ex.getMessage()));
        }

    }
}
