package br.ufpr.webII.trabalhoFinal.domain.request.reports;

import java.time.LocalDateTime;

import jakarta.validation.constraints.PastOrPresent;

public record CommomReportDTO(
    @PastOrPresent
    LocalDateTime startDate,
    @PastOrPresent
    LocalDateTime endDate
) {}
