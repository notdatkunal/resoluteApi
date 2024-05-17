package com.resolute.zero.utilities;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class MetaDocInfo {
    public static final Map<String, String> MAIN_TYPE_MAP = new HashMap<>() {{
        put("loanRecallNotice", "LRN");
        put("intentLetter", "ITL");
        put("referenceLetter", "RFL");
        put("contentLetter", "CNL");
        put("intimationLetter", "IML");
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
        put("section17", "SEC");
        put("award", "AWD");
        put("other", "OTH");
    }};
    String mainType;
    String subType;
    Integer caseId;

}
