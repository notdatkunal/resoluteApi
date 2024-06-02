package com.resolute.zero.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeMappingUtil {


    public static final List<String> CASE_TYPES = List.of("Arbitration", "Notice", "Sarfaesi", "Sec 138", "PASA");


    public static final Map<String, String> MAIN_TYPE_MAP = new HashMap<>() {{
        // notice types
        put("loanRecallNotice", "LRN");

        // letter types
        put("intentLetter", "ITL");
        put("referenceLetter", "RFL");
        put("contentLetter", "CNL");
        put("intimationLetter", "IML");

        // media types
        put("communication", "COM");
        put("document", "DOC");
        put("hearing", "HER");
        put("bankDocument","BND");
        put("borrowerDocument","BRW");

        // hearing types
        put("recording","REC");
        put("minutesOfMeeting","MOM");

        //order types
        put("section17", "SEC");
        put("interim", "INT");
        put("award", "AWD");
        put("order", "ORD");

    }};
    public static final Map<String, String> SUB_TYPE_MAP = new HashMap<>() {{
        put("notice", "NOT");
        put("RPAD", "RPA");
        put("tracking", "TRA");
        put("statementOfClaim", "SOC");
        put("affidavit", "AFD");
        put("roznama", "ROZ");
        put("bankDocument", "BND");
//        put("section17", "SEC");
//        put("interim", "INT");
//        put("award", "AWD");
        put("other", "OTH");
    }};
}
