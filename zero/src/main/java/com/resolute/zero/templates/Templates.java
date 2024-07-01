package com.resolute.zero.templates;
import lombok.AllArgsConstructor;

import java.util.Map;
@AllArgsConstructor
public enum Templates {
    LRN("loan recall notice",Map.of(
            1,Arguments.BORROWER.toString()
            ,2,Arguments.LOAN_RECALL_NOTICE.toString()
            ,3,Arguments.OUTSTANDING_AMOUNT.toString()
            ,4,Arguments.LRN_LINK.toString()
            ,5,Arguments.BANK.toString()
    ), """
            Dear {{1}},
            Greetings of the Day,
            Pfa, the {{2}} link for Non-Payment of your outstanding dues  {{3}} to {{5}} as per the terms and conditions of the Loan Agreement, for your reference and kind perusal.
            {{4}}
            Please ignore this communication if already paid or settled.The contents of this communication are confidential and if you are not the intended recipient of this communication, please inform us immediately.
            Thanks and regards {{5}}
            """),
    INTENT("intent letter",Map.of(
            1,Arguments.BORROWER.toString()
            ,2,Arguments.INTENT_LETTER.toString()
            ,3,Arguments.OUTSTANDING_AMOUNT.toString()
            ,4,Arguments.INTENT_LETTER_LINK.toString()
            ,5,Arguments.BANK.toString()
    ), """
            Dear {{1}},
            Greetings of the Day,
            Pfa, the {{2}} link for Non-Payment of your outstanding dues to {{3}} Bank as per the terms and conditions of the Loan Agreement, for your reference and kind perusal.
            {{4}}
            Please ignore this communication if already paid or settled.The contents of this communication are confidential and if you are not the intended recipient of this communication, please inform us immediately.
            Thanks and regards {{5}}
            """),
    REFERENCE("reference letter",Map.of(
            1,Arguments.BORROWER.toString()
            ,2,Arguments.REFERENCE_LETTER.toString()
            ,3,Arguments.OUTSTANDING_AMOUNT.toString()
            ,4,Arguments.REFERENCE_LETTER_LINK.toString()
            ,5,Arguments.BANK.toString()
    ), """
            Dear {{1}},
            Greetings of the Day,
            Pfa, the {{2}} link for Non-Payment of your outstanding dues to {{3}} Bank as per the terms and conditions of the Loan Agreement, for your reference and kind perusal.
            {{4}}
            Please ignore this communication if already paid or settled.The contents of this communication are confidential and if you are not the intended recipient of this communication, please inform us immediately.
            Thanks and regards {{5}}
            """),
    CONSENT("consent letter",Map.of(
            1,Arguments.BORROWER.toString()
            ,2,Arguments.CONSENT_LETTER.toString()
            ,3,Arguments.OUTSTANDING_AMOUNT.toString()
            ,4,Arguments.DATE.toString()
            ,5,Arguments.CONSENT_LETTER_LINK.toString()
            ,6,Arguments.BANK.toString()
    ), """
            Dear {{1}},
            Greetings of the Day,
            Pfa, the {{2}} link for Non-Payment of your outstanding dues to {{3}} Bank as per the terms and conditions of the Loan Agreement, for your reference and kind perusal.
            Kindly attend the scheduled meeting on {{4}} between 11AM TO 5PM, through the below link.
            {{5}}
            Please ignore this communication if already paid or settled.The contents of this communication are confidential and if you are not the intended recipient of this communication, please inform us immediately.
            Thanks and regards {{6}}
            """),
    FIRST_HEARING("first hearing",Map.of(
            1,Arguments.BORROWER.toString()
            ,2,Arguments.CASE_NUMBER.toString()
            ,3,Arguments.ATTENDANCE.toString()
            ,4,Arguments.SOC_AND_SEC17_APPLICATION.toString()
            ,5,Arguments.DATE.toString()
            ,6,Arguments.MEETING_LINK.toString()
            ,7,Arguments.BANK.toString()
    ), """
            Dear {{1}},
            Ref {{2}}
            Greetings of the Day,
            Today the first hearing was conducted in the above matter and you have been marked {{3}}, claimant was present and filed {{4}} through its advocate, you can download the same through the below link.\s
            The next date for hearing  {{5}}
            Link for meeting {{6}} Please ignore this communication if already paid or settled.The contents of this communication are confidential and if you are not the intended recipient of this communication, please inform us immediately.
            Thanks and regards {{7}}
            """),
    DEFAULT_HEARING("hearing",Map.of(
            1,Arguments.BORROWER.toString()
            ,2,Arguments.CASE_NUMBER.toString()
            ,3,Arguments.HEARING_SEQUENCE.toString()
            ,4,Arguments.ATTENDANCE.toString()
            ,5,Arguments.DATE.toString()
            ,6,Arguments.MEETING_LINK.toString()
            ,7,Arguments.BANK.toString()
    ), """
            Dear {{1}},
            Ref {{2}}
            Greetings of the Day,
            Today the {{3}} hearing was conducted in the above matter and you have been marked {{4}}, claimant was present through its advocate\s
            The next date for hearing  {{5}}
            Link for meeting {{6}}
            Please ignore this communication if already paid or settled.The contents of this communication are confidential and if you are not the intended recipient of this communication, please inform us immediately.
            Thanks and regards {{7}}
            """),
    LAST_HEARING("last hearing",Map.of(
            1,Arguments.BORROWER.toString()
            ,2,Arguments.CASE_NUMBER.toString()
            ,3,Arguments.ATTENDANCE.toString()
            ,4,Arguments.LINK_FOR_REWARD.toString()
            ,5,Arguments.BANK.toString()
            ,6,Arguments.HEARING_SEQUENCE.toString()
    ), """
            Dear {{1}},
            Ref {{2}}
            Greetings of the Day,
            Today the {{6}} hearing was conducted in the above matter and you have been marked {{3}}, claimant was present through its advocate,\s
            The final Award is passed.
            Link for Award {{4}}
            Please ignore this communication if already paid or settled. The contents of this communication are confidential and if you are not the intended recipient of this communication, please inform us immediately.
            Thanks and regards {{5}}
            """)
    ;
    final String templateName;
    final Map<Integer,String> arguments;
    final String template;


}
