package br.ufpr.webII.trabalhoFinal.domain.request.reports;

import java.time.LocalDateTime;

public record CommomReport( 
    LocalDateTime date,
    Double budget)
{
}