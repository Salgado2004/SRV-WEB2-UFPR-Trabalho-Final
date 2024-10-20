package br.ufpr.webII.trabalhoFinal.infra.repository;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.RequestStatusOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.model.RequestStatusCategory;
import br.ufpr.webII.trabalhoFinal.domain.model.Request;
import br.ufpr.webII.trabalhoFinal.domain.model.RequestStatus;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;

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

    public void update (RequestUpdateDTO request, RequestStatusCategory requestStatus) {
        try {
            List<RequestOutputDTO> storedRequests = jsonService.readObjectFromFile("requests.json", new TypeReference<>() {});

            // Update em requests.json
            for (RequestOutputDTO storedRequest : storedRequests) {
                if (storedRequest.requestId().equals(request.requestId())) {
                    //Pegar todos os campos do Json antes de dar o remove, exceto os que ja tem no model Request
                    Long equipmentCategoryId = storedRequest.equipmentCategoryId();
                    String equipmentDesc = storedRequest.equipmentDesc();
                    String defectDesc = storedRequest.defectDesc();
                    Long customerId = storedRequest.customerId();

                    storedRequests.remove(storedRequest);

                    storedRequests.add(new RequestOutputDTO(
                            request.requestId(),
                            equipmentCategoryId,
                            equipmentDesc,
                            defectDesc,
                            request.budget(),
                            request.repairDesc(),
                            request.customerOrientations(),
                            customerId
                    ));

                    break;
                }
            }
            jsonService.writeJsonToFile("requests.json", storedRequests);

            // Insert em requestStatuses.json (inserir a mudança no histórico de status)
            List<RequestStatusOutputDTO> data = jsonService.readObjectFromFile("requestStatuses.json", new TypeReference<>() {});
            Long id = (long) (data.size() + 1);
            RequestStatusOutputDTO status = new RequestStatusOutputDTO(
                request.requestId(),
                Date.from(request.datetime().atZone(ZoneId.systemDefault()).toInstant()),
                request.inChargeEmployeeId(),
                request.senderEmployeeId(),
                requestStatus
            );

            data.add(status);
            jsonService.writeJsonToFile("requestStatuses.json", data);

        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: " + e.getMessage());
        }
    }
}
