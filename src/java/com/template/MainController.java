package com.template;

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

    private void mostrarAlerta(String titulo, String cabecalho, String mensagem, AlertType tipo) {
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        // 1. Barreira de Segurança: Verifica campos vazios
        if (txtNome.getText().trim().isEmpty() ||
                txtAutor.getText().trim().isEmpty() ||
                txtGenero.getText().trim().isEmpty() ||
                txtDificuldade.getText().trim().isEmpty()) {

            mostrarAlerta("Campos Incompletos", "Não foi possível salvar",
                    "Todos os campos (Nome, Autor, Gênero e Dificuldade) devem ser preenchidos.",
                    AlertType.WARNING);
            return;
        }

        int difficulty;
        try {
            difficulty = Integer.parseInt(txtDificuldade.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Digitação", "Valor Inválido",
                    "A dificuldade deve ser um número inteiro válido.", AlertType.ERROR);
            return;
        }

        BibliotecaDTO objBibliotecaDTO = new BibliotecaDTO();
        objBibliotecaDTO.setNome(txtNome.getText());
        objBibliotecaDTO.setAutor(txtAutor.getText());
        objBibliotecaDTO.setGenero(txtGenero.getText());
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

        if (txtNome.getText().trim().isEmpty() ||
                txtAutor.getText().trim().isEmpty() ||
                txtGenero.getText().trim().isEmpty() ||
                txtDificuldade.getText().trim().isEmpty()) {

            mostrarAlerta("Campos Incompletos", "Não foi possível alterar",
                    "Não deixe os campos em branco para realizar a alteração.",
                    AlertType.WARNING);
            return;
        }

        try {
            String nome = txtNome.getText();
            String autor = txtAutor.getText();
            String genero = txtGenero.getText();
            int dificuldade = Integer.parseInt(txtDificuldade.getText().trim());

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

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Digitação", "Valor Inválido",
                    "A dificuldade deve ser um número inteiro válido.", AlertType.ERROR);
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        BibliotecaDTO livroSelecionado = tblLivro.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            mostrarAlerta("Aviso", "Ação Necessária",
                    "Por favor, selecione um livro na tabela para deletar.", AlertType.WARNING);
            return;
        }

        BibliotecaDAO objBibliotecaDAO = new BibliotecaDAO();
        objBibliotecaDAO.deletarLivro(livroSelecionado);

        carregarBiblioteca();
        limparCampos();

        mostrarAlerta("Sucesso", null, "Livro excluído com sucesso!", AlertType.INFORMATION);
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
        mostrarAlerta("Campos Limpos", null, "Os campos de texto foram resetados.", AlertType.INFORMATION);
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