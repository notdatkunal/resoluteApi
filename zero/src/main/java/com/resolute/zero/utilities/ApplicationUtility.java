package com.resolute.zero.utilities;
import java.security.MessageDigest;

public class ApplicationUtility {

        private static String getOs(){
            return System.getProperty("os.name");
        }
        public static boolean checkIfLinux(){
            return getOs().equalsIgnoreCase("linux");
        }


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
