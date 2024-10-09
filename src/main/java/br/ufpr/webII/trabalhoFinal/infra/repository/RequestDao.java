package br.ufpr.webII.trabalhoFinal.infra.repository;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.dto.RequestStatusOutputDTO;
import br.ufpr.webII.trabalhoFinal.domain.model.Request;
import br.ufpr.webII.trabalhoFinal.domain.model.RequestStatus;
import br.ufpr.webII.trabalhoFinal.infra.service.JsonFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RequestDao {

    @Autowired
    JsonFileService jsonService;

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
}
