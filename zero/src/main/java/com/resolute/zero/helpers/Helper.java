package com.resolute.zero.helpers;

import com.resolute.zero.domains.Arbitrator;
import com.resolute.zero.domains.Bank;
import com.resolute.zero.domains.BankCase;
import com.resolute.zero.domains.User;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.requests.ArbitratorRequest;
import com.resolute.zero.requests.BankRequest;
import com.resolute.zero.responses.ArbitratorResponse;
import com.resolute.zero.responses.CaseResponse;
import com.resolute.zero.responses.UserModel;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Date;

public class Helper {
    public static class Response{


        public static UserModel getUserModel(User user){

            return UserModel.builder()
                    .id(user.getId())
                    .status(true)
                    .username(user.getUserName())
                    .role(user.getRole())
                    .build();
        }

        public static ArbitratorResponse getArbitratorResponse(Arbitrator arbitrator) {
            return ArbitratorResponse.builder()
                    .arbitratorId(arbitrator.getId())
                    .arbitratorName(arbitrator.getArbitratorName())
                    .registrationDate(arbitrator.getRegistrationDate())
                    .build();
        }

        public static CaseResponse getCaseResponse(BankCase bankCase) {
            return CaseResponse.builder()
                    .caseType(bankCase.getCaseHistory().getCaseDetails().getCaseType())
                    .caseNo(bankCase.getCaseNo())
                    .fillingDate(Date.from(bankCase.getUpdatedAt()))
                    .accountNumber(bankCase.getAccountNumber())
                    .registrationDate(Date.from(bankCase.getCreatedAt()))
                    .build();
        }
    }
    public static class Request{


        public static Bank createBank(BankRequest request) {

            var bank = new Bank();
            bank.setBankName(request.getBankName());
            bank.setLocation(request.getLocation());
            bank.setOfficerName(request.getOfficerName());
            bank.setRegistrationDate(request.getRegistrationDate());
            bank.setUsername(request.getUsername());

            return bank;
        }

        public static Arbitrator createArbitrator(ArbitratorRequest request){
            var arbitratorObj = new Arbitrator();
            arbitratorObj.setUserName(request.getUserName());
            arbitratorObj.setArbitratorName(request.getArbitratorName());
            arbitratorObj.setRegistrationDate(request.getRegistrationDate());
            arbitratorObj.setLocation(request.getLocation());
            return arbitratorObj;
        }

        public static BankCase createCase(AdminCaseRequest req){
            var caseObj = new BankCase();
            caseObj.setCaseNo(req.getCaseNo());
            caseObj.setState(req.getState());
            caseObj.setZone(req.getZone());
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
            caseObj.getCaseHistory().getCaseDetails().setSocFillingDate(req.getSocFillingDate());
            caseObj.getCaseHistory().getCaseDetails().setClaimAmountInSOC(req.getClaimAmountInSOC());
            caseObj.getCaseHistory().getCaseStatus().setFirstHearingDate(req.getFirstHearingDate());
            caseObj.getCaseHistory().getCaseStatus().setLastHearingDate(req.getLastHearingDate());
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
    }

}