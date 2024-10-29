package br.ufpr.webII.trabalhoFinal.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record RequestUpdateDTO(
        @NotNull Long requestId,
        Long inChargeEmployeeId,
        Long senderEmployeeId,
        @NotBlank String currentStatus,
        @NotBlank String nextStatus,
        @PastOrPresent LocalDateTime datetime,
        String rejectionReason,
        Double budget,
        String repairDesc,
        String customerOrientations
) {}
