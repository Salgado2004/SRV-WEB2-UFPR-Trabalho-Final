package br.ufpr.webII.trabalhoFinal.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record RequestInputDTO(
        @NotNull
        Long equipmentCategoryId,
        @NotBlank
        String equipmentDesc,
        @NotBlank
        @Size(max = 255)
        String defectDesc,
        @NotNull
        Long customerId,
        @PastOrPresent
        LocalDateTime startDate) {
}
