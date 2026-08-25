package com.template.validator;

import javafx.scene.control.Alert.AlertType;
import static com.template.util.DialogUtil.mostrarAlerta;
import java.util.List;

public class BibliotecaValidador {

    public boolean validarCamposLivro(String nome, String autor, String genero, String dificuldade) {

        List<Validador<String>> validadores = List.of(
                new CampoObrigatorioValidador("Nome", nome),
                new CampoObrigatorioValidador("Autor", autor),
                new CampoObrigatorioValidador("Gênero", genero),
                new CampoObrigatorioValidador("Dificuldade", dificuldade),
                new DificuldadeValidador(dificuldade)
        );

        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                mostrarAlerta(
                        "Erro de Validação",
                        "Não foi possível processar",
                        validador.getMensagemErro(),
                        AlertType.WARNING
                );
                return false;
            }
        }

        return true;
    }
}