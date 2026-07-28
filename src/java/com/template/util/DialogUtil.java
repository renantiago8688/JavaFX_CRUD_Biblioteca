package com.template.util;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DialogUtil {

    public static void mostrarAlerta(String titulo, String cabecalho, String mensagem, Alert.AlertType tipo) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
