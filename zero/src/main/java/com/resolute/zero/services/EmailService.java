package com.resolute.zero.services;

import com.resolute.zero.domains.EmailDetails;
import com.resolute.zero.domains.TemplateRepository;
import com.resolute.zero.helpers.TemplateType;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.utilities.ApplicationUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class EmailService {
    @Autowired
    private TemplateRepository templateRepository;
    public static final String JMSWIFTAPP_GMAIL = "jmswiftapp@gmail.com";
    @Autowired
    private JavaMailSender mailSender;
    public boolean sendEmail(EmailDetails emailDetails) throws Exception {
        emailDetails.setFrom(JMSWIFTAPP_GMAIL);
        try{
            sendMail(emailDetails);
            return true;
        }catch (Exception e ) {
            throw new Exception("Exception Occured in email service, Error: "+ e.getMessage());
        }
    }

    private void sendMail(EmailDetails emailDetails) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailDetails.getFrom());
        message.setTo(emailDetails.getTo());
        message.setText(emailDetails.getBody());
        message.setSubject(emailDetails.getSubject());
        mailSender.send(message);
        System.out.println("Mail Send...");
    }

    @SneakyThrows
    public void sendBulkEmail(MultipartFile sheet) {
        List<EmailDetails> emailDetailsList = new ArrayList<>();
        var template = templateRepository.findByType(TemplateType.EMAIL).get().getTemplate();
        try (InputStream inputStream = sheet.getInputStream()) {
            List<String> headers = new ArrayList<>();
            // Use the InputStream to read the Excel file
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            log.info(workbook.getSheetName(0));
            // Process the workbook here using the steps mentioned previously
            XSSFSheet sh = workbook.getSheetAt(0);
            XSSFRow headerRow = sh.getRow(0);
            if(headerRow!=null) {
                for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
                    XSSFCell cell = headerRow.getCell(cellIndex);

                    // Check if cell exists and extract value
                    if (cell != null) {
                        String headerValue = cell.getStringCellValue();
                        headers.add(headerValue);
                    }
                }
            }

            int startingRow = 1;
            for (int rowIndex = startingRow; rowIndex <= sh.getLastRowNum(); rowIndex++) {
                XSSFRow dataRow = sh.getRow(rowIndex);
                if (dataRow != null) {
                    var builder = EmailDetails.builder();
                    builder.from(JMSWIFTAPP_GMAIL);
                    builder.body(template);
                    for (int cellIndex = 0; cellIndex < dataRow.getLastCellNum(); cellIndex++) {
                        XSSFCell cell = dataRow.getCell(cellIndex);
                        if (cell != null) {
                                    switch (cellIndex){
                                        case 0 -> builder.to(ApplicationUtility.getStringValue(cell));
                                        case 1 -> builder.cc(ApplicationUtility.getStringValue(cell));
                                        case 2 -> builder.bcc(ApplicationUtility.getStringValue(cell));
                                        case 3 -> builder.subject(ApplicationUtility.getStringValue(cell));
                                        default ->  log.warn("Unmapped column index: {}", cellIndex);
                                    }
                                }
                            }
                    emailDetailsList.add(builder.build());
                        }
                    }
                } catch (IOException e) {
                        e.printStackTrace();
                }
            emailDetailsList.forEach(email->{
                try {
                    sendEmail(email);
                }catch (Exception e){
                    log.warn("exception occured during sending {}", email.getTo());
                }
            });
    }
}
