package com.resolute.zero.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;

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
    //token validation
    public static boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
            ;
            return true;
        }
        catch (SignatureException se){
                se.printStackTrace();
        }
        catch (ExpiredJwtException ee){
            ee.printStackTrace();
        }
        return false;

    }

    //extract username
    public static String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();


    }
}
