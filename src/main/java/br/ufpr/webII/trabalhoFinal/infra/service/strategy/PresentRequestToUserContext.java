package br.ufpr.webII.trabalhoFinal.infra.service.strategy;

import br.ufpr.webII.trabalhoFinal.infra.exceptions.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mateus
 */

@Component
public class PresentRequestToUserContext {

    public String showRequestToUser(String profile) {
        return switch (profile) {
            case "Customer" -> """
                    SELECT r.id, r.equip_desc, r.defect_desc, rs.status, c.user_id AS customer_id,
                           u.name AS customer_name, u.surname AS customer_surname, u.email AS customer_email,
                           c.phone_number AS customer_phone, c.cpf AS customer_cpf, a.street, a.number, a.district,
                           a.city, a.uf, a.cep, rs.date_time AS created_at
                    FROM public.request r
                    JOIN (
                        SELECT DISTINCT ON (request_id) request_id, status, date_time
                        FROM public.request_status
                        ORDER BY request_id, date_time DESC
                    ) rs ON r.id = rs.request_id
                    JOIN public.customer c ON r.customer_id = c.user_id
                    JOIN public.user u ON c.user_id = u.id
                    JOIN public.address a ON c.address_id = a.id
                    WHERE r.customer_id = ? AND r.active = TRUE;
                    """;
            case "Employee" -> """
                        SELECT r.id, r.equip_desc, r.defect_desc, rs.status, c.user_id AS customer_id,
                                               u.name AS customer_name, u.surname AS customer_surname, u.email AS customer_email,
                                               c.phone_number AS customer_phone, c.cpf AS customer_cpf, a.street, a.number, a.district,
                                               a.city, a.uf, a.cep, rs.date_time AS created_at
                                        FROM public.request r
                                        JOIN (
                                            SELECT DISTINCT ON (request_id) request_id, status, date_time
                                            FROM public.request_status
                                            ORDER BY request_id, date_time DESC
                                        ) rs ON r.id = rs.request_id
                                        JOIN public.customer c ON r.customer_id = c.user_id
                                        JOIN public."user" u ON c.user_id = u.id
                                        JOIN public.address a ON c.address_id = a.id
                                        WHERE r.id IN
                                        (SELECT request_id FROM public.request_status rs WHERE
                                        (status = 'OPEN' AND date_time = (SELECT MAX(date_time) FROM public.request_status WHERE request_id = rs.request_id)) OR
                        				(in_charge_employee_id = ? AND date_time = (SELECT MAX(date_time) FROM public.request_status
                        				WHERE request_id = rs.request_id AND in_charge_employee_id NOTNULL))) AND r.active = TRUE;
                    """;
            default -> throw new TokenException("Perfil não encontrado");
        };
    }

}
