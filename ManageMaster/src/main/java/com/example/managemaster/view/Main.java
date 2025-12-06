package com.example.managemaster.view;

import com.example.managemaster.model.Categoria;
import com.example.managemaster.model.Produto;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    private Produto produtoTeste;

    @Override
    public void start(Stage stage) {

        // Criando o produto de teste
        produtoTeste = new Produto(
                "Arroz",
                333,
                "ManageMaster",
                15.90,
                12,
                LocalDate.of(2026, 9, 6),
                new Categoria(325, "alimento"));

        // -----------------------------------------------
        // LABELS DE TESTE DOS ATRIBUTOS
        // -----------------------------------------------

        Label titulo = new Label("📦 Produto (Teste)");
        titulo.getStyleClass().add("titulo");

        Label idLabel = new Label("ID: " + produtoTeste.getId());
        idLabel.getStyleClass().add("info");

        Label nomeLabel = new Label("Nome: " + produtoTeste.getNome());
        nomeLabel.getStyleClass().add("info");

        Label marcaLabel = new Label("Marca: " + produtoTeste.getMarca());
        marcaLabel.getStyleClass().add("info");

        Label precoLabel = new Label("Preço: R$ " + produtoTeste.getPreco());
        precoLabel.getStyleClass().add("info");

        Label quantidadeLabel = new Label("Quantidade: " + produtoTeste.getQuantidade());
        quantidadeLabel.getStyleClass().add("info");

        Label validadeLabel = new Label("Validade: " + produtoTeste.getValidade());
        validadeLabel.getStyleClass().add("info");

        // -----------------------------------------------
        // TESTE DE ATUALIZAÇÃO DO PREÇO
        // -----------------------------------------------

        TextField novoPrecoField = new TextField();
        novoPrecoField.setPromptText("Novo preço");
        novoPrecoField.getStyleClass().add("campo");

        Button atualizarPrecoBtn = new Button("Atualizar preço");
        atualizarPrecoBtn.getStyleClass().add("botao");

        atualizarPrecoBtn.setOnAction(e -> {
            try {
                double novoPreco = Double.parseDouble(novoPrecoField.getText());
                produtoTeste.setPreco(novoPreco);
                precoLabel.setText("Preço: R$ " + produtoTeste.getPreco());
            } catch (NumberFormatException ex) {
                precoLabel.setText("Preço inválido!");
            }
        });

        // Card visual com informações
        VBox card = new VBox(10,
                idLabel, nomeLabel, marcaLabel,
                precoLabel, quantidadeLabel, validadeLabel
        );
        card.getStyleClass().add("card");

        VBox root = new VBox(20, titulo, card, novoPrecoField, atualizarPrecoBtn);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 350, 400);

        // CSS
        scene.getStylesheets().add(
                getClass().getResource("/com/example/managemaster/style.css").toExternalForm()
        );

        stage.setTitle("ManageMaster - Teste Produto");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
