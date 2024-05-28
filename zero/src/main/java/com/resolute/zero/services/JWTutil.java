package com.resolute.zero.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTutil
{

    private static final String SECRET = "YourSecretKeyToMyAPIYourSecretKeyToMyAPIYourSecretKeyToMyAPIYourSecretKeyToMyAPIYourSecretKeyToMyAPI";
    private static final long EXP_TIME_IN_10_DAYS = 864_000_000;
    public static  String generateToken(String username){
        return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis()+EXP_TIME_IN_10_DAYS))
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
    }
}
