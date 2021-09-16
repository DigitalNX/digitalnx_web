package com.digitalnx.resource.auth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtKeyGenerator {
    private static JwtKeyGenerator  jwtKeyGenerator = null;
    public Key key;

    private JwtKeyGenerator() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public static JwtKeyGenerator getInstance() {
        if(jwtKeyGenerator == null) {
            jwtKeyGenerator = new JwtKeyGenerator();
        }
        return jwtKeyGenerator;
    }
}
