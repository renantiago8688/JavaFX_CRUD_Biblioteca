package com.template.controller;

import static com.template.util.DialogUtil.*;

import com.template.model.dao.BibliotecaDAO;
import com.template.model.dto.BibliotecaDTO;
import com.template.validator.BibliotecaValidador;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;

public class MainController {

    @FXML private Button btnSalvar;
    @FXML private Button btnListar;
    @FXML private Button btnAdicionar;
    @FXML private Button btnDeletar;

    @FXML private TextField txtNome;
    @FXML private TextField txtAutor;
    @FXML private TextField txtGenero;
    @FXML private TextField txtDificuldade;

    @FXML private Label lblContador;

    @FXML private TableView<BibliotecaDTO> tblLivro;
    @FXML private TableColumn<BibliotecaDTO, Integer> colId;
    @FXML private TableColumn<BibliotecaDTO, String> colNome;
    @FXML private TableColumn<BibliotecaDTO, String> colAutor;
    @FXML private TableColumn<BibliotecaDTO, String> colGenero;
    @FXML private TableColumn<BibliotecaDTO, Integer> colDificuldade;

    // Instância do Validador
    private final BibliotecaValidador validador = new BibliotecaValidador();

    @FXML
    private void carregarBiblioteca() {
        BibliotecaDAO objBibliotecaDAO = new BibliotecaDAO();
        ArrayList<BibliotecaDTO> listaBiblioteca = objBibliotecaDAO.listarLivro();
        tblLivro.setItems(FXCollections.observableArrayList(listaBiblioteca));

        int totalRegistros = listaBiblioteca.size();
        if (lblContador != null) {
            lblContador.setText("Total de livros cadastrados: " + totalRegistros);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtAutor.setText("");
        txtGenero.setText("");
        txtDificuldade.setText("");
        tblLivro.getSelectionModel().clearSelection();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        String nome = txtNome.getText();
        String autor = txtAutor.getText();
        String genero = txtGenero.getText();
        String dificuldadeStr = txtDificuldade.getText();

        // Passa a responsabilidade de validação para a classe validadora
        if (!validador.validarCamposLivro(nome, autor, genero, dificuldadeStr)) {
            return;
        }

        int difficulty = Integer.parseInt(dificuldadeStr.trim());

        BibliotecaDTO objBibliotecaDTO = new BibliotecaDTO();
        objBibliotecaDTO.setNome(nome);
        objBibliotecaDTO.setAutor(autor);
        objBibliotecaDTO.setGenero(genero);
        objBibliotecaDTO.setDificuldade(difficulty);

        BibliotecaDAO objBibliotecaDAO = new BibliotecaDAO();
        objBibliotecaDAO.salvarLivro(objBibliotecaDTO);

        carregarBiblioteca();
        limparCampos();

        mostrarAlerta("Sucesso", null, "Livro salvo com sucesso!", AlertType.INFORMATION);
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        BibliotecaDTO livroSelecionado = tblLivro.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            mostrarAlerta("Aviso", "Ação Necessária",
                    "Por favor, selecione um livro na tabela para poder alterar.", AlertType.WARNING);
            return;
        }

        String nome = txtNome.getText();
        String autor = txtAutor.getText();
        String genero = txtGenero.getText();
        String dificuldadeStr = txtDificuldade.getText();

        // Reutiliza a mesma validação
        if (!validador.validarCamposLivro(nome, autor, genero, dificuldadeStr)) {
            return;
        }

        int dificuldade = Integer.parseInt(dificuldadeStr.trim());

        BibliotecaDTO objBibliotecaDTO = new BibliotecaDTO();
        objBibliotecaDTO.setId(livroSelecionado.getId());
        objBibliotecaDTO.setNome(nome);
        objBibliotecaDTO.setAutor(autor);
        objBibliotecaDTO.setGenero(genero);
        objBibliotecaDTO.setDificuldade(dificuldade);

        BibliotecaDAO objBibliotecaDAO = new BibliotecaDAO();
        objBibliotecaDAO.alterarLivro(objBibliotecaDTO);

        carregarBiblioteca();
        limparCampos();

        mostrarAlerta("Sucesso", null, "Livro atualizado com sucesso!", AlertType.INFORMATION);
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        BibliotecaDTO livroSelecionado = tblLivro.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            mostrarAlerta(
                    "Aviso",
                    "Ação Necessária",
                    "Por favor, selecione um livro na tabela para deletar.", AlertType.WARNING);
            return;
        }

        BibliotecaDAO objBibliotecaDAO = new BibliotecaDAO();
        objBibliotecaDAO.deletarLivro(livroSelecionado);

        carregarBiblioteca();
        limparCampos();

        mostrarAlerta(
                "Sucesso",
                null,
                "Livro excluído com sucesso!", AlertType.INFORMATION);
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
        mostrarAlerta(
                "Campos Limpos",
                null,
                "Os campos de texto foram resetados.", AlertType.INFORMATION);
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colDificuldade.setCellValueFactory(new PropertyValueFactory<>("dificuldade"));

        tblLivro.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            carregarCampos();
        });

        txtNome.setOnAction(event -> txtAutor.requestFocus());
        txtAutor.setOnAction(event -> txtGenero.requestFocus());
        txtGenero.setOnAction(event -> txtDificuldade.requestFocus());
        txtDificuldade.setOnAction(event -> btnSalvarAction(null));

        carregarBiblioteca();
    }

    @FXML
    private void carregarCampos() {
        BibliotecaDTO objBibliotecaDTO = tblLivro.getSelectionModel().getSelectedItem();

        if (objBibliotecaDTO != null) {
            txtNome.setText(objBibliotecaDTO.getNome());
            txtAutor.setText(objBibliotecaDTO.getAutor());
            txtGenero.setText(objBibliotecaDTO.getGenero());
            txtDificuldade.setText(String.valueOf(objBibliotecaDTO.getDificuldade()));
        }
    }
}