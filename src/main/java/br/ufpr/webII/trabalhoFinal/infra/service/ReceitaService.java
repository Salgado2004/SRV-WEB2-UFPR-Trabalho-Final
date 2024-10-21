package br.ufpr.webII.trabalhoFinal.infra.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceitaService {

    // Lista de receitas fictícias para teste
    private List<String> receitas = new ArrayList<>();

    public ReceitaService() {
        // Inicializa a lista com dados fictícios
        receitas.add("Venda de produtos: R$ 5000,00");
        receitas.add("Serviços prestados: R$ 3000,00");
        receitas.add("Investimentos: R$ 2000,00");
    }

    // Este método retorna a lista de receitas com base em uma opção de filtro
    public List<String> listarReceitas(String opcao) {
        // Caso não haja filtro, retorna todas as receitas
        if (opcao == null || opcao.isEmpty()) {
            return receitas;
        }

        // Filtra as receitas de acordo com a opção fornecida
        List<String> receitasFiltradas = new ArrayList<>();
        for (String receita : receitas) {
            if (receita.toLowerCase().contains(opcao.toLowerCase())) {
                receitasFiltradas.add(receita);
            }
        }
        return receitasFiltradas;
    }
}
