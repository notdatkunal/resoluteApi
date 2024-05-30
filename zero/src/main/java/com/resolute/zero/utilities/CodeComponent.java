package com.resolute.zero.utilities;

import com.resolute.zero.repositories.ProceedingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CodeComponent {
    /**
     * code creation breakdown
     * LRNNOT000044 (12-digit code)
     * code consists three parts
     * ABC+XYZ+(6-digit number)
     *
     * @param mainType  (ABC) - represents main type of document
     * @param subType   (XYZ) - represents subtype of document
     * @param caseId    (6-digit number) - unique case number
     * @param hearingId (single digit) - this is an ID which creates a custom subType for teh document
     * @return 12-character alphanumeric code
     * @author notdatkunal
     * @since 1.0
     */
    public String getCode(String mainType, String subType, Integer caseId, Integer hearingId){
        String main = getMainTypeAbbreviation(mainType);
        String sub  = getSubTypeMapping(subType);
        String cId = formatNumberWithLeadingZeros(caseId);
        var listOfHearingType = List.of("REC","MOM");
        if(listOfHearingType.contains(main)){
            var hearing = proceedingRepository.findById(hearingId);
            if(hearing.isEmpty()) throw new RuntimeException("hearing object not fount");
            sub = hearing.get().getOrderType();
        }

    return main+sub+cId;



    }
    @Autowired
    private ProceedingRepository proceedingRepository;


    public String getMainTypeAbbreviation(String mainType) {
        String abbreviation = MetaDocInfo.MAIN_TYPE_MAP.get(mainType);
        if (abbreviation == null) {
            throw new IllegalArgumentException("Invalid mainType: " + mainType);
        }
        return abbreviation;
    }
    public String getMainTypeByAbbreviation(String abbreviation) {
        for (Map.Entry<String, String> entry : MetaDocInfo.MAIN_TYPE_MAP.entrySet()) {
            if (entry.getValue().equals(abbreviation)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Invalid mainType abbreviation : " + abbreviation); // Indicate no mainType found for the abbreviation
    }


    public MetaDocInfo getMetaDocInfo(String code){
        String mainTypeCode = code.substring(0,3);
        String subTypeCode = code.substring(3,6);
        String caseCode = code.substring(6);
        int index = 0;
        for(char character :caseCode.toCharArray()){

            if(character!='0')
            {
                index = caseCode.indexOf(character);
                break;
            }


        }
        if(index!=0){
            caseCode = caseCode.substring(index);
        }

        return MetaDocInfo.builder()
                .caseId(Integer.parseInt(caseCode))
                .mainType(getMainTypeByAbbreviation(mainTypeCode))
                .subType(getSubTypeKeyByAbbr(subTypeCode))
                .build();

    }
    public MetaDocInfo getMetaDocInfo(String code, MultipartFile file){
        String mainTypeCode = code.substring(0,3);
        String subTypeCode = code.substring(3,6);
        String caseCode = code.substring(6);
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().indexOf("."));
        int index = 0;
        for(char character :caseCode.toCharArray()){

            if(character!='0')
            {
                index = caseCode.indexOf(character);
                break;
            }


        }
        if(index!=0){
            caseCode = caseCode.substring(index);
        }

        return MetaDocInfo.builder()
                .caseId(Integer.parseInt(caseCode))
                .mainType(getMainTypeByAbbreviation(mainTypeCode))
                .subType(getSubTypeKeyByAbbr(subTypeCode))
                .fileExtension(extension)
                .build();

    }

    public String getSubTypeMapping(String subType) {
        String mappedValue = MetaDocInfo.SUB_TYPE_MAP.get(subType);
        if (mappedValue == null) {
            throw new IllegalArgumentException("Invalid subType: " + subType);
        }
        return mappedValue;
    }
    public String getSubTypeKeyByAbbr(String value) {
        for (Map.Entry<String, String> entry : MetaDocInfo.SUB_TYPE_MAP.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException(); // Indicate no key found for the value
    }


    public  String formatNumberWithLeadingZeros(int number) {
        int digits = countDigits(number);
        int zerosToAdd = 6 - digits;
        if(digits>6 || zerosToAdd<0)
            throw new IllegalArgumentException();
        //it throws exception when
        StringBuilder formattedNumber = new StringBuilder();

        if (zerosToAdd > 0) {
            // Add leading zeros
            formattedNumber.append("0".repeat(zerosToAdd));
        }

        // Append the original number
        formattedNumber.append(number);

        return formattedNumber.toString();
    }
    public int countDigits(int number) {
        if (number == 0) {
            return 1; // Special case: 0 has 1 digit
        }
        return (int) Math.floor(Math.log10(number)) + 1;
    }
//    public static void main(String[] args) {
//        Integer digits = 4224;
//
//        CodeComponent codeCreator = new CodeComponent();
//        String code = codeCreator.getCode("loanRecallNotice","notice",digits, hearingId);
//
//        System.out.println("created code " + code);
//        System.out.println("created meta info"+codeCreator.getMetaDocInfo(code));
//
//    }

    public List<MetaDocInfo> getMetaDocsInfo(ArrayList<String> fileNamesList) {
        var list = new ArrayList<MetaDocInfo>();
           for(String fileName :  fileNamesList){
                list.add(this.getMetaDocInfo(fileName));
           }
        return list;
    }
}
