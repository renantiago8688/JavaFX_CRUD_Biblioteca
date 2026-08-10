package com.template.util;

import com.template.model.dto.BibliotecaDTO;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class FormUtil {


    public static void limparCampos(TableView<?> tabela, TextField... campos) {
        for (TextField campo : campos) {
            if (campo != null) {
                campo.clear();
            }
        }
        if (tabela != null) {
            tabela.getSelectionModel().clearSelection();
        }
    }

    public static String[] extrairCampos(TextField... campos) {
        String[] valores = new String[campos.length];
        for (int i = 0; i < campos.length; i++) {
            valores[i] = campos[i] != null ? campos[i].getText() : "";
        }
        return valores;
    }

    public static void preencherCampos(BibliotecaDTO dto, TextField txtNome, TextField txtAutor, TextField txtGenero, TextField txtDificuldade) {
        if (dto != null) {
            txtNome.setText(dto.getNome());
            txtAutor.setText(dto.getAutor());
            txtGenero.setText(dto.getGenero());
            txtDificuldade.setText(String.valueOf(dto.getDificuldade()));
        }
    }
}