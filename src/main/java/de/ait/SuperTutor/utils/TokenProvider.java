package de.ait.SuperTutor.utils;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TokenProvider {

    private static final String FIREBASE_API_KEY = "AIzaSyA3Odi_0ot-bzaAc0sGNdQ2YdkEciMIe_M"; // замени на свой API Key

    public static String getFirebaseIdToken(String email, String password) {
        try {
            URL url = new URL(
                    "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + FIREBASE_API_KEY
            );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            JSONObject payload = new JSONObject();
            payload.put("email", email);
            payload.put("password", password);
            payload.put("returnSecureToken", true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes("UTF-8"));
            }

            InputStream is = (conn.getResponseCode() == 200) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JSONObject jsonResponse = new JSONObject(response.toString());

            if (conn.getResponseCode() == 200) {
                return jsonResponse.getString("idToken");
            } else {
                throw new RuntimeException("Ошибка Firebase: " + jsonResponse.toString());
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении Firebase токена", e);
        }
    }
}
