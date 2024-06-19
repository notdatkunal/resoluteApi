package com.resolute.zero.helpers;


import com.resolute.zero.responses.*;
import com.resolute.zero.requests.*;
import com.resolute.zero.domains.*;
import com.resolute.zero.services.JWTutil;
import java.util.Date;


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
			var caseHistory =  CaseHistoryResponse.builder()
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
							.build())
					.build();
            if(bankCase.getArbitrator()!=null) caseHistory.getCaseStatus().setArbitratorName(bankCase.getArbitrator().getArbitratorName());

            return caseHistory;
		}

        public static AdminCaseResponse convertAdminCaseResponse(BankCase bankCase) {
            var response =  AdminCaseResponse.builder()
            .caseType(bankCase.getCaseType())
            .registrationDate(Date.from(bankCase.getCreatedAt()))
            .id(bankCase.getId())
            .bankId(bankCase.getBank().getId())
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
            if(bankCase.getArbitrator()!=null){
                response.setArbitratorId(bankCase.getArbitrator().getId());
                response.setArbitrator(bankCase.getArbitrator().getArbitratorName());
            }
            return response;
        }



        public static CommunicationResponse convertCommunicationResponse(Communication comm) {
            // to be completed
            return CommunicationResponse.builder()
                    .fileName(comm.getFileName())
                    .date(comm.getDate())
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
                    .country(loginRecord.getCountry())
                    .ip(loginRecord.getIp())
                    .username(loginRecord.getUser().getUsername())
                    .build();
        }

        public static AdminOrderResponse convertOrderResponse(CaseOrder caseOrder) {
            return AdminOrderResponse.builder()
                    .type(caseOrder.getType())
                    .filName(caseOrder.getFileName())
                    .date(caseOrder.getDate())
                    .build();
        }

        public static DocumentResponse convertDocumentResponse(Document document) {
            return DocumentResponse.builder()
                    .uploadDate(Date.from(document.getCreatedAt()))
                    .subType(document.getDocumentSubTypeTitle())
                    .mainType(document.getDocumentMainTypeTitle())
                    .caseId(document.getCaseId())
                    .documentId(document.getId())
                    .documentTitle(document.getImageName())

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
            caseObj.setCaseType(req.getCaseType());
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

        public static CaseOrder createCaseOrder(AdminOrderRequest adminOrder) {
            var order = new CaseOrder();
            order.setDate(adminOrder.getDate());
            order.setType(adminOrder.getType());
            return order;
        }

        public static Communication createCommunication(AdminCommRequest adminCommRequest) {
            Communication communication = new Communication();
            communication.setDate(adminCommRequest.getDate());
            return communication;
        }
    }

}
