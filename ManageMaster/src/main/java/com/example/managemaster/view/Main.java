package com.example.managemaster.view;

import com.example.managemaster.model.Categoria;
import com.example.managemaster.model.Produto;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Categoria categoria = new Categoria(325, "Alimento");

        Produto p1 = new Produto(
                "Arroz", 333, "ManageMaster",
                new BigDecimal("12.10"),
                12,
                LocalDate.of(2026, 9, 6),
                categoria
        );

        Produto p2 = new Produto(
                "Biscoito", 334, "ManageMaster",
                new BigDecimal("5.10"), 15,
                LocalDate.of(2026, 9, 6),
                categoria
        );

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                criarTabProduto("Arroz", p1),
                criarTabProduto("Biscoito", p2)
        );

        Scene scene = new Scene(tabPane, 420, 750);
        scene.getStylesheets().add(
                getClass().getResource("/com/example/managemaster/style.css").toExternalForm()
        );

        stage.setTitle("ManageMaster - Produtos");
        stage.setScene(scene);
        stage.show();
    }

    // -------------------------------------------------------------------
    // CRIA A TELA COMPLETA DO PRODUTO COM TESTE DOS MÉTODOS SET
    // -------------------------------------------------------------------
    private Tab criarTabProduto(String nomeTab, Produto produto) {

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        Label titulo = new Label("📦 Produto: " + produto.getNome());
        titulo.getStyleClass().add("titulo");

        // -------------------------------
        // Labels que serão atualizados
        // -------------------------------
        Label idLabel = new Label("ID: " + produto.getId());
        Label nomeLabel = new Label("Nome: " + produto.getNome());
        Label marcaLabel = new Label("Marca: " + produto.getMarca());
        Label precoLabel = new Label("Preço: R$ " + produto.getPreco());
        Label qtdLabel = new Label("Quantidade: " + produto.getQuantidade());
        Label validadeLabel = new Label("Validade: " + produto.getValidade());

        idLabel.getStyleClass().add("info");
        nomeLabel.getStyleClass().add("info");
        marcaLabel.getStyleClass().add("info");
        precoLabel.getStyleClass().add("info");
        qtdLabel.getStyleClass().add("info");
        validadeLabel.getStyleClass().add("info");

        VBox cardProduto = new VBox(10, idLabel, nomeLabel, marcaLabel, precoLabel, qtdLabel, validadeLabel);
        cardProduto.getStyleClass().add("card");

        // -------------------------------
        // Categoria (com set também!)
        // -------------------------------
        Label catTitulo = new Label("🏷️ Categoria");
        catTitulo.getStyleClass().add("titulo");

        Label catId = new Label("ID Categoria: " + produto.getCategoria().getId());
        Label catNome = new Label("Nome Categoria: " + produto.getCategoria().getNome());
        catId.getStyleClass().add("info");
        catNome.getStyleClass().add("info");

        VBox cardCategoria = new VBox(10, catId, catNome);
        cardCategoria.getStyleClass().add("card");

        // =====================================================================
        // FORMULÁRIO PARA TESTAR MÉTODOS SET
        // =====================================================================

        TextField nomeField = new TextField();
        nomeField.setPromptText("Novo nome");

        TextField marcaField = new TextField();
        marcaField.setPromptText("Nova marca");

        TextField precoField = new TextField();
        precoField.setPromptText("Novo preço");

        TextField qtdField = new TextField();
        qtdField.setPromptText("Nova quantidade");

        TextField validadeField = new TextField();
        validadeField.setPromptText("Nova validade (YYYY-MM-DD)");

        TextField catIdField = new TextField();
        catIdField.setPromptText("Novo ID categoria");

        TextField catNomeField = new TextField();
        catNomeField.setPromptText("Novo nome categoria");

        Button aplicar = new Button("Aplicar alterações");
        aplicar.getStyleClass().add("botao");

        aplicar.setOnAction(e -> {
            try {
                if (!nomeField.getText().isEmpty()) {
                    produto.setNome(nomeField.getText());
                    nomeLabel.setText("Nome: " + produto.getNome());
                }

                if (!marcaField.getText().isEmpty()) {
                    produto.setMarca(marcaField.getText());
                    marcaLabel.setText("Marca: " + produto.getMarca());
                }

                if (!precoField.getText().isEmpty()) {
                    BigDecimal novoPreco = new BigDecimal(precoField.getText());
                    produto.setPreco(novoPreco);
                    precoLabel.setText("Preço: R$ " + produto.getPreco());
                }

                if (!qtdField.getText().isEmpty()) {
                    produto.setQuantidade(Integer.parseInt(qtdField.getText()));
                    qtdLabel.setText("Quantidade: " + produto.getQuantidade());
                }

                if (!validadeField.getText().isEmpty()) {
                    produto.setValidade(LocalDate.parse(validadeField.getText()));
                    validadeLabel.setText("Validade: " + produto.getValidade());
                }

                // Categoria
                if (!catIdField.getText().isEmpty()) {
                    produto.getCategoria().setId(Integer.parseInt(catIdField.getText()));
                    catId.setText("ID Categoria: " + produto.getCategoria().getId());
                }

                if (!catNomeField.getText().isEmpty()) {
                    produto.getCategoria().setNome(catNomeField.getText());
                    catNome.setText("Nome Categoria: " + produto.getCategoria().getNome());
                }

            } catch (Exception ex) {
                System.out.println("Erro ao aplicar alterações: " + ex.getMessage());
            }
        });

        VBox formulario = new VBox(10,
                new Label("🔧 Testar métodos set:"),
                nomeField, marcaField, precoField, qtdField,
                validadeField, catIdField, catNomeField,
                aplicar
        );

        VBox.setMargin(formulario, new Insets(10));

        root.getChildren().addAll(
                titulo,
                cardProduto,
                catTitulo, cardCategoria,
                formulario
        );

        Tab tab = new Tab(nomeTab, root);
        tab.setClosable(false);
        return tab;
    }

    public static void main(String[] args) {
        launch();
    }
}
