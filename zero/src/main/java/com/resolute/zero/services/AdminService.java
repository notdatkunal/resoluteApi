package com.resolute.zero.services;


import com.resolute.zero.domains.*;
import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.*;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.requests.ArbitratorRequest;
import com.resolute.zero.requests.BankRequest;
import com.resolute.zero.responses.*;
import com.resolute.zero.utilities.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    @Autowired
    private CaseTypeRepository caseTypeRepository;

    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ArbitratorRepository arbitratorRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;



    public void saveCase(AdminCaseRequest request) {
        var caseObj =  Helper.Creator.createCase(request);
        Arbitrator arbitrator = null;
        if(request.getArbitratorId()!=null) arbitrator = arbitratorRepository.findById(request.getArbitratorId()).get();
        var bank = bankRepository.findById(request.getBankId());
        caseObj.setArbitrator(arbitrator);
        if(bank.isEmpty()) throw new RuntimeException("bank id does not exist");
        caseObj.setBank(bank.get());
        caseObj.setHearingsCount(0);
        caseObj.setOrdersCount(0);
        caseObj.setCommunicationCount(0);
        caseRepository.save(caseObj);

    }

    public void addArbitrator(ArbitratorRequest request) {
            var arbitratorObj = Helper.Creator.createArbitrator(request);
            arbitratorRepository.save(arbitratorObj);
    }
    public void addBank(BankRequest request) {
        var bank = Helper.Creator.createBank(request);
        bankRepository.save(bank);

        var password = request.getUsername()+"atResolute"+new Random().nextInt(1000000);
        var bankUser = new User();
                bankUser.setUserName(request.getUsername());
                bankUser.setPassword(ApplicationUtility.encryptPassword(password));
                bankUser.setRole("bank");
        userService.createUser(bankUser);
        var email = EmailDetails.builder()
                .to(request.getEmail())
                .subject("congratulations for connecting with resolute")
                .body(String.format("""
                        congratulations on signing up on with jmswift.com
                        your username is : %s
                        your password is : %s
                        please change your password from the website as soon as you receive the email
                        """,request.getUsername(),password)
                )
                .build();

        try {
            emailService.sendEmail(email);
        } catch (Exception e) {
            log.warn("email not sent for username "+request.getUsername());
        }
    }
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;


    @Autowired
    private DocumentRepository documentRepository;
    /**
     * this method will return list of all documents on server according to the structured model
     * */
    public List<DocumentResponse> getDocumentList() {
       return documentRepository.findAll().stream().map(Helper.Convert::convertDocumentResponse).toList();
    }

    public ArbitratorResponse getArbitratorById(Integer arbitratorId) {
        var arbOptional = arbitratorRepository.findById(arbitratorId);
        if(arbOptional.isEmpty()) throw new RuntimeException("arbitrator ID does not exist");
        return Helper.Convert.convertArbitratorResponse(arbOptional.get());
    }

    public List<ArbitratorResponse> getArbitratorList() {
        var arbitratorList = arbitratorRepository.findAll();
        return arbitratorList.stream().map(Helper.Convert::convertArbitratorResponse).collect(Collectors.toList());
    }

    public AdminCaseResponse getCaseById(Integer caseId) {
        var caseOptional = caseRepository.findById(caseId);
        if(caseOptional.isEmpty()) throw new RuntimeException("Case ID Does Not Exist");
        return Helper.Convert.convertAdminCaseResponse(caseOptional.get());
    }

    public List<CaseResponse> getSearchResponse(String searchParameter, Date date) {
        if(date==null&&searchParameter==null) throw new RuntimeException("no search parameters provided");
        List<BankCase> cases = caseRepository.findAll();
        List<BankCase> caseList = new LinkedList<>();
        List<CaseResponse> caseResponseList;
        if(searchParameter!=null){
            cases.forEach(item->{
                if(item.contains(searchParameter)) caseList.add(item);
            });
        }
        if(date!=null){
            cases.forEach(item->{


                if(item.getCreatedAt().equals(date.toInstant())) caseList.add(item);
                if(item.getSocFillingDate()!=null&&date.equals(item.getSocFillingDate()))  caseList.add(item);
            });
        }
        caseResponseList = caseList.stream().map(Helper.Convert::convertCaseResponse).collect(Collectors.toList());
        return caseResponseList;
    }

	public List<BankResponse> getBanksList() {
		// TODO Auto-generated method stub
		var banks = bankRepository.findAll();
		LinkedList<BankResponse> bankResponses = new LinkedList<>();
		banks.forEach(item->{
			bankResponses.add(Helper.Convert.convertBankResponse(item));
			bankResponses.getLast().setSerialNo(bankResponses.size()+1);
		});
		return bankResponses;
	}
	public List<CaseResponse> getCaseList() {
		// TODO Auto-generated method stub
		var cases = caseRepository.findAll();
        return cases.stream().map(Helper.Convert::convertCaseResponse).collect(Collectors.toCollection(LinkedList::new));
	}
	
	//to be updated later
    public BankResponse getBankById(Integer bankId) {
        return bankRepository.findById(bankId)
                .map(Helper.Convert::convertBankResponse)
                .orElseThrow(()->new RuntimeException("bank Id not found")); // Or return a default BankResponse if not found
    }

    public void deleteBank(Integer bankId) {
		bankRepository.deleteById(bankId);
	}
	public void updateBank(BankRequest request, Integer bankId) {
		var bankOptional = bankRepository.findById(bankId);
		if(bankOptional.isEmpty()) throw new RuntimeException("bank Id does not exist");
		var bank = Helper.Creator.createBank(request);
		bank.setId(bankId);
        bank.setCreatedAt(bankOptional.get().getCreatedAt());
		bankRepository.save(bank);
	}
	public BorrowerResponse getBorrowerById(Integer borrowerId) {
		var borrower = borrowerRepository.findById(borrowerId);
		if(borrower.isEmpty()) throw new RuntimeException("borrower Id not found");
		
		return Helper.Convert.convertBorrowerResponse(borrower.get());
	}
	public List<BorrowerResponse> getBorrwerList() {
		var list = borrowerRepository.findAll();
		var resList = new LinkedList<BorrowerResponse>();
		list.forEach(item->resList.add(Helper.Convert.convertBorrowerResponse(item)));
		return resList;
	}

    public void updateArbitrator(ArbitratorRequest request, Integer arbitratorId) {
        var arbitratorOptional = arbitratorRepository.findById(arbitratorId);
        if(arbitratorOptional.isEmpty()) throw new RuntimeException("arbitrator Id does not exist");
        var arbitrator = Helper.Creator.createArbitrator(request);
        arbitrator.setId(arbitratorId);
        arbitrator.setRegistrationDate(arbitratorOptional.get().getRegistrationDate());
        arbitratorRepository.save(arbitrator);
    }

    public void updateCase(AdminCaseRequest request, Integer caseId) {
        var caseOptional = caseRepository.findById(caseId);
        var caseObj =  Helper.Creator.createCase(request);
        var arbitrator = arbitratorRepository.findById(request.getArbitratorId());
        var bank = bankRepository.findById(request.getBankId());
        if(caseOptional.isEmpty()) throw new RuntimeException("case id does not exist");
        if(arbitrator.isEmpty()) throw new RuntimeException("arbitrator id does not exist");
        if(bank.isEmpty()) throw new RuntimeException("bank id does not exist");
        caseObj.setArbitrator(arbitrator.get());
        caseObj.setBank(bank.get());
        caseObj.setId(caseId);
        caseObj.setCommunicationCount(caseOptional.get().getCommunicationCount());
        caseObj.setOrdersCount(caseOptional.get().getOrdersCount());
        caseObj.setHearingsCount(caseOptional.get().getHearingsCount());
        caseObj.setCreatedAt(caseOptional.get().getCreatedAt());
        caseRepository.save(caseObj);

    }

    public List<CaseResponse> getSearchResponseArbitratorId(String searchParameter, Date date, Integer arbitratorId) {
        if(date==null&&searchParameter==null) throw new RuntimeException("no search parameters provided");
        List<BankCase> cases = caseRepository.findByArbitrator_Id(arbitratorId);
        List<BankCase> caseList = new LinkedList<>();
        List<CaseResponse> caseResponseList;
        if(searchParameter!=null){
            cases.forEach(item->{
                if(item.contains(searchParameter)) caseList.add(item);
            });
        }
        if(date!=null){
            cases.forEach(item->{
                if(item.getSocFillingDate().equals(date)) caseList.add(item);
            });
        }
        caseResponseList = caseList.stream().map(Helper.Convert::convertCaseResponse).collect(Collectors.toList());
        return caseResponseList;
    }

    public List<CaseResponse> getSearchResponseBankId(String searchParameter, Date date, Integer bankId) {
        if(date==null&&searchParameter==null) throw new RuntimeException("no search parameters provided");
        List<BankCase> cases = caseRepository.findByBank_Id(bankId);
        List<BankCase> caseList = new LinkedList<>();
        List<CaseResponse> caseResponseList;
        if(searchParameter!=null){
            cases.forEach(item->{
                if(item.contains(searchParameter)) caseList.add(item);
            });
        }
        if(date!=null){
            cases.forEach(item->{
                if(item.getSocFillingDate().equals(date)) caseList.add(item);
            });
        }
        caseResponseList = caseList.stream().map(Helper.Convert::convertCaseResponse).collect(Collectors.toList());
        return caseResponseList;
    }

    public List<AdminCaseResponse> getCasesByBankIdAndType(Integer bankId, String type) {
        var caseListByBankId = caseRepository.findByBank_IdAndCaseType(bankId,type);
        return caseListByBankId.stream().map(Helper.Convert::convertAdminCaseResponse).toList();
    }

    public Map<String, Long> getTypeCountsByBankId(Integer bankId) {
        Map<String, Long> caseTypeCount = new HashMap<>();
        caseTypeRepository.findAll().forEach(caseType -> {

            caseTypeCount.put(caseType.getType(),caseRepository.countByCaseType(caseType.getType()));
        });
        return caseTypeCount;
    }

    public void createType(String type) {
        CaseType caseType = new CaseType();
        caseType.setType(type);
        caseTypeRepository.save(caseType);
    }

    public List<AdminCaseResponse> getCasesByBankIdAndTypeAndStatus(Integer bankId, String type, String status) {
        return caseRepository.findByBank_IdAndCaseTypeAndCaseStatus(bankId,type,status).stream().map(Helper.Convert::convertAdminCaseResponse).toList();
    }

    public Long getCountOfCaseByBankIdAndTypeAndStatus(Integer bankId, String type, String status) {
    return caseRepository.countByBank_IdAndCaseTypeAndCaseStatus(bankId,type,status);
    }
}
