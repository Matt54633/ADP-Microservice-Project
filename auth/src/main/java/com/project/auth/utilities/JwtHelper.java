package com.project.auth.utilities;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

// JWT Helper (provided from Spring Boot Labs)
// Use admin@example.com as the issuer

public class JwtHelper {
    public static String createToken(String scopes) {

        try {
            Algorithm algorithm = Algorithm.HMAC256("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDV/WRLZgX9y2TN2B7bsWmsqc32VBhEf5zRWO+pGP84mQwYW56EXG7xiu1dzUaV2B/V2/xyaecZrOSD6SMi1qD87LdtbJ7Na4QRowvujXILFu1vmtq1v1VPtzcyMr+L5NG/iF3yCuEkRvDy");
            long fiveHoursInMillis = 1000 * 60 * 60 * 5;

            Date expireDate = new Date(System.currentTimeMillis() + fiveHoursInMillis);

            String token = JWT.create()
                    .withSubject("Admin")
                    .withIssuer("admin@example.com")
                    .withClaim("scopes", scopes)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public static boolean verifyToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDV/WRLZgX9y2TN2B7bsWmsqc32VBhEf5zRWO+pGP84mQwYW56EXG7xiu1dzUaV2B/V2/xyaecZrOSD6SMi1qD87LdtbJ7Na4QRowvujXILFu1vmtq1v1VPtzcyMr+L5NG/iF3yCuEkRvDy");

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("admin@example.com")
                    .build();

            verifier.verify(token);

            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }

    }

    public static Map<String, Claim> getClaims(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDV/WRLZgX9y2TN2B7bsWmsqc32VBhEf5zRWO+pGP84mQwYW56EXG7xiu1dzUaV2B/V2/xyaecZrOSD6SMi1qD87LdtbJ7Na4QRowvujXILFu1vmtq1v1VPtzcyMr+L5NG/iF3yCuEkRvDy");

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("admin@example.com")
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            return jwt.getClaims();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public static String getScopes(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDV/WRLZgX9y2TN2B7bsWmsqc32VBhEf5zRWO+pGP84mQwYW56EXG7xiu1dzUaV2B/V2/xyaecZrOSD6SMi1qD87LdtbJ7Na4QRowvujXILFu1vmtq1v1VPtzcyMr+L5NG/iF3yCuEkRvDy");

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("admin@example.com")
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("scopes").asString();

        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
