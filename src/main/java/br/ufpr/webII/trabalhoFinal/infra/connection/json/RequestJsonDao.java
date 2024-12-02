/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

import br.ufpr.webII.trabalhoFinal.domain.request.Request;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.RequestUpdateDTO;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatus;
import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusOutputDTO;
import br.ufpr.webII.trabalhoFinal.infra.connection.RequestDao;
import br.ufpr.webII.trabalhoFinal.infra.exceptions.ResourceNotFoundException;
import br.ufpr.webII.trabalhoFinal.infra.repository.EquipmentDao;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;

/**
 *
 * @author mateus
 */
public class RequestJsonDao extends RequestDao {
    
    public static RequestJsonDao requestDao;
    
    private RequestJsonDao(){}

    public static RequestDao getRequestJsonDao() {
        if(requestDao == null){
            return requestDao = new RequestJsonDao();
        }else
            return requestDao;    
    }
    
    @Autowired
    JsonFileService jsonService;

    @Autowired
    EquipmentDao equipmentDao;
    
    @Autowired
    CustomerJsonDao custumerDao;

    @Override
    public void insert(Request element) throws Exception {
        try{
            List<RequestOutputDTO> data = jsonService.readObjectFromFile("requests.json", new TypeReference<>() {});
            Long id = (long) (data.size() + 1);
            element.setId(id);
            data.add(new RequestOutputDTO(element));
            jsonService.writeJsonToFile("requests.json", data);

            List<RequestStatusOutputDTO> status = jsonService.readObjectFromFile("requestStatuses.json", new TypeReference<>() {});
            RequestStatus requestStatus = element.getRequestStatus().get(0);
            requestStatus.setRequestStatusId((long) (status.size() + 1));
            status.add(new RequestStatusOutputDTO(requestStatus));
            jsonService.writeJsonToFile("requestStatuses.json", status);
        } catch (IOException e) {
            System.out.println("Erro ao consultar arquivos: "+ e.getMessage());
        }   
    }

    @Override
    public Request getById(Long id) throws Exception {
        try {
            List<RequestOutputDTO> data = jsonService.readObjectFromFile("requests.json", new TypeReference<>() {
            });
            List<RequestStatusOutputDTO> status = jsonService.readObjectFromFile("requestStatuses.json", new TypeReference<>() {
            });

            for (RequestOutputDTO request : data) {
                if (request.requestId().equals(id)) {
                    Request newRequest = new Request(request);
                    newRequest.setEquipmentCategory(equipmentDao.select(request.equipmentCategoryId()));
                    newRequest.setCustomer(custumerDao.getById(request.customerId()));
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

    @Override
    public void update(Request objeto) throws Exception {
        try{
            List<RequestOutputDTO> data = jsonService.readObjectFromFile("requestStatuses.json", new TypeReference<>() {});
            for(RequestOutputDTO request : data){
                if(Objects.equals(objeto.getId(), request.requestId())){
                    data.remove(request);
                    data.add(new RequestOutputDTO(objeto));
                    jsonService.writeJsonToFile("requestStatuses.json", data);

                }
            }
            
        }catch(IOException e){
            System.out.println("Erro ao acessar arquivos: "+e.getMessage());
        }
    }

    @Override
    public void delete(Request objeto) throws Exception {
        throw new UnsupportedOperationException("Uma requisição não pode ser Deletada."); 
    }

    @Override
    public List<Request> listAll() throws Exception {
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
                    newRequest.setCustomer(custumerDao.getById(request.customerId()));
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

    @Override
    public void insertStatus(RequestStatus requestStatus) throws Exception {
        try{
            List<RequestStatusOutputDTO> data = jsonService.readObjectFromFile("requestStatuses.json", new TypeReference<>() {
            });
            data.add(new RequestStatusOutputDTO(requestStatus));
            jsonService.writeJsonToFile("requestStatuses.json", data);
        } catch(Exception e){
            System.out.println("Erro ao acessar o arquivo: "+e.getMessage());
        }
    }

    @Override
    public List<Request> listAll(String query, Long id) throws Exception {
        return null;
    }
}
