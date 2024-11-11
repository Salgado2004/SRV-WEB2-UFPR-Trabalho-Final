package br.ufpr.webII.trabalhoFinal.infra.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonFileService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Método para gravar um objeto em um arquivo JSON
    public void writeJsonToFile(String filePath, Object data) throws IOException {
        String pathname = "src/main/resources/static/json_database/"+filePath;
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(pathname), data);
    }

    public <T> T readObjectFromFile(String filePath, TypeReference<T> valueType) throws IOException{
        String pathname = "src/main/resources/static/json_database/"+filePath;
        return objectMapper.readValue(new File(pathname), valueType);
    }
}
