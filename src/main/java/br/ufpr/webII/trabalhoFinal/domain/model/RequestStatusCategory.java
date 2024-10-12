package br.ufpr.webII.trabalhoFinal.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestStatusCategory {
    OPEN,
    BUDGETED,
    REJECTED,
    APPROVED,
    REDIRECTED,
    FIXED,
    PAID,
    FINALIZED;

    @JsonCreator
    public static RequestStatusCategory fromString(String value) {
        return RequestStatusCategory.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return this.name().toLowerCase();
    }
}
