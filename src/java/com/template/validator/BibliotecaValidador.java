package com.template.validator;

import javafx.scene.control.Alert.AlertType;
import static com.template.util.DialogUtil.mostrarAlerta;

public class BibliotecaValidador {

    public boolean validarCamposLivro(String nome, String autor, String genero, String dificuldade) {

        // 1. Barreira de Segurança: Checa nulos e campos vazios
        if (isVazio(nome) || isVazio(autor) || isVazio(genero) || isVazio(dificuldade)) {
            mostrarAlerta(
                    "Campos Incompletos",
                    "Não foi possível processar",
                    "Todos os campos (Nome, Autor, Gênero e Dificuldade) devem ser preenchidos.",
                    AlertType.WARNING
            );
            return false;
        }

        // 2. Validação e conversão do número de dificuldade
        if (!validarDificuldade(dificuldade)) {
            return false;
        }

        return true;
    }

    public boolean validarDificuldade(String dificuldade) {
        try {
            Integer.parseInt(dificuldade.trim());
            return true;
        } catch (NumberFormatException e) {
            mostrarAlerta(
                    "Erro de Digitação",
                    "Valor Inválido",
                    "A dificuldade deve ser um número inteiro válido.",
                    AlertType.ERROR
            );
            return false;
        }
    }

    private boolean isVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}