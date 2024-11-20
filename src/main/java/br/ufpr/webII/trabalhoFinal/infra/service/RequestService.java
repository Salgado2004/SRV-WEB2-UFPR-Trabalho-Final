package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import br.ufpr.webII.trabalhoFinal.infra.connection.CustomerDao;
import br.ufpr.webII.trabalhoFinal.infra.connection.DaoFactory;
import br.ufpr.webII.trabalhoFinal.infra.connection.EquipmentDao;
import br.ufpr.webII.trabalhoFinal.infra.connection.RequestDao;
import br.ufpr.webII.trabalhoFinal.infra.connection.sql.RequestSQLDao;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;
import br.ufpr.webII.trabalhoFinal.infra.service.ValidateStatusChangeContext;
import br.ufpr.webII.trabalhoFinal.infra.service.ValidateStatusChangeByClient;
import br.ufpr.webII.trabalhoFinal.infra.service.ValidateStatusChangeByEmployee;

import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private DaoFactory daoFactory;



    @Autowired
    private RequestSQLDao requestSQLDao;

    public void createRequest(RequestInputDTO data) {
        try {
            EquipmentDao equipmentDao = daoFactory.getEquipmentDao();
            EquipmentCategory equipment = equipmentDao.getById(data.equipmentCategoryId());

            CustomerDao customerDao = daoFactory.getCustomerDao();
            Customer customer = customerDao.getById(data.customerId());

            RequestDao requestDao = daoFactory.getRequestDao();
            Request request = new Request(data, customer, equipment);

            requestDao.insert(request);
            requestDao.insertStatus(new RequestStatus(request, RequestStatusCategory.OPEN, data.startDate()));

        } catch (IllegalArgumentException e) {
            throw new RequestException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Request> listRequests() {
        try {
            RequestDao requestDao = daoFactory.getRequestDao();
            return requestDao.listAll();
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
            if ("CLIENT".equals(data.userType())) {
                context.setStrategy(new ValidateStatusChangeByClient());
            } else if ("EMPLOYEE".equals(data.userType())) {
                context.setStrategy(new ValidateStatusChangeByEmployee());
            } else {
                throw new RequestException("Usuário não autorizado a realizar essa ação");
            }

            if (context.isValid(data)) {

                RequestDao requestDao = daoFactory.getRequestDao();

                Request request = requestDao.getById(data.requestId());
                request.setRejectionReason(data.rejectionReason());
                request.setBudget(data.budget());
                request.setRepairDesc(data.repairDesc());
                request.setCustomerOrientations(data.customerOrientations());
                requestDao.update(request);

                requestDao.insertStatus(
                        new RequestStatus(
                                new Request(data.requestId()),
                                data.currentStatus(),
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
}
