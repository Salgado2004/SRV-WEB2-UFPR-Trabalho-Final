package br.ufpr.webII.trabalhoFinal.controller;

import br.ufpr.webII.trabalhoFinal.infra.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    // Este método recebe um parâmetro opcional para listar receitas filtradas
    @GetMapping("/receitas")
    public List<String> listarReceitas(@RequestParam(required = false) String opcao) {
        // Chama o service para recuperar a lista de receitas
        return receitaService.listarReceitas(opcao);
    }
}

