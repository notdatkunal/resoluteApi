package com.resolute.zero.excel;

import com.resolute.zero.responses.AdminCaseResponse;
import com.resolute.zero.services.AdminService;
import com.resolute.zero.services.CaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ExcelService {

    @Autowired
    private CaseService caseService;
    @Autowired
    private AdminService adminService;
    private enum headers { id,
     bankId,
//     sec17OrderDate,
    arbitratorId
        ,state
    ,caseType
    ,caseNo
    , zone
    ,branchName
    ,customerId
    ,accountNumber
    , creditCardNumber
    ,customerName
    ,actualProduct
    ,flagProductGroup
//    ,natureOfLegalAction
    ,totalTos
    ,totalTosInCr
    ,noticeDate
//    ,refLetter
    ,socFillingDate
    ,claimAmountInSOC
    ,firstHearingDate
    ,lastHearingDate
    ,nextHearingDate
    ,stagesOfLastHearingDate
    ,stagesOfNextHearingDate
    ,caseStatus
    ,flagForContested
    ,detailsRemark
    ,awardDate
    ,awardAmount
    ,sec17AppFillingDate
    ,sec17AppStatus
    ,courtName
    ,place
    ,arbitrator
    ,lawyerName
    ,lmName
   ,registrationDate};
    public ByteArrayInputStream getExcelDataByBankId(Integer bankId) throws IOException {
        var rowIndex = 0;
        var cases = caseService.getCasesByBankId(bankId);
        var bank = adminService.getBankById(bankId);
        var workbook = new XSSFWorkbook();
        var out = new ByteArrayOutputStream();
        try{
            var sheet = workbook.createSheet(bank.getBankName());
            var headerRow = sheet.createRow(rowIndex++);
            var headerValues = List.of(headers.values());
            createExcelHeaders(headerValues, headerRow);
            insertCaseValues(cases, sheet, rowIndex, headerValues);
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
        finally {
           workbook.close();
           out.close();
        }
    }

    private static void insertCaseValues(List<AdminCaseResponse> cases, Sheet sheet, int rowIndex, List<headers> headerValues) {
        for(var bankCase: cases){
            var row = sheet.createRow(rowIndex++);
            convertCaseDataToSheetData(headerValues, bankCase, row);

        }
    }

    private static void convertCaseDataToSheetData(List<headers> headerValues, AdminCaseResponse bankCase, Row row) {
        row.createCell(headerValues.indexOf(headers.id)).setCellValue(bankCase.getId());
        row.createCell(headerValues.indexOf(headers.bankId)).setCellValue(bankCase.getBankId());
//        row.createCell(headerValues.indexOf(headers.sec17OrderDate)).setCellValue(bankCase.getSec17OrderDate());
        row.createCell(headerValues.indexOf(headers.arbitratorId)).setCellValue(bankCase.getArbitrator());
        row.createCell(headerValues.indexOf(headers.state)).setCellValue(bankCase.getState());
        row.createCell(headerValues.indexOf(headers.caseType)).setCellValue(bankCase.getCaseType());
        row.createCell(headerValues.indexOf(headers.caseNo)).setCellValue(bankCase.getCaseNo());
        row.createCell(headerValues.indexOf(headers.zone)).setCellValue(bankCase.getZone());
        row.createCell(headerValues.indexOf(headers.branchName)).setCellValue(bankCase.getBranchName());
        row.createCell(headerValues.indexOf(headers.customerId)).setCellValue(bankCase.getCustomerId());
        row.createCell(headerValues.indexOf(headers.accountNumber)).setCellValue(bankCase.getAccountNumber());
        row.createCell(headerValues.indexOf(headers.creditCardNumber)).setCellValue(bankCase.getCreditCardNumber());
        row.createCell(headerValues.indexOf(headers.customerName)).setCellValue(bankCase.getCustomerName());
        row.createCell(headerValues.indexOf(headers.actualProduct)).setCellValue(bankCase.getActualProduct());
        row.createCell(headerValues.indexOf(headers.flagProductGroup)).setCellValue(bankCase.getFlagProductGroup());
//        row.createCell(headerValues.indexOf(headers.natureOfLegalAction)).setCellValue(bankCase.getNatureOfLegalAction());
        row.createCell(headerValues.indexOf(headers.totalTos)).setCellValue(bankCase.getTotalTos());
        if(bankCase.getTotalTosInCr()!=null) row.createCell(headerValues.indexOf(headers.totalTosInCr)).setCellValue(bankCase.getTotalTosInCr());
        if(bankCase.getNoticeDate()!=null)row.createCell(headerValues.indexOf(headers.noticeDate)).setCellValue(bankCase.getNoticeDate().toString());
//        row.createCell(headerValues.indexOf(headers.refLetter)).setCellValue(bankCase.getRefLetter());
        if(bankCase.getSocFillingDate()!=null)row.createCell(headerValues.indexOf(headers.socFillingDate)).setCellValue(bankCase.getSocFillingDate().toString());
        if(bankCase.getClaimAmountInSOC()!=null) row.createCell(headerValues.indexOf(headers.claimAmountInSOC)).setCellValue(bankCase.getClaimAmountInSOC());
        if(bankCase.getFirstHearingDate()!=null) row.createCell(headerValues.indexOf(headers.firstHearingDate)).setCellValue(bankCase.getFirstHearingDate().toString());
        if(bankCase.getLastHearingDate()!=null)  row.createCell(headerValues.indexOf(headers.lastHearingDate)).setCellValue(bankCase.getLastHearingDate().toString());
        if(bankCase.getNextHearingDate()!=null)row.createCell(headerValues.indexOf(headers.nextHearingDate)).setCellValue(bankCase.getNextHearingDate().toString());
        row.createCell(headerValues.indexOf(headers.stagesOfLastHearingDate)).setCellValue(bankCase.getStagesOfLastHearingDate());
        row.createCell(headerValues.indexOf(headers.stagesOfNextHearingDate)).setCellValue(bankCase.getStagesOfNextHearingDate());
        row.createCell(headerValues.indexOf(headers.caseStatus)).setCellValue(bankCase.getCaseStatus());
        row.createCell(headerValues.indexOf(headers.flagForContested)).setCellValue(bankCase.getFlagForContested());
        row.createCell(headerValues.indexOf(headers.detailsRemark)).setCellValue(bankCase.getDetailsRemark());
        if(bankCase.getAwardDate()!=null)row.createCell(headerValues.indexOf(headers.awardDate)).setCellValue(bankCase.getAwardDate().toString());
        row.createCell(headerValues.indexOf(headers.awardAmount)).setCellValue(bankCase.getAwardAmount());
        if(bankCase.getSec17AppFillingDate()!=null)row.createCell(headerValues.indexOf(headers.sec17AppFillingDate)).setCellValue(bankCase.getSec17AppFillingDate().toString());
        row.createCell(headerValues.indexOf(headers.sec17AppStatus)).setCellValue(bankCase.getSec17AppStatus());
        row.createCell(headerValues.indexOf(headers.courtName)).setCellValue(bankCase.getCourtName());
        row.createCell(headerValues.indexOf(headers.place)).setCellValue(bankCase.getPlace());
        row.createCell(headerValues.indexOf(headers.arbitrator)).setCellValue(bankCase.getArbitrator());
        row.createCell(headerValues.indexOf(headers.lawyerName)).setCellValue(bankCase.getLawyerName());
        row.createCell(headerValues.indexOf(headers.lmName)).setCellValue(bankCase.getLmName());
        if(bankCase.getRegistrationDate()!=null)row.createCell(headerValues.indexOf(headers.registrationDate)).setCellValue(bankCase.getRegistrationDate().toString());
    }

    private static void createExcelHeaders(List<headers> headerValues, Row headerRow) {
        for(var header : headerValues) {
            var cell = headerRow.createCell(headerValues.indexOf(header));
            cell.setCellValue(header.toString());
        };
    }
}
