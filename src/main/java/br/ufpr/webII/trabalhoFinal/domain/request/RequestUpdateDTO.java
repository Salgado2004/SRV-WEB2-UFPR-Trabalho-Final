package br.ufpr.webII.trabalhoFinal.domain.request;

import br.ufpr.webII.trabalhoFinal.domain.request.status.RequestStatusCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record RequestUpdateDTO(
        @NotNull Long requestId,
        Long inChargeEmployeeId,
        Long senderEmployeeId,
        @NotNull RequestStatusCategory currentStatus,
        @NotNull RequestStatusCategory nextStatus,
        @PastOrPresent LocalDateTime datetime,
        String rejectionReason,
        Double budget,
        String repairDesc,
        String customerOrientations,
        @NotBlank String userType // Novo campo para identificar o tipo de usuário
) {}
