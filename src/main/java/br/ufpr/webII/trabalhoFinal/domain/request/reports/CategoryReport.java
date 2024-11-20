package br.ufpr.webII.trabalhoFinal.domain.request.reports;

import br.ufpr.webII.trabalhoFinal.domain.equipment.EquipmentCategory;

public record CategoryReport( 
    String category,
    Double budget
    )
{
}