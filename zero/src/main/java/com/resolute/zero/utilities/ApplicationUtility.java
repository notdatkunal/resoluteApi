package com.resolute.zero.utilities;
import com.resolute.zero.requests.AdminCaseRequest;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class ApplicationUtility {


    public static String processWordFile(MultipartFile wordFile) throws IOException {
        XWPFDocument document = new XWPFDocument(wordFile.getInputStream());
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        StringBuilder content = new StringBuilder();
        for (XWPFParagraph paragraph : paragraphs) {
            content.append(paragraph.getText()).append("\n");
        }

        return content.toString();
    }

    public static String processSheetFile(MultipartFile sheetFile) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(sheetFile.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0); // Assuming first sheet

        StringBuilder content = new StringBuilder();
        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                String cellValue = row.getCell(cellNum) != null ? row.getCell(cellNum).getStringCellValue() : "";
                content.append(cellValue).append("\t");
            }
            content.append("\n");
        }

        return content.toString();
    }



    public static long getDifferenceInMinutes(Instant start, Instant end) {
        // Ensure end Instant is after start for positive difference
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start Instant cannot be after end Instant");
        }
        Duration duration = Duration.between(start, end);
        return duration.toMinutes(); // Round up to nearest minute
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

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(dateString);
    }
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
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
        return request.getBankId()!=null&&request.getCaseNo()!=null&&request.getCaseType()!=null&&request.getCustomerId()!=null&&request.getCustomerName()!=null;
    }
}
