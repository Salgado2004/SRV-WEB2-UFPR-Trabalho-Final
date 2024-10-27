package br.ufpr.webII.trabalhoFinal.infra.repository;

import br.ufpr.webII.trabalhoFinal.domain.request.RequestOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestDao {

    @Autowired
    JsonFileService jsonService;

    @Autowired
    EquipmentDao equipmentDao;

    @Autowired
    UserDao userDao;

    public void insert(Request request) {
        try{
            List<RequestOutputDTO> data = jsonService.readObjectFromFile("requests.json", new TypeReference<>() {});
            Long id = (long) (data.size() + 1);
            request.setId(id);
            data.add(new RequestOutputDTO(request));
            jsonService.writeJsonToFile("requests.json", data);

            List<RequestStatusOutputDTO> status = jsonService.readObjectFromFile("requestStatuses.json", new TypeReference<>() {});
            RequestStatus requestStatus = request.getRequestStatus().get(0);
            requestStatus.setRequestStatusId((long) (status.size() + 1));
            status.add(new RequestStatusOutputDTO(requestStatus));
            jsonService.writeJsonToFile("requestStatuses.json", status);
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: "+ e.getMessage());
        }
    }

    public ArrayList<Request> selectAll() {
        try {
            List<RequestOutputDTO> data = jsonService.readObjectFromFile("requests.json", new TypeReference<>() {
            });
            List<RequestStatusOutputDTO> status = jsonService.readObjectFromFile("requestStatuses.json", new TypeReference<>() {
            });

            ArrayList<Request> requests = new ArrayList<>();
            for (RequestOutputDTO request : data) {
                Request newRequest = new Request(request);
                newRequest.setEquipmentCategory(equipmentDao.select(request.equipmentCategoryId()));
                for (RequestStatusOutputDTO requestStatus : status) {
                    if (requestStatus.requestId().equals(request.requestId())) {
                        newRequest.addRequestStatus(new RequestStatus(newRequest, requestStatus));
                    }
                }
                requests.add(newRequest);
            }
            return requests;
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: " + e.getMessage());
            return null;
        }
    }

    public Request select(Long id) {
        try {
            List<RequestOutputDTO> data = jsonService.readObjectFromFile("requests.json", new TypeReference<>() {
            });
            List<RequestStatusOutputDTO> status = jsonService.readObjectFromFile("requestStatuses.json", new TypeReference<>() {
            });

            for (RequestOutputDTO request : data) {
                if (request.requestId().equals(id)) {
                    Request newRequest = new Request(request);
                    newRequest.setEquipmentCategory(equipmentDao.select(request.equipmentCategoryId()));
                    newRequest.setCustomer(userDao.selectCustomer(request.customerId()));
                    for (RequestStatusOutputDTO requestStatus : status) {
                        if (requestStatus.requestId().equals(request.requestId())) {
                            newRequest.addRequestStatus(new RequestStatus(newRequest, requestStatus));
                        }
                    }
                    return newRequest;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: " + e.getMessage());
        }
        throw new ResourceNotFoundException("Solicitação não encontrada");
    }
}
