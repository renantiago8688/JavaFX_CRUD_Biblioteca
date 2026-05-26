package com.template;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private Button btnListar;
    @FXML private Button btnAdicionar;
    @FXML private Button btnDeletar;

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtAutor;
    @FXML private TextField txtGenero;
    @FXML private TextField txtDificuldade;

    @FXML private TableView<BibliotecaDTO> tblLivro;
    @FXML private TableColumn<BibliotecaDTO, String> colNome;
    @FXML private TableColumn<BibliotecaDTO, String> colAutor;
    @FXML private TableColumn<BibliotecaDTO, String> colGenero;
    @FXML private TableColumn<BibliotecaDTO, Integer> colDificuldade;




    @FXML
    private void initialize()
    {
        System.out.println("FXML loaded successfully!");
    }
}

