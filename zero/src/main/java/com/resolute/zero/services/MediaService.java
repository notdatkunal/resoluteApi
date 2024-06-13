package com.resolute.zero.services;


import com.resolute.zero.domains.Document;
import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.repositories.DocumentRepository;
import com.resolute.zero.repositories.ProceedingRepository;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.utilities.ApplicationUtility;
import com.resolute.zero.utilities.MediaUtility;
import com.resolute.zero.utilities.MetaDocInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
@Service
public class MediaService {
    public void uploadFile(MetaDocInfo metaDocInfo) throws IOException {
        var name = metaDocInfo.getFile().getOriginalFilename();
        if (name == null) throw new AssertionError();
        String format = name.split("\\.")[1];
        Files.createDirectories(Path.of("media"));
        Path uploadTo = Path.of(String.format("media/%s.%s", metaDocInfo.getCode(),format));
        metaDocInfo.getFile().transferTo(uploadTo);
        metaDocInfo.setFileName(uploadTo.getFileName().toString());
    }
    public ArrayList<String> uploadFiles(MultipartFile[] files) throws IOException {
        Files.createDirectories(Path.of("media"));
        var list = new ArrayList<String>();
        for (MultipartFile file : files){
            list.add(saveSingleFile(file));
        }
        return list;
    }

    private String saveSingleFile(MultipartFile file) throws IOException {
        Path uploadTo = Path.of(String.format("media/%s", file.getOriginalFilename()));
        file.transferTo(uploadTo);
        return file.getOriginalFilename();
    }

    public void saveDocumentsInDB(List<MetaDocInfo> metaDocsInfo) {

        for(MetaDocInfo metaDocInfo:metaDocsInfo)
        {
            this.saveDocumentInDB(metaDocInfo);

        }
    }

    @Autowired
    private CaseRepository caseRepository;
    public ResponseEntity<?> saveDocumentInDB(MetaDocInfo metaDocInfo) {
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
            caseRepository.save(caseObj);
        }else {
             return ResponseEntity.notFound()
                     .eTag("case id not found")
                     .build();
        }
        if(metaDocInfo.getHearingId() !=0){
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
        return ResponseEntity.ok(metaDocInfo.getFileName()+" saved in server ");

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
            fileToDelete.delete();
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

    public List<AdminCaseRequest> saveCases(MultipartFile sheet) {
        List<AdminCaseRequest> caseSheetRequests = new ArrayList<>();
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
                    var builder = AdminCaseRequest.builder();
                    for (int cellIndex = 0; cellIndex < dataRow.getLastCellNum(); cellIndex++) {
                        XSSFCell cell = dataRow.getCell(cellIndex);
                        if (cell != null) {
                            switch (cellIndex) {
                                case 0:
                                    builder.bankId(ApplicationUtility.getIntValue(cell)); // Assuming this column contains integer values
                                    break;
                                case 1:
                                    builder.arbitratorId(ApplicationUtility.getIntValue(cell));
                                    break;
                                case 2:
                                    builder.state(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 3:
                                    builder.caseNo(ApplicationUtility.getStringValue(cell)); // Assuming this column contains integer values
                                    break;
                                case 4:
                                    builder.caseType(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 5:
                                    builder.zone(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 6:
                                    builder.branchName(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 7:
                                    builder.customerId(Objects.requireNonNull(ApplicationUtility.getIntValue(cell)).toString());
                                    break;
                                case 8:
                                    builder.accountNumber(Objects.requireNonNull(ApplicationUtility.getIntValue(cell)).toString());
                                    break;
                                case 9:
                                    builder.creditCardNumber(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 10:
                                    builder.customerName(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 11:
                                    builder.actualProduct(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 12:
                                    builder.flagProductGroup(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 13:
                                    builder.natureOfLegalAction(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 14:
                                    builder.totalTos(Double.parseDouble(Objects.requireNonNull(ApplicationUtility.getStringValue(cell))));
                                    break;
                                case 15:
                                    builder.totalTosInCr(Double.parseDouble(Objects.requireNonNull(ApplicationUtility.getStringValue(cell))));
                                    break;
                                case 16:
                                    builder.noticeDate(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 17:
                                    builder.refLetter(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 18:
                                    builder.socFillingDate(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 19:
                                    builder.claimAmountInSOC(Double.parseDouble(Objects.requireNonNull(ApplicationUtility.getStringValue(cell))));
                                    break;
                                case 20:
                                    builder.firstHearingDate(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 21:
                                    builder.lastHearingDate(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 22:
                                    builder.nextHearingDate(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 23:
                                    builder.stagesOfLastHearingDate(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 24:
                                    builder.stagesOfNextHearingDate(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 25:
                                    builder.caseStatus(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 26:
                                    builder.flagForContested(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 27:
                                    builder.detailsRemark(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 28:
                                    builder.awardDate(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 29:
                                    builder.awardAmount(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 30:
                                    builder.sec17AppFillingDate(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 31:
                                    builder.sec17OrderDate(ApplicationUtility.parseDate(ApplicationUtility.getStringValue(cell)));
                                    break;
                                case 32:
                                    builder.sec17AppStatus(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 33:
                                    builder.courtName(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 34:
                                    builder.place(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 35:
                                    builder.arbitrator(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 36:
                                    builder.lawyerName(ApplicationUtility.getStringValue(cell));
                                    break;
                                case 37:
                                    builder.lmName(ApplicationUtility.getStringValue(cell));
                                    break;
                                default:
                                    // Handle unexpected columns (optional)
                                    log.warn("Unmapped column index: {}", cellIndex);
                            }
                        }
                    }
                    builder.rowNum(rowIndex+1);
                    caseSheetRequests.add(builder.build());
                }
            }
                log.info(headers.toString());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle potential IO exceptions
        }
        AtomicBoolean check = new AtomicBoolean(true);
        caseSheetRequests.forEach(request->{
             check.set(ApplicationUtility.checkCase(request));
        });
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
    @Autowired
    private AdminService adminService;
}
