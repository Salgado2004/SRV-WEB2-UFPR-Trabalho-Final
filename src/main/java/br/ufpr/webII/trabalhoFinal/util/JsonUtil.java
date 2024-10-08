package br.ufpr.webII.trabalhoFinal.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Método para gravar um objeto em um arquivo JSON
    public static void writeJsonToFile(String filePath, Object data) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(resource.getURI()), data);
    }
}
