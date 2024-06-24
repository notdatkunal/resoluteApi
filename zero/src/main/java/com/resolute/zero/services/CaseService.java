package com.resolute.zero.services;



import com.resolute.zero.domains.*;
import com.resolute.zero.repositories.CommunicationRepository;
import com.resolute.zero.requests.AdminCommRequest;
import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.repositories.CaseOrderRepository;
import com.resolute.zero.requests.AdminOrderRequest;
import com.resolute.zero.requests.CaseHearingRequest;
import com.resolute.zero.repositories.ProceedingRepository;
import com.resolute.zero.requests.HearingResponse;
import com.resolute.zero.responses.*;
import com.resolute.zero.utilities.CodeComponent;
import com.resolute.zero.utilities.MetaDocInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.CaseRepository;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CaseService {
	
	
	@Autowired
	private final CaseRepository caseRepository;
	@Autowired
	private final ProceedingRepository proceedingRepository;
	private final CaseOrderRepository caseOrderRepository;

	public CaseHistoryResponse getCaseHistoryByCaseId(Integer caseId) {
		var obj = CaseService.extracted(caseRepository,caseId);
	    return Helper.Convert.convertCaseHistoryResponse(obj);
	}

	
	public static BankCase extracted(CaseRepository repository,Integer caseId) {
		var caseOptional = repository.findById(caseId);
		if(caseOptional.isEmpty()) throw AppException.builder().data(ResponseEntity.of(ProblemDetail
						.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,"case id does not exist in database"))
				.build()).build();
		return caseOptional.get();
	}



	public void createHearingByCaseId(Integer caseId, CaseHearingRequest caseHearingRequest) {
		var obj = CaseService.extracted(caseRepository,caseId);
		Proceeding hearing = Helper.Creator.createProceeding(caseHearingRequest);
		obj.getProceeding().add(hearing);
		obj.setHearingsCount(obj.getHearingsCount()+1);
		if(obj.getHearingsCount()<10) {
			hearing.setOrderType("H0" + obj.getHearingsCount());
		}
		else {
			hearing.setOrderType("H" + obj.getHearingsCount());
		}
		proceedingRepository.save(hearing);
		caseRepository.save(obj);
	}

	public List<HearingResponse> getHearingsByCaseId(Integer caseId) {
		return CaseService.extracted(caseRepository,caseId)
				.getProceeding()
				.stream()
				.map(Helper.Convert::convertHearingResponse)
				.toList();
	}

	public void updateHearingByHearingId(Integer hearingId, Date date) {
		var proceedingOptional = proceedingRepository.findById(hearingId);
		if(proceedingOptional.isEmpty()) throw new RuntimeException("hearing id does not exist");
		var proceeding = proceedingOptional.get();
		proceeding.setHearingDate(date);
		proceedingRepository.save(proceeding);
	}

	public void deleteHearingById(Integer hearingId) {
		var proceeding = proceedingRepository.findById(hearingId);
		if(proceeding.isEmpty()) throw new RuntimeException("hearing id does not exist");
		var obj = caseRepository.findByProceeding_Id(hearingId);
		if(obj.isEmpty()) throw new RuntimeException("hearing is orphaned");
		obj.get().getProceeding().remove(proceeding.get());
		caseRepository.save(obj.get());
		proceedingRepository.deleteById(hearingId);
	}
	@Autowired
	private CodeComponent codeComponent;
	@Autowired
	private MediaService mediaService;
    private final CommunicationRepository communicationRepository;

    public ResponseEntity<?> createOrderByCaseId(AdminOrderRequest adminOrder) throws IOException {

		var obj = CaseService.extracted(caseRepository,adminOrder.getCaseId());
		obj.setOrdersCount(obj.getOrdersCount()+1);

		CaseOrder caseOrder = Helper.Creator.createCaseOrder(adminOrder);
		{	//creating for order sequence
			if (obj.getOrdersCount() < 10)
				caseOrder.setSequence("K0" + obj.getOrdersCount());
			else
				caseOrder.setSequence("K" + obj.getOrdersCount());
		}
		MetaDocInfo metaDocInfo = codeComponent.getMetaCode("order", caseOrder.getSequence(), adminOrder.getCaseId(),0, adminOrder.getFile());
		mediaService.uploadFile(metaDocInfo);
		mediaService.saveDocumentInDB(metaDocInfo);
		caseOrder.setFileName(metaDocInfo.getFileName());
		obj.getOrders().add(caseOrder);
		caseOrderRepository.save(caseOrder);
		caseRepository.save(obj);
		return ResponseEntity.ok("order created");
	}

	public List<AdminOrderResponse> getCaseOrdersByCaseId(Integer caseId) {
		var obj = extracted(caseRepository,caseId);
		return obj.getOrders().stream().map(Helper.Convert::convertOrderResponse).toList();
	}

    public HearingResponse getHearingDateByHearingId(Integer hearingId) {
		var proceedingOpt = proceedingRepository.findById(hearingId);
		if(proceedingOpt.isEmpty())
			throw AppException.builder()
					.data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,"hearing id does not exist in database")).build())
					.build();
		return Helper.Convert.convertHearingResponse(proceedingOpt.get());
    }

	public AdminOrderResponse getOrderById(Integer orderId) {
		var caseOrderOpt = caseOrderRepository.findById(orderId);
		if(caseOrderOpt.isEmpty())
			throw AppException.builder()
					.data(ResponseEntity.of(ProblemDetail
							.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE
							,"order id does not exist"))
							.build()
					).build();
		return Helper.Convert.convertOrderResponse(caseOrderOpt.get());
	}

	public void updateOrderTypeById(Integer orderId, String type) {
		var caseOrderOpt = caseOrderRepository.findById(orderId);
		if(caseOrderOpt.isEmpty())
			throw AppException.builder()
					.data(ResponseEntity.of(ProblemDetail
									.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE
											,"order id does not exist"))
							.build()
					).build();

		var caseOrder = caseOrderOpt.get();
		caseOrder.setType(type);
		caseOrderRepository.save(caseOrder);
	}

	public void updateOrderDateById(Integer orderId, Date date) {
		var caseOrderOpt = caseOrderRepository.findById(orderId);
		if(caseOrderOpt.isEmpty())
			throw AppException.builder()
					.data(ResponseEntity.of(ProblemDetail
									.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE
											,"order id does not exist"))
							.build()
					).build();
		var caseOrder = caseOrderOpt.get();
		caseOrder.setDate(date);
		caseOrderRepository.save(caseOrder);
	}

	public void deleteOrderById(Integer orderId) {
		var caseOrderOpt = caseOrderRepository.findById(orderId);
		if(caseOrderOpt.isEmpty())
			throw AppException.builder()
					.data(ResponseEntity.of(ProblemDetail
									.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE
											,"order id does not exist"))
							.build()
					).build();
		var caseOrder = caseOrderOpt.get();
		var caseObj = caseRepository.findByOrders_Id(orderId).get();
		caseObj.getOrders().remove(caseOrder);
		caseRepository.save(caseObj);
		caseOrderRepository.delete(caseOrder);
	}



    public List<AdminCaseResponse> getCasesByArbitratorId(Integer arbitratorId) {
		return caseRepository.findByArbitrator_Id(arbitratorId).stream().map(Helper.Convert::convertAdminCaseResponse).toList();
    }

	public List<AdminCaseResponse> getCasesByBankId(Integer bankId) {
		return caseRepository.findByBank_Id(bankId).stream().map(Helper.Convert::convertAdminCaseResponse).toList();
	}
}
