Tecnologia em Análise e Desenvolvimento de Sistemas

Setor de Educação Profissional e Tecnológica - SEPT

Universidade Federal do Paraná - UFPR

---

*DS140 - Desenvolvimento Web II*

Prof. Dr. Razer A. N. R. Montaño

# SRV-WEB2-UFPR-Trabalho-Final <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="45" height="45"/> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/>
Projeto Back-End desenvolvido como trabalho final da disciplina de Desenvolvimento Web II do curso de Análise e Desenvolvimento de Sistemas

## Alunos
- Alisson Gabriel Santos [@AlissonGSantos](https://github.com/AlissonGSantos)
- Gabriel Alamartini Troni [@Gabriel-Troni](https://github.com/Gabriel-Troni)
- Leonardo Felipe Salgado [@Salgado2004](https://github.com/Salgado2004)
- Mateus Bazan Bespalhok [@Matbaaz](https://github.com/matbaaz)
- Pedro Henrique de Souza [@Pedro-H108](https://github.com/Pedro-H108)
- Raul Ferreira Costa Bana [@raulbana](https://github.com/raulbana)

## Modelo Relacional Do BD PostgreSQL
``` mermaid
erDiagram
    
    Usuario {
        int idUsuario pk
        varchar(100) email uk
        char(11) cpf uk
        varchar(100) nome
        varchar(14) telefone
        char(64) senha

        int id_endereco fk
        bool status
    }
    Usuario ||--|| Cliente : herda
    Usuario ||--|| Funcionario : herda

    Funcionario {
        int idFuncionario pk, fk
        date dtNasc
        bool status
    }

    Cliente {
        int idCliente pk, fk
        bool status
    }
    Cliente }|--|| Endereco : possui
    Cliente ||--o{ Solicitacao : faz

    Endereco {
        int idEndereco pk
        char(8) cep
        char(2) uf
        varchar(32) cidade
        varchar(48) rua
        varchar(48) bairro
        varchar(6) numero
        bool status
    }

    EstadoSolicitacao {
        int idSolicitacao fk
        int idEstado fk
        int idFuncionarioOrigem fk
        int idFuncionarioResponsavel fk
        timestamp dataHora
        bool status
    }
    EstadoSolicitacao }o--|| Funcionario : altera
    Solicitacao ||--o{ EstadoSolicitacao : esta

    Solicitacao {
        int idSolicitacao pk
        varchar(30) descSolicitacao
        varchar(30) descEquipamento
        varchar(255) descDefeito
        money orcamento
        text descricaoManutencao
        text orientacoesCliente
        int idCategoria fk
        int idEquipamento fk
        int idCliente fk
        int idManutencao fk
        bool status
    }

    Categoria{
        int idCategoria pk        
        varchar(30) descCategoria
        bool status
    }
    Categoria ||--o{ Solicitacao : pertence

    Estado {
        int idSolicitacao pk
        varchar(13) descEstado
        bool status
    }
    Estado ||--o{ EstadoSolicitacao : esta


```
