package com.example.managemaster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private produto produtoTeste;

    @Override
    public void start(Stage stage) {

        // Criando um produto para testar
        produtoTeste = new produto("Arroz", "ManageMaster", 15.90,
                12, 5, 2026
        );

        Label nomeLabel = new Label("Nome: " + produtoTeste.getNome());
        Label marcaLabel = new Label("Marca: " + produtoTeste.getMarca());
        Label precoLabel = new Label("Preço: R$ " + produtoTeste.getPreco());
        Label validadeLabel = new Label("Validade: " +
                produtoTeste.getValidadedd() + "/" +
                produtoTeste.getValidademm() + "/" +
                produtoTeste.getValidadeyy()
        );

        TextField novoPrecoField = new TextField();
        novoPrecoField.setPromptText("Novo preço");

        Button atualizarPrecoBtn = new Button("Atualizar preço");

        atualizarPrecoBtn.setOnAction(e -> {
            try {
                double novoPreco = Double.parseDouble(novoPrecoField.getText());
                produtoTeste.setPreco(novoPreco);
                precoLabel.setText("Preço: R$ " + produtoTeste.getPreco());
            } catch (NumberFormatException ex) {
                precoLabel.setText("Preço inválido!");
            }
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(
                nomeLabel,
                marcaLabel,
                precoLabel,
                validadeLabel,
                novoPrecoField,
                atualizarPrecoBtn
        );

        Scene scene = new Scene(root, 320, 250);

        stage.setTitle("Teste da classe Produto ManageMaster");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
