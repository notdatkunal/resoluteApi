package com.resolute.zero.utilities;
import com.resolute.zero.requests.AdminCaseRequest;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
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

    // Helper methods to extract cell values
    public static Integer getIntValue(XSSFCell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return null; // Handle non-numeric values (optional)
    }

    public static String getStringValue(XSSFCell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return null; // Handle non-string values (optional)
    }

    public static boolean checkCase(AdminCaseRequest request) {
        System.out.println(request.getBankId()!=null);
        System.out.println(request.getCaseNo()!=null);
        System.out.println(request.getCaseType()!=null);
        System.out.println(request.getCustomerId()!=null);
        System.out.println(request.getCustomerId());
        System.out.println(request.getCustomerName()!=null);
        return request.getBankId()!=null&&request.getCaseNo()!=null&&request.getCaseType()!=null&&request.getCustomerId()!=null&&request.getCustomerName()!=null;
    }
}
