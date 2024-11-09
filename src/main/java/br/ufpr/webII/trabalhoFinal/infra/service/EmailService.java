package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.email.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender sender;

    public void sendEmail(MessageDTO messageDTO){
        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(messageDTO.getFrom());
            message.setTo(messageDTO.getTo());
            message.setSubject(messageDTO.getTitle());
            message.setText(messageDTO.getText());

            sender.send(message);

        } catch (MailException ex){
            System.out.println(("Erro no envio do email!"));
        }

    }
}
