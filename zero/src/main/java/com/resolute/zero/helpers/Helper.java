package com.resolute.zero.helpers;


import com.resolute.zero.responses.*;
import com.resolute.zero.requests.*;
import com.resolute.zero.domains.*;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.JWTutil;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Helper {
    public static class Convert {


        public static UserModel convertUserModel(User user){

            return UserModel.builder()
                    .id(user.getId())
                    .status(true)
                    .token(JWTutil.generateToken(user.getUsername()))
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build();
        }

        public static ArbitratorResponse convertArbitratorResponse(Arbitrator arbitrator) {
            return ArbitratorResponse.builder()
                    .arbitratorId(arbitrator.getId())
                    .username(arbitrator.getUserName())
                    .email(arbitrator.getEmail())
                    .location(arbitrator.getLocation())
                    .arbitratorName(arbitrator.getArbitratorName())
                    .registrationDate(Date.from(arbitrator.getRegistrationDate()))
                    .build();
        }

        public static CaseResponse convertCaseResponse(BankCase bankCase) {
            return CaseResponse.builder()
                    .id(bankCase.getId())
                    .caseType(bankCase.getCaseType())
                    .caseNo(bankCase.getCaseNo())
                    .fillingDate(bankCase.getSocFillingDate())
                    .accountNumber(bankCase.getAccountNumber())
                    .registrationDate(Date.from(bankCase.getCreatedAt()))
                    .build();
        }

		public static BankResponse convertBankResponse(Bank item) {
			return BankResponse.builder()
					.bankName(item.getBankName())
					.bankId(item.getId())
					.username(item.getUsername())
					.registrationDate(Date.from(item.getCreatedAt()))
                    .officerName(item.getOfficerName())
                    .location(item.getLocation())
                    .email(item.getEmail())
					.build();
		}

		public static BorrowerResponse convertBorrowerResponse(Borrower borrower) {
			return BorrowerResponse.builder()
					.borrowerId(borrower.getId())
					.borrowerName(borrower.getBorrowerName())
					.build();
		}

		public static CaseHistoryResponse convertCaseHistoryResponse(BankCase bankCase) {
			return CaseHistoryResponse.builder()
					.caseDetails(CaseDetailsResponse.builder()
							.caseType(bankCase.getCaseType())
                            .registrationDate(bankCase.getAwardDate())
                            .caseNumber(bankCase.getCaseNo())
                            .fillingDate(bankCase.getSocFillingDate())
							.build())
					.caseStatus(CaseStatusResponse.builder()
                            .caseStage(bankCase.getCaseStatus())
                            .NextHearingDate(bankCase.getNextHearingDate())
                            .firstHearingDate(bankCase.getFirstHearingDate())
                            .arbitratorName(bankCase.getArbitrator().getArbitratorName())
							.build())
					.build();
		}

        public static AdminCaseResponse convertAdminCaseResponse(BankCase bankCase) {
            return AdminCaseResponse.builder()
            .bankId(bankCase.getBank().getId())
            .arbitratorId(bankCase.getArbitrator().getId())
            .caseNo(bankCase.getCaseNo())
            .state(bankCase.getState())
            .sec17OrderDate(bankCase.getSec17OrderDate())
            .zone(bankCase.getZone())
            .branchName(bankCase.getBranchName())
            .customerId(bankCase.getCustomerId())
            .accountNumber(bankCase.getAccountNumber())
            .creditCardNumber(bankCase.getCreditCardNumber())
            .customerName(bankCase.getCustomerName())
            .actualProduct(bankCase.getActualProduct())
            .flagProductGroup(bankCase.getFlagProductGroup())
            .natureOfLegalAction(bankCase.getNatureOfLegalAction())
            .totalTos(bankCase.getTotalTos())
            .totalTosInCr(bankCase.getTotalTosInCr())
            .noticeDate(bankCase.getNoticeDate())
            .refLetter(bankCase.getRefLetter())
            .socFillingDate(bankCase.getSocFillingDate())
            .claimAmountInSOC(bankCase.getClaimAmountInSOC())
            .firstHearingDate(bankCase.getFirstHearingDate())
            .lastHearingDate(bankCase.getLastHearingDate())
            .lmName(bankCase.getLmName())
            .lawyerName(bankCase.getLawyerName())
            .arbitrator(bankCase.getArbitrator().getArbitratorName())
            .place(bankCase.getPlace())
            .courtName(bankCase.getCourtName())
            .sec17AppStatus(bankCase.getSec17AppStatus())
            .sec17AppFillingDate(bankCase.getSec17AppFillingDate())
            .awardAmount(bankCase.getAwardAmount())
            .awardDate(bankCase.getAwardDate())
            .detailsRemark(bankCase.getDetailsRemark())
            .flagForContested(bankCase.getFlagForContested())
            .caseStatus(bankCase.getCaseStatus())
            .stagesOfLastHearingDate(bankCase.getStagesOfLastHearingDate())
            .nextHearingDate(bankCase.getNextHearingDate())
            .stagesOfNextHearingDate(bankCase.getStagesOfNextHearingDate())
            .build();
        }


        public static CaseDocumentsResponse convertCaseDocumentResponse(BankCase bankCase) {
            // to be continued
            return CaseDocumentsResponse.builder()
                    .loanRecallNotice(LoanRecallNoticeResponse.builder()
                            .notice("LRNNOT000044")
                            .RPAD("LRNNOT000044")
                            .tracking("LRNNOT000044")
                            .build())
                    .intentLetter(IntentLetterResponse.builder()
                            .notice("LRNNOT000044")
                            .RPAD("LRNNOT000044")
                            .tracking("LRNNOT000044")
                            .build())
                    .referenceLetter(ReferenceLetterResponse.builder()
                            .notice("LRNNOT000044")
                            .RPAD("LRNNOT000044")
                            .tracking("LRNNOT000044")
                            .build())
                    .concentLetter(ConcentLetterResponse.builder()
                            .notice("LRNNOT000044")
                            .RPAD("LRNNOT000044")
                            .tracking("LRNNOT000044")
                            .statementOfClaim("LRNNOT000044")
                            .build())
                    .intimationLetter(IntimationLetterResponse.builder()
                            .notice("LRNNOT000044")
                            .RPAD("LRNNOT000044")
                            .tracking("LRNNOT000044")
                            .affidavit("LRNNOT000044")
                            .build())
                    .awardLetter(AwardLetterResponse.builder()
                            .notice("LRNNOT000044")
                            .RPAD("LRNNOT000044")
                            .tracking("LRNNOT000044")
                            .roznama("LRNNOT000044")
                            .bankDocument("LRNNOT000044")
                            .build())
                    .build();
        }

        public static CommunicationResponse convertCommunicationResponse(BankCase obj) {
            // to be completed
            return CommunicationResponse.builder()
                    .dates(List.of(CommDateResponse.builder().date(Date.from(Instant.now())).emailComm("emailDoc").emailCommTitle("emailTitleLink").whatsAppComm("whatsappComm").whatsAppCommTitle("whatsappCommTitleLink").textComm("textComm").textCommTitle("textCommTitleLink").build(),CommDateResponse.builder().date(Date.from(Instant.now())).emailComm("emailDoc").emailCommTitle("emailTitleLink").whatsAppComm("whatsappComm").whatsAppCommTitle("whatsappCommTitleLink").textComm("textComm").textCommTitle("textCommTitleLink").build(),CommDateResponse.builder().date(Date.from(Instant.now())).emailComm("emailDoc").emailCommTitle("emailTitleLink").whatsAppComm("whatsappComm").whatsAppCommTitle("whatsappCommTitleLink").textComm("textComm").textCommTitle("textCommTitleLink").build()))
                    .build();


        }

        public static HearingResponse convertHearingResponse(Proceeding proceeding) {
            return HearingResponse.builder()
                    .current(false)
                    .hearingId(proceeding.getId())
                    .hearingDate(proceeding.getHearingDate())
                    .minutesOfMeetings(proceeding.getMinutesOfMeetings())
                    .meetingRecordings(proceeding.getMeetingRecordings())
                    .build();
        }

        public static LoginRecordResponse convertLoginRecord(LoginRecord loginRecord) {
            return LoginRecordResponse.builder()
                    .loginTime(loginRecord.getLoginTime())
                    .username(loginRecord.getUser().getUsername())
                    .build();
        }
    }
    public static class Creator {


        public static Bank createBank(BankRequest request) {
            var bank = new Bank();
            bank.setBankName(request.getBankName());
            bank.setLocation(request.getLocation());
            bank.setOfficerName(request.getOfficerName());
            bank.setRegistrationDate(request.getRegistrationDate());
            bank.setUsername(request.getUsername());
            bank.setEmail(request.getEmail());
            return bank;
        }

        public static Arbitrator createArbitrator(ArbitratorRequest request){
            var arbitratorObj = new Arbitrator();
            arbitratorObj.setUserName(request.getUsername());
            arbitratorObj.setArbitratorName(request.getArbitratorName());
//            arbitratorObj.setRegistrationDate(request.getRegistrationDate());
            arbitratorObj.setLocation(request.getLocation());
            arbitratorObj.setEmail(request.getEmail());
            return arbitratorObj;
        }

        public static BankCase createCase(AdminCaseRequest req){
            var caseObj = new BankCase();
            caseObj.setCaseNo(req.getCaseNo());
            caseObj.setState(req.getState());
            caseObj.setZone(req.getZone());
            caseObj.setSec17OrderDate(req.getSec17OrderDate());
            caseObj.setBranchName(req.getBranchName());
            caseObj.setCustomerId(req.getCustomerId());
            caseObj.setAccountNumber(req.getAccountNumber());
            caseObj.setCreditCardNumber(req.getCreditCardNumber());
            caseObj.setCustomerName(req.getCustomerName());
            caseObj.setActualProduct(req.getActualProduct());
            caseObj.setFlagProductGroup(req.getFlagProductGroup());
            caseObj.setNatureOfLegalAction(req.getNatureOfLegalAction());
            caseObj.setTotalTos(req.getTotalTos());
            caseObj.setTotalTosInCr(req.getTotalTosInCr());
            caseObj.setNoticeDate(req.getNoticeDate());
            caseObj.setRefLetter(req.getRefLetter());
            caseObj.setSocFillingDate(req.getSocFillingDate());
            caseObj.setClaimAmountInSOC(req.getClaimAmountInSOC());
            caseObj.setFirstHearingDate(req.getFirstHearingDate());
            caseObj.setLastHearingDate(req.getLastHearingDate());
            caseObj.setLmName(req.getLmName());
            caseObj.setLawyerName(req.getLawyerName());
            caseObj.getArbitrator().setUserName(req.getArbitrator());
            caseObj.setPlace(req.getPlace());
            caseObj.setCourtName(req.getCourtName());
            caseObj.setSec17AppStatus(req.getSec17AppStatus());
            caseObj.setSec17AppFillingDate(req.getSec17AppFillingDate());
            caseObj.setAwardAmount(req.getAwardAmount());
            caseObj.setAwardDate(req.getAwardDate());
            caseObj.setDetailsRemark(req.getDetailsRemark());
            caseObj.setFlagForContested(req.getFlagForContested());
            caseObj.setCaseStatus(req.getCaseStatus());
            caseObj.setStagesOfLastHearingDate(req.getStagesOfLastHearingDate());
            caseObj.setNextHearingDate(req.getNextHearingDate());
            caseObj.setStagesOfNextHearingDate(req.getStagesOfNextHearingDate());

            return caseObj;
        }

        public static Proceeding createProceeding(CaseHearingRequest caseHearingRequest) {
            var proceeding = new Proceeding();
            proceeding.setHearingDate(caseHearingRequest.getHearingDate());
            return proceeding;
        }
    }

}
