package com.resolute.zero.utilities;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(dateString);
    }
}
