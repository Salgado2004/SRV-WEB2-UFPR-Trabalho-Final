package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.infra.connection.sql.RequestSQLDao;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RequestException;
import br.ufpr.webII.trabalhoFinal.infra.repository.EquipmentDao;
import br.ufpr.webII.trabalhoFinal.infra.repository.RequestDao;
import br.ufpr.webII.trabalhoFinal.infra.repository.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufpr.webII.trabalhoFinal.domain.dto.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;
import br.ufpr.webII.trabalhoFinal.infra.service.ValidateStatusChangeContext;
import br.ufpr.webII.trabalhoFinal.infra.service.ValidateStatusChangeByClient;
import br.ufpr.webII.trabalhoFinal.infra.service.ValidateStatusChangeByEmployee;

import br.ufpr.webII.trabalhoFinal.domain.user.customer.Customer;

import java.util.ArrayList;

@Service
public class RequestService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private UserDao userDao;

//    @Autowired
//    private RequestSQLDao requestSQLDao;

    public void createRequest(RequestInputDTO data) {
        try {
            EquipmentCategory equipment = equipmentDao.select(data.equipmentCategoryId());
            Customer customer = userDao.selectCustomer(data.customerId());

            Request request = new Request(data, customer, equipment);
            requestDao.insert(request);
        } catch (IllegalArgumentException e) {
            throw new RequestException(e.getMessage());
        }
    }

    public ArrayList<Request> listRequests() {
        return requestDao.selectAll();
    }

    public Request detailRequest(Long id) {
        return requestDao.select(id);
    }

    public void updateRequest(RequestUpdateDTO data) {
        ValidateStatusChangeContext context = new ValidateStatusChangeContext();

        try {
            // Verificar o tipo de usuário usando o campo userType do DTO
            if ("CLIENT".equals(data.userType())) { // Verifica se é cliente
                context.setStrategy(new ValidateStatusChangeByClient());
            } else if ("EMPLOYEE".equals(data.userType())) { // Verifica se é funcionário
                context.setStrategy(new ValidateStatusChangeByEmployee());
            } else {
                throw new RequestException("Usuário não autorizado a realizar essa ação");
            }
            /*   codigo comentado pois é necessário fazer a integração com o bd
            if (context.isValid(data)) {
                // Implementar o DAO de SQL
                requestSQLDao.update(data);
            } else {
                throw new RequestException("Não é possível alterar o status da requisição para " + data.nextStatus());
            }
            */
        } catch (IllegalArgumentException e) {
            throw new RequestException(e.getMessage());
        }
    }
}
