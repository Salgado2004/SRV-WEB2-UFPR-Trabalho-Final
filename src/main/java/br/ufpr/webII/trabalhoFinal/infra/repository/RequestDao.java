package br.ufpr.webII.trabalhoFinal.infra.repository;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpr.webII.trabalhoFinal.domain.user.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusOutputDTO;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;


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

    public ArrayList<Request> listAll() {
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
                    newRequest.setCustomer(userDao.getById(request.customerId()));
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
                            request.rejectReason(),
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
                new Employee(request.inChargeEmployeeId()),
                new Employee(request.senderEmployeeId()),
                requestStatus
            );

            data.add(status);
            jsonService.writeJsonToFile("requestStatuses.json", data);

        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: " + e.getMessage());
        }
    }

}
