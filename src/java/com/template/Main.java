package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega o arquivo FXML buscando a partir da raiz do projeto
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/template/main.fxml"));
        Parent root = loader.load();

        // Configura a janela (Stage) e a cena (Scene)
        primaryStage.setTitle("Gerenciador de Biblioteca");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}