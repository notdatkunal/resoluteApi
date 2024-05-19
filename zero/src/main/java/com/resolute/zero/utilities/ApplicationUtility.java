package com.resolute.zero.utilities;

import com.resolute.zero.exceptions.LogoutException;
import com.resolute.zero.domains.User;
import jakarta.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;


public class ApplicationUtility {




    public static String encryptPassword( String password ) {
        String encrypted = "";
        try {
            MessageDigest digest = MessageDigest.getInstance( "MD5" );
            byte[] passwordBytes = password.getBytes( );

            digest.reset( );
            digest.update( passwordBytes );
            byte[] message = digest.digest( );

            StringBuffer hexString = new StringBuffer();
            for ( int i=0; i < message.length; i++)
            {
                hexString.append( Integer.toHexString(
                        0xFF & message[ i ] ) );
            }
            encrypted = hexString.toString();
        }
        catch( Exception e ) {


        }
        return encrypted;
    }
}
