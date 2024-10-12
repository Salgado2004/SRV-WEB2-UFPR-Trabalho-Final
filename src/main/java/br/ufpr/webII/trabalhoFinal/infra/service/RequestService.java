package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestInputDTO;
import br.ufpr.webII.trabalhoFinal.domain.model.Customer;
import br.ufpr.webII.trabalhoFinal.domain.model.EquipmentCategory;
import br.ufpr.webII.trabalhoFinal.domain.model.Request;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.RequestException;
import br.ufpr.webII.trabalhoFinal.infra.repository.EquipmentDao;
import br.ufpr.webII.trabalhoFinal.infra.repository.RequestDao;
import br.ufpr.webII.trabalhoFinal.infra.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RequestService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private UserDao userDao;

    public void createRequest(RequestInputDTO data) {
        try {
            EquipmentCategory equipment = equipmentDao.select(data.equipmentCategoryId());
            Customer customer = userDao.selectCustomer(data.customerId());

            Request request = new Request(data, customer, equipment);
            requestDao.insert(request);
        } catch (IllegalArgumentException e){
            throw new RequestException(e.getMessage());
        }
    }

    public ArrayList<Request> listRequests() {
        return requestDao.selectAll();
    }

    public Request detailRequest(Long id) {
        return requestDao.select(id);
    }
}
