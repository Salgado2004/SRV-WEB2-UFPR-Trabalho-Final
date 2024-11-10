package br.ufpr.webII.trabalhoFinal.infra.service;

import br.ufpr.webII.trabalhoFinal.domain.dto.RequestUpdateDTO;

public interface ValidateStatusChangeInterface {
    boolean validateStatusChange(RequestUpdateDTO data);
}