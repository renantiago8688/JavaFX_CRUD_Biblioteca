package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent; // CORRIGIDO: Import correto do JavaFX (antes estava java.awt.event.ActionEvent)
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
        try {
            String nome = txtNome.getText();
            String autor = txtAutor.getText();
            String genero = txtGenero.getText();
            int difficulty = Integer.parseInt(txtDificuldade.getText());

            BibliotecaDTO objBibliotecaDTO = new BibliotecaDTO();
            objBibliotecaDTO.setNome(nome);
            objBibliotecaDTO.setAutor(autor);
            objBibliotecaDTO.setGenero(genero);
            objBibliotecaDTO.setDificuldade(difficulty);

            BibliotecaDAO objBibliotecaDAO = new BibliotecaDAO();
            objBibliotecaDAO.salvarLivro(objBibliotecaDTO);

            carregarBiblioteca();
            limparCampos();
        } catch (NumberFormatException e) {
            System.err.println("Erro: A dificuldade deve ser um número inteiro válido.");
        }
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        BibliotecaDTO livroSelecionado = tblLivro.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            System.out.println("Selecione um livro na tabela para alterar.");
            return;
        }

        try {
            String nome = txtNome.getText();
            String autor = txtAutor.getText();
            String genero = txtGenero.getText();
            int dificuldade = Integer.parseInt(txtDificuldade.getText());

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
        } catch (NumberFormatException e) {
            System.err.println("Erro: A dificuldade deve ser um número inteiro válido.");
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        BibliotecaDTO livroSelecionado = tblLivro.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            System.out.println("Selecione um livro na tabela para deletar.");
            return;
        }

        //BibliotecaDTO objBibliotecaDTO = new BibliotecaDTO();
        //objBibliotecaDTO.setId(livroSelecionado.getId());

        BibliotecaDAO objBibliotecaDAO = new BibliotecaDAO();
        objBibliotecaDAO.deletarLivro(livroSelecionado);

        carregarBiblioteca();
        limparCampos();
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos(); // Apenas limpa a tela, sem mexer no banco!
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