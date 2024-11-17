package br.ufpr.webII.trabalhoFinal.infra.config;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;

public class EnvConfig {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().load();
    }
}

