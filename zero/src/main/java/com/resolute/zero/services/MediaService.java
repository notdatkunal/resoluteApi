package com.resolute.zero.services;


import com.resolute.zero.domains.BankCase;
import com.resolute.zero.domains.BankCaseRepository;
import com.resolute.zero.domains.Document;
import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.repositories.*;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.utilities.ApplicationUtility;
import com.resolute.zero.utilities.MediaUtility;
import com.resolute.zero.utilities.MetaDocInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
@Service
public class MediaService {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ArbitratorRepository arbitratorRepository;
    @Autowired
    private BankCaseRepository bankCaseRepository;

    public void uploadFile(MetaDocInfo metaDocInfo) throws IOException {
        var name = metaDocInfo.getFile().getOriginalFilename();
        if (name == null) throw new AssertionError();
        String format = name.split("\\.")[1];
        Files.createDirectories(Path.of("media"));
        Path uploadTo = Path.of(String.format("media/%s.%s", metaDocInfo.getCode(),format));
        metaDocInfo.getFile().transferTo(uploadTo);
        metaDocInfo.setFileName(uploadTo.getFileName().toString());
    }
    public List<String> uploadFiles(MultipartFile[] files) throws IOException, ExecutionException, InterruptedException {
        Files.createDirectories(Path.of("media"));
        List<String> list = new ArrayList<>();
        list = Collections.synchronizedList(list);
        for (MultipartFile file : files){
            list.add(saveSingleFile(file).get());
        }
        return list.stream().toList();
    }

    @Async("taskExecutor")
    protected Future<String> saveSingleFile(MultipartFile file) throws IOException {
        Path uploadTo = Path.of(String.format("media/%s", file.getOriginalFilename()));
        file.transferTo(uploadTo);
        return CompletableFuture.completedFuture(file.getOriginalFilename());
    }

    public void saveDocumentsInDB(List<MetaDocInfo> metaDocsInfo) {

        for(MetaDocInfo metaDocInfo:metaDocsInfo)
        {
            this.saveDocumentInDB(metaDocInfo);

        }
    }

    @Autowired
    private CaseRepository caseRepository;

    @Async("taskExecutor")
    public Future<ResponseEntity<?>> saveDocumentInDB(MetaDocInfo metaDocInfo) {
        System.out.println(metaDocInfo);
        Document document = new Document();
        document.setDocumentMainTypeTitle(metaDocInfo.getMainType());
        document.setDocumentSubTypeTitle(metaDocInfo.getSubType());
        document.setCaseId(metaDocInfo.getCaseId());
        document.setImageName(metaDocInfo.getFileName());
        document.setFileExtension(metaDocInfo.getFileExtension());
        var caseOptional = caseRepository.findById(metaDocInfo.getCaseId());
        if(caseOptional.isPresent()){
            var caseObj = caseOptional.get();
            var documentsList = caseObj.getDocumentList();
            documentsList.add(document);
            caseObj.setDocumentList(documentsList);
            documentRepository.save(document);
            caseRepository.save(caseObj);
        }else {
             return CompletableFuture.completedFuture(
                     ResponseEntity.notFound()
                     .eTag("case id not found")
                     .build());
        }
        if(metaDocInfo.getHearingId()!=null && metaDocInfo.getHearingId() !=0){
            var proceedingOpt = proceedingRepository.findById(metaDocInfo.getHearingId());
            if(proceedingOpt.isEmpty())
                throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,"proceeding not found")).build())
                    .build();
            var proceeding = proceedingOpt.get();
            if (metaDocInfo.getMainType().equals("recording")) {
                proceeding.setMeetingRecordings(metaDocInfo.getFileName());
            } else {
                proceeding.setMinutesOfMeetings(metaDocInfo.getFileName());
            }
            proceedingRepository.save(proceeding);
        }
        return CompletableFuture.completedFuture(
                ResponseEntity.ok(metaDocInfo.getFileName()+" saved in server ")
        );

    }

    public ResponseEntity<String> getFile(String fileName) throws IOException {
        // Path to the media folder (replace with your actual path)
                String mediaPath= MediaUtility.getPath(fileName);
        // Read the file as bytes
        var fileInputStream = new FileInputStream(mediaPath);
        byte[] fileBytes = IOUtils.toByteArray(fileInputStream);
        String mimeType;
//        String mimeType = "";
        Tika tika = new Tika();
        try {
            // Get MIME type
            var file = new File(mediaPath);
            mimeType = tika.detect(file);

        } catch (IOException e) {
            e.printStackTrace();
            mimeType = "application/octet-stream";
        }
//        String format = fileName.split("\\.")[1];
        String bytes = Base64.getEncoder().encodeToString(fileBytes);
        // Convert bytes to Base64 string
        String base64Encoded = "data:"+mimeType+";base64,"+bytes;

        // Prepare and return the response
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(base64Encoded);
    }

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ProceedingRepository proceedingRepository;

    public List<Document> getDocsByCaseId(Integer caseId) {
       return documentRepository.findByCaseId(caseId);
    }

    public ResponseEntity<?> deleteDocument(Integer documentId) {
        try {
            var document = documentRepository.findById(documentId).get();
            var caseObj = caseRepository.findById(document.getCaseId()).get();
            caseObj.getDocumentList().remove(document);
            String path = MediaUtility.getPath(document.getImageName());
            File fileToDelete = new File(path);
            var resultFileDeleted = fileToDelete.delete();
            log.info("result as follows for deletion {} ",resultFileDeleted);
            caseRepository.save(caseObj);
            documentRepository.deleteById(documentId);
            return ResponseEntity.ok(ProblemDetail.forStatus(HttpStatus.FOUND));
        }
        catch (Exception e){
            return ResponseEntity
                    .of(ProblemDetail
                            .forStatusAndDetail(HttpStatus
                                    .REQUESTED_RANGE_NOT_SATISFIABLE,
                                    e.toString()))
                    .build();
        }
    }
    public List<AdminCaseRequest> updateCases(MultipartFile sheet) {
        List<AdminCaseRequest> caseSheetRequests = new ArrayList<>();
        try (InputStream inputStream = sheet.getInputStream()) {

            // Process the workbook here using the steps mentioned previously
            XSSFSheet sh = EmailService.getRows(inputStream);

            int startingRow = 1;
            for (int rowIndex = startingRow; rowIndex <= sh.getLastRowNum(); rowIndex++) {
                XSSFRow dataRow = sh.getRow(rowIndex);
                if (dataRow != null) {
                    var builder = AdminCaseRequest.builder();
                    for (int cellIndex = 0; cellIndex < dataRow.getLastCellNum(); cellIndex++) {
                        XSSFCell cell = dataRow.getCell(cellIndex);
                        adminBuilder(cell, cellIndex, builder);
                    }
                    builder.rowNum(rowIndex+1);
                    caseSheetRequests.add(builder.build());
                }
            }
            log.info(sh.getHeader().toString());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle potential IO exceptions
        }
        AtomicBoolean check = new AtomicBoolean(true);
        caseSheetRequests.forEach(request->
            check.set(ApplicationUtility.checkCase(request))
        );
        System.out.println("completed fetching data");
        if(!check.get()){
            return new ArrayList<>();
        }
        var bankCases = new LinkedList<BankCase>();
        caseSheetRequests.forEach(caseRequest-> {
            var caseOptional = bankCaseRepository.findByCaseNo(caseRequest.getCaseNo());
            if(caseOptional.isEmpty())return;
            var caseDataToBeUpdated = caseOptional.get();
            if(caseRequest.getArbitratorId()!=null&&caseRequest.getArbitratorId()==0) caseDataToBeUpdated.setArbitrator(null);
            else if(caseRequest.getArbitratorId()!=null) caseDataToBeUpdated.setArbitrator(arbitratorRepository.getReferenceById(caseRequest.getArbitratorId()));
            if(caseRequest.getState()!=null) caseDataToBeUpdated.setState(caseRequest.getState());
            if(caseRequest.getBankId()!=null) caseDataToBeUpdated.setBank(bankRepository.getReferenceById(caseRequest.getBankId()));
            if(caseRequest.getCaseType()!=null) caseDataToBeUpdated.setCaseType(caseRequest.getCaseType());
            if(caseRequest.getZone()!=null) caseDataToBeUpdated.setZone(caseRequest.getZone());
            if(caseRequest.getBranchName()!=null) caseDataToBeUpdated.setBranchName(caseRequest.getBranchName());
            if(caseRequest.getCustomerId()!=null) caseDataToBeUpdated.setCustomerId(caseRequest.getCustomerId());
            if(caseRequest.getAccountNumber()!=null) caseDataToBeUpdated.setAccountNumber(caseRequest.getAccountNumber());
            if(caseRequest.getCreditCardNumber()!=null) caseDataToBeUpdated.setCreditCardNumber(caseRequest.getCreditCardNumber());
            if(caseRequest.getCustomerName()!=null) caseDataToBeUpdated.setCustomerName(caseRequest.getCustomerName());
            if(caseRequest.getActualProduct()!=null) caseDataToBeUpdated.setActualProduct(caseRequest.getActualProduct());
            if(caseRequest.getFlagProductGroup()!=null) caseDataToBeUpdated.setFlagProductGroup(caseRequest.getFlagProductGroup());
            if(caseRequest.getNatureOfLegalAction()!=null) caseDataToBeUpdated.setNatureOfLegalAction(caseRequest.getNatureOfLegalAction());
            if(caseRequest.getTotalTos()!=null) caseDataToBeUpdated.setTotalTos(caseRequest.getTotalTos());
            if(caseRequest.getTotalTosInCr()!=null) caseDataToBeUpdated.setTotalTosInCr(caseRequest.getTotalTosInCr());
            if(caseRequest.getClaimAmountInSOC()!=null) caseDataToBeUpdated.setClaimAmountInSOC(caseRequest.getClaimAmountInSOC());
            if(caseRequest.getRefLetter()!=null) caseDataToBeUpdated.setRefLetter(caseRequest.getRefLetter());
            if(caseRequest.getNoticeDate()!=null) caseDataToBeUpdated.setNoticeDate(caseRequest.getNoticeDate());
            if(caseRequest.getSocFillingDate()!=null) caseDataToBeUpdated.setSocFillingDate(caseRequest.getSocFillingDate());
            bankCases.add(caseDataToBeUpdated);
        });
        bankCaseRepository.saveAll(bankCases);
        return caseSheetRequests;
    }

    
    public List<AdminCaseRequest> saveCases(MultipartFile sheet) {
        List<AdminCaseRequest> caseSheetRequests = new ArrayList<>();
        try (InputStream inputStream = sheet.getInputStream()) {

            // Process the workbook here using the steps mentioned previously
            XSSFSheet sh = EmailService.getRows(inputStream);

            int startingRow = 1;
            for (int rowIndex = startingRow; rowIndex <= sh.getLastRowNum(); rowIndex++) {
                XSSFRow dataRow = sh.getRow(rowIndex);
                if (dataRow != null) {
                    var builder = AdminCaseRequest.builder();
                    for (int cellIndex = 0; cellIndex < dataRow.getLastCellNum(); cellIndex++) {
                        XSSFCell cell = dataRow.getCell(cellIndex);
                        adminBuilder(cell, cellIndex, builder);
                    }
                    builder.rowNum(rowIndex+1);
                    caseSheetRequests.add(builder.build());
                }
            }
                log.info(sh.getHeader().toString());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle potential IO exceptions
        }
        AtomicBoolean check = new AtomicBoolean(true);
        caseSheetRequests.forEach(request->
             check.set(ApplicationUtility.checkCase(request))
        );
        if(!check.get()){
            return new ArrayList<>();
        }

        caseSheetRequests.forEach(caseRequest->{
            try {
                adminService.saveCase(caseRequest);
            }catch (Exception e){
                throw AppException.builder()
                        .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"problem in row number "+caseRequest.getRowNum())).build())
                        .build();
            }
        });

//        return sheet.getOriginalFilename();
        return caseSheetRequests;
    }

    private static void adminBuilder(XSSFCell cell, int cellIndex, AdminCaseRequest.AdminCaseRequestBuilder builder) throws ParseException {
        if (cell != null&&cell.getRawValue()!=null) {
            switch (cellIndex) {
                case 0:
                    builder.bankId((int)cell.getNumericCellValue()); // Assuming this column contains integer values
                    break;
                case 1:
                    if(cell.getRawValue().equals("211")) builder.arbitratorId(0);
                    else
                        builder.arbitratorId((int)cell.getNumericCellValue());
                    break;
                case 2:
                    if(cell.getRawValue().equals("211")) builder.state("");
                    else
                        builder.state(cell.getStringCellValue());
                    break;
                case 3:
                    builder.caseNo(cell.getStringCellValue()); // Assuming this column contains integer values
                    break;
                case 4:
                    builder.caseType(cell.getStringCellValue());
                    break;
                case 5:
                    if(cell.getRawValue().equals("211")) builder.zone("");
                    else
                        builder.zone(cell.getStringCellValue());
                    break;
                case 6:
                    if(cell.getRawValue().equals("211")) builder.branchName("");
                    else
                        builder.branchName(cell.getStringCellValue());
                    break;
                case 7:
                    builder.customerId(cell.getRawValue());
                    break;
                case 8:
                    if (cell.getCellType()== CellType.STRING) builder.accountNumber(cell.getStringCellValue());
                    else if (cell.getCellType()== CellType.NUMERIC) builder.accountNumber(""+cell.getNumericCellValue());
                    break;
                case 9:
                    if(cell.getRawValue().equals("211")) builder.creditCardNumber("");
                    else
                        builder.creditCardNumber(cell.getStringCellValue());
                    break;
                case 10:
                    builder.customerName(cell.getStringCellValue());
                    break;
                case 11:
                    if(cell.getRawValue().equals("211")) builder.actualProduct("");
                    else
                        builder.actualProduct(cell.getStringCellValue());
                    break;
                case 12:
                    if(cell.getRawValue().equals("211")) builder.flagProductGroup("");
                    else
                        builder.flagProductGroup(cell.getStringCellValue());
                    break;
                case 13:
                    if(cell.getRawValue().equals("211")) builder.natureOfLegalAction("");
                    else
                        builder.natureOfLegalAction(cell.getStringCellValue());
                    break;
                case 14:
                    if(cell.getRawValue().equals("211")) builder.totalTos(Double.NaN);
                    else
                        builder.totalTos(Double.parseDouble(cell.getRawValue()));
                    break;
                case 15:
                    if(cell.getRawValue().equals("211")) builder.totalTosInCr(Double.NaN);
                    else
                        builder.totalTosInCr(Double.parseDouble(cell.getRawValue()));
                    break;
                case 16:
                    builder.noticeDate(ApplicationUtility.parseDate(cell.getStringCellValue()));
                    break;
                case 17:
                    if(cell.getRawValue().equals("211")) builder.refLetter(Date.from(Instant.EPOCH));
                    else
                        builder.refLetter(ApplicationUtility.parseDate(cell.getStringCellValue()));
                    break;
                case 18:
                    builder.socFillingDate(ApplicationUtility.parseDate(cell.getStringCellValue()));
                    break;
                case 19:
                    if(cell.getRawValue().equals("211")) builder.claimAmountInSOC(Double.NaN);
                    else
                        builder.claimAmountInSOC(Double.parseDouble(cell.getRawValue()));
                    break;
                case 20:
                    builder.firstHearingDate(ApplicationUtility.parseDate(cell.getStringCellValue()));
                    break;
                case 21:
                    builder.lastHearingDate(ApplicationUtility.parseDate(cell.getStringCellValue()));
                    break;
                case 22:
                    builder.nextHearingDate(ApplicationUtility.parseDate(cell.getStringCellValue()));
                    break;
                case 23:
                    builder.stagesOfLastHearingDate(cell.getStringCellValue());
                    break;
                case 24:
                    builder.stagesOfNextHearingDate(cell.getStringCellValue());
                    break;
                case 25:
                    builder.caseStatus(cell.getStringCellValue());
                    break;
                case 26:
                    builder.flagForContested(cell.getStringCellValue());
                    break;
                case 27:
                    builder.detailsRemark(cell.getStringCellValue());
                    break;
                case 28:
                    builder.awardDate(cell.getDateCellValue());
                    break;
                case 29:
                    builder.awardAmount(cell.getStringCellValue());
                    break;
                case 30:
                    builder.sec17AppFillingDate(cell.getDateCellValue());
                    break;
                case 31:
                    builder.sec17OrderDate(ApplicationUtility.parseDate(cell.getStringCellValue()));
                    break;
                case 32:
                    builder.sec17AppStatus(cell.getStringCellValue());
                    break;
                case 33:
                    builder.courtName(cell.getStringCellValue());
                    break;
                case 34:
                    builder.place(cell.getStringCellValue());
                    break;
                case 35:
                    builder.arbitrator(cell.getStringCellValue());
                    break;
                case 36:
                    builder.lawyerName(cell.getStringCellValue());
                    break;
                case 37:
                    builder.lmName(cell.getStringCellValue());
                    break;
                default:
                    // Handle unexpected columns (optional)
                    log.warn("Unmapped column index: {}", cellIndex);
            }
        }
    }

    @Autowired
    private AdminService adminService;


}
