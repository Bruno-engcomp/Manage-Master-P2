package com.example.managemaster;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    private produto produtoTeste;

    @Override
    public void start(Stage stage) {

        produtoTeste = new produto("Arroz", "ManageMaster", 15.90,
                12, 5, 2026
        );

        Label titulo = new Label("📦 Produto");
        titulo.getStyleClass().add("titulo");

        Label nomeLabel = new Label("Nome: " + produtoTeste.getNome());
        nomeLabel.getStyleClass().add("info");

        Label marcaLabel = new Label("Marca: " + produtoTeste.getMarca());
        marcaLabel.getStyleClass().add("info");

        Label precoLabel = new Label("Preço: R$ " + produtoTeste.getPreco());
        precoLabel.getStyleClass().add("info");

        Label validadeLabel = new Label("Validade: " +
                produtoTeste.getValidadedd() + "/" +
                produtoTeste.getValidademm() + "/" +
                produtoTeste.getValidadeyy()
        );
        validadeLabel.getStyleClass().add("info");

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

        VBox card = new VBox(10, nomeLabel, marcaLabel, precoLabel, validadeLabel);
        card.getStyleClass().add("card");

        VBox root = new VBox(20, titulo, card, novoPrecoField, atualizarPrecoBtn);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 350, 350);

        // aplica o CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("ManageMaster - Teste Produto");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
