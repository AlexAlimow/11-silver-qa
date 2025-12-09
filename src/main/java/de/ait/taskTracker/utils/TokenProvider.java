package de.ait.taskTracker.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Map;

public class TokenProvider {

    private static Map<String, String> tokens;

    static {
        try (InputStream is = TokenProvider.class.getResourceAsStream("/mock_tokens.json")) {
            ObjectMapper mapper = new ObjectMapper();
            tokens = mapper.readValue(is, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось прочитать mock_tokens.json", e);
        }
    }

    public static String getToken(String email) {
        return tokens.get(email);
    }
}