package com.unyx.ticketeira;

import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class JwtSecretKeyGenerator {
    public static void main(String[] args) {
        // Gera uma chave secreta segura para HS256
        byte[] key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(key);

        System.out.println("Generated Secret Key: " + base64Key);
    }
}
