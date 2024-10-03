package br.ufpr.webII.trabalhoFinal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Método para gravar um objeto em um arquivo JSON
    public static void writeJsonToFile(String filePath, Object data) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
    }
}
