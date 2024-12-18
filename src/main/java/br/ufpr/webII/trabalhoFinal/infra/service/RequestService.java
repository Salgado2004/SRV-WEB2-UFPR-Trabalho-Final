package br.ufpr.webII.trabalhoFinal.infra.service;

import java.util.ArrayList;
import java.util.List;

import br.ufpr.webII.trabalhoFinal.infra.service.strategy.PresentRequestToUserContext;
import br.ufpr.webII.trabalhoFinal.infra.service.strategy.ValidateStatusChangeByClient;
import br.ufpr.webII.trabalhoFinal.infra.service.strategy.ValidateStatusChangeByEmployee;
import br.ufpr.webII.trabalhoFinal.infra.service.strategy.ValidateStatusChangeContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CategoryReport;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CommomReport;
import br.ufpr.webII.trabalhoFinal.domain.request.reports.CommomReportDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;
import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.infra.connection.CustomerDao;
import br.ufpr.webII.trabalhoFinal.infra.connection.DaoFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.EquipmentDao;
import br.ufpr.webII.trabalhoFinal.infra.connection.RequestDao;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RequestException;

@Service
public class RequestService {

    @Autowired
    private DaoFactory daoFactory;

    @Autowired
    private PresentRequestToUserContext requestContext;

    @Autowired
    private TokenService tokenSrv;

    public void createRequest(RequestInputDTO data) {
        try {
            EquipmentDao equipmentDao = daoFactory.getEquipmentDao();
            EquipmentCategory equipment = equipmentDao.getById(data.equipmentCategoryId());

            CustomerDao customerDao = daoFactory.getCustomerDao();
            Customer customer = customerDao.getByUserId(data.customerId());

            RequestDao requestDao = daoFactory.getRequestDao();
            Request request = new Request(data, customer, equipment);
            request.getRequestStatus().add(new RequestStatus(request, RequestStatusCategory.OPEN, data.startDate()));

            requestDao.insert(request);

        } catch (IllegalArgumentException e) {
            throw new RequestException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Request> listRequests(String auth) {
        try {
            String authorization = tokenSrv.getAuthorizationToken(auth);
            String profile = tokenSrv.getProfile(authorization);
            String context = requestContext.showRequestToUser(profile);
            Long userId = tokenSrv.getUserId(authorization);
            RequestDao requestDao = daoFactory.getRequestDao();
            return requestDao.listAll(context, userId);
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
    }

    public Request detailRequest(Long id) {
        try {
            RequestDao requestDao = daoFactory.getRequestDao();
            return requestDao.getById(id);
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
    }

    public void updateRequest(RequestUpdateDTO data) {
        ValidateStatusChangeContext context = new ValidateStatusChangeContext();
        try {
            if ("CUSTOMER".equalsIgnoreCase(data.userType())) {
                context.setStrategy(new ValidateStatusChangeByClient());
            } else if ("EMPLOYEE".equalsIgnoreCase(data.userType())) {
                context.setStrategy(new ValidateStatusChangeByEmployee());
            } else {
                throw new RequestException("Usuário não autorizado a realizar essa ação");
            }

            if (context.isValid(data)) {

                RequestDao requestDao = daoFactory.getRequestDao();

                Request request = requestDao.getById(data.requestId());
                if (data.rejectReason() != null) {
                    request.setRejectReason(data.rejectReason());
                }
                if (data.budget() != null) {
                    request.setBudget(data.budget());
                }
                if (data.repairDesc() != null) {
                    request.setRepairDesc(data.repairDesc());
                }
                if (data.customerOrientations() != null) {
                    request.setCustomerOrientations(data.customerOrientations());
                }
                requestDao.update(request);

                requestDao.insertStatus(
                        new RequestStatus(
                                new Request(data.requestId()),
                                data.nextStatus(),
                                new Employee(data.inChargeEmployeeId()),
                                new Employee(data.senderEmployeeId()),
                                data.datetime()
                        ));

            } else {
                throw new RequestException("Não é possível alterar o status da requisição para " + data.nextStatus());
            }

        } catch (IllegalArgumentException e) {
            throw new RequestException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CommomReport> listCommomReport(CommomReportDTO data) {
        List<CommomReport> report = new ArrayList<>();

        try {
            RequestDao requestDao = daoFactory.getRequestDao();
            report = requestDao.listCommomReport(data.startDate(), data.endDate());
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
        return report;
    }

    public List<CategoryReport> listCategoryReport() {
        List<CategoryReport> report = new ArrayList<>();
        try {
            RequestDao requestDao = daoFactory.getRequestDao();
            report = requestDao.listCategoryReport();
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
        return report;
    }
}
