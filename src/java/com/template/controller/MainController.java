package com.template.controller;

import static com.template.util.DialogUtil.*;
import static com.template.util.FormUtil.*;

import com.template.model.dto.BibliotecaDTO;
import com.template.service.BibliotecaService;
import com.template.validator.BibliotecaValidador;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class MainController {

    @FXML private Button btnSalvar, btnListar, btnAdicionar, btnDeletar;
    @FXML private TextField txtNome, txtAutor, txtGenero, txtDificuldade;
    @FXML private Label lblContador;

    @FXML private TableView<BibliotecaDTO> tblLivro;
    @FXML private TableColumn<BibliotecaDTO, Integer> colId;
    @FXML private TableColumn<BibliotecaDTO, String> colNome, colAutor, colGenero;
    @FXML private TableColumn<BibliotecaDTO, Integer> colDificuldade;

    private final BibliotecaValidador validador = new BibliotecaValidador();
    private final BibliotecaService service = new BibliotecaService();

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        String[] f = extrairCampos(txtNome, txtAutor, txtGenero, txtDificuldade);

        if (!validador.validarCamposLivro(f[0], f[1], f[2], f[3])) {
            return;
        }

        service.salvar(f[0], f[1], f[2], f[3]);
        posAcaoSucesso("Livro salvo com sucesso!");
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        BibliotecaDTO livroSelecionado = tblLivro.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            mostrarAlerta("Aviso", "Ação Necessária",
                    "Por favor, selecione um livro na tabela para poder alterar.", AlertType.WARNING);
            return;
        }

        String[] f = extrairCampos(txtNome, txtAutor, txtGenero, txtDificuldade);

        if (!validador.validarCamposLivro(f[0], f[1], f[2], f[3])) {
            return;
        }

        service.atualizar(livroSelecionado.getId(), f[0], f[1], f[2], f[3]);
        posAcaoSucesso("Livro atualizado com sucesso!");
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        BibliotecaDTO livroSelecionado = tblLivro.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            mostrarAlerta("Aviso", "Ação Necessária",
                    "Por favor, selecione um livro na tabela para deletar.", AlertType.WARNING);
            return;
        }

        service.deletar(livroSelecionado);
        posAcaoSucesso("Livro excluído com sucesso!");
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos(tblLivro, txtNome, txtAutor, txtGenero, txtDificuldade);
        mostrarAlerta("Campos Limpos", null, "Os campos de texto foram resetados.", AlertType.INFORMATION);
    }

    private void posAcaoSucesso(String mensagem) {
        carregarBiblioteca();
        limparCampos(tblLivro, txtNome, txtAutor, txtGenero, txtDificuldade);
        mostrarAlerta("Sucesso", null, mensagem, AlertType.INFORMATION);
    }

    @FXML
    private void carregarBiblioteca() {
        List<BibliotecaDTO> lista = service.listar();
        tblLivro.setItems(FXCollections.observableArrayList(lista));

        if (lblContador != null) {
            lblContador.setText("Total de livros cadastrados: " + lista.size());
        }
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        BibliotecaDTO livroSelecionado = tblLivro.getSelectionModel().getSelectedItem();
        preencherCampos(livroSelecionado, txtNome, txtAutor, txtGenero, txtDificuldade);
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colDificuldade.setCellValueFactory(new PropertyValueFactory<>("dificuldade"));

        tblLivro.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) ->
                preencherCampos(newV, txtNome, txtAutor, txtGenero, txtDificuldade)
        );

        txtNome.setOnAction(e -> txtAutor.requestFocus());
        txtAutor.setOnAction(e -> txtGenero.requestFocus());
        txtGenero.setOnAction(e -> txtDificuldade.requestFocus());
        txtDificuldade.setOnAction(e -> btnSalvarAction(null));

        carregarBiblioteca();
    }
}