import sqlite3
import pandas as pd
import matplotlib.pyplot as plt
import datetime

DB_FILE = 'e:/PROJETOS DE ENGENHARIA 2/produtos.db' 
TABLE_NAME = 'estoque_produtos'

# --- FUNÇÃO DE ESCRITA ---

def inserir_produto(nome, compra, venda, marca, fabricacao, validade, usuario):
    """Insere um novo produto no banco de dados, incluindo o responsável."""
    try:
        with sqlite3.connect(DB_FILE) as conn:
            cursor = conn.cursor()
            
            sql_insert = """
                INSERT INTO estoque_produtos (
                    nome_produto, preco_compra_unidade, preco_venda_unidade, 
                    marca, data_fabricacao, data_validade, usuario_responsavel
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """
            
            valores = (nome, compra, venda, marca, fabricacao, validade, usuario)
            cursor.execute(sql_insert, valores)
            
            print(f"✨ Produto '{nome}' inserido por {usuario} com sucesso!")

    except sqlite3.Error as e:
        print(f"❌ Erro ao inserir '{nome}': {e}")


# --- FUNÇÃO DE LEITURA E ANÁLISE (FINAL) ---

def analisar_e_visualizar_produtos():
    """Lê os dados e gera um gráfico de margem de lucro individual para cada usuário, com melhorias visuais."""
    try:
        # 1. ESTILO VISUAL
        plt.style.use('ggplot') 

        with sqlite3.connect(DB_FILE) as conn:
            query = f"SELECT nome_produto, preco_compra_unidade, preco_venda_unidade, usuario_responsavel FROM {TABLE_NAME}"
            df = pd.read_sql_query(query, conn)
            
            if df.empty:
                print("⚠️ Banco de dados vazio. Não há dados para análise.")
                return

            # Cálculo da Margem de Lucro
            df['lucro_bruto_unidade'] = df['preco_venda_unidade'] - df['preco_compra_unidade']
            df['margem_lucro_percentual'] = (df['lucro_bruto_unidade'] / df['preco_venda_unidade']) * 100
            
            # Impressão do Resumo Geral
            print("\n--- RESUMO DE LUCRO MÉDIO POR PRODUTO E POR RESPONSÁVEL ---")
            resumo_por_usuario = df.groupby(['usuario_responsavel', 'nome_produto'])['margem_lucro_percentual'].mean().round(2).reset_index()
            print(resumo_por_usuario.to_string(index=False))
            
            usuarios_unicos = df['usuario_responsavel'].unique()
            print(f"\nTotal de usuários únicos encontrados: {len(usuarios_unicos)}")

            # Loop para gerar um gráfico por usuário
            for usuario in usuarios_unicos:
                
                df_usuario = df[df['usuario_responsavel'] == usuario].copy()
                resumo_grafico = df_usuario.groupby('nome_produto')['margem_lucro_percentual'].mean().reset_index()
                resumo_grafico = resumo_grafico.sort_values(
                    by='margem_lucro_percentual', ascending=False
                )
                resumo_grafico['margem_lucro_percentual'] = resumo_grafico['margem_lucro_percentual'].round(2)
                
                # Plotagem e Customização Visual
                fig, ax = plt.subplots(figsize=(10, 6)) 
                
                barras = ax.bar(resumo_grafico['nome_produto'], 
                                resumo_grafico['margem_lucro_percentual'], 
                                color='#2ecc71', # Cor suave
                                width=0.7) 
                
                # Rótulos de Valor (no topo das barras)
                for bar in barras:
                    yval = bar.get_height()
                    ax.text(bar.get_x() + bar.get_width()/2, yval + 1, 
                            f'{yval:.2f}%', 
                            ha='center', va='bottom', fontsize=9)
                
                # Títulos e Eixos
                ax.set_ylabel('Margem de Lucro (%)', fontsize=12)
                ax.set_title(f'Margem de Lucro Média por Produto (Usuário: {usuario})', fontsize=14, weight='bold')
                
                # Estética
                plt.xticks(rotation=45, ha='right', fontsize=10)
                ax.spines['right'].set_visible(False)
                ax.spines['top'].set_visible(False)
                
                plt.tight_layout()
                
                # CORREÇÃO CRÍTICA DE RENDERIZAÇÃO: Força a exibição da janela
                plt.draw() 
                plt.show(block=True) 

    except sqlite3.Error as e:
        print(f"❌ Erro ao consultar o banco de dados: {e}")

# --- EXECUÇÃO DE TESTE/OPERAÇÃO ---
if __name__ == "__main__":
    
    # Lista de produtos a serem inseridos
    produtos_para_inserir = [
        {"nome": "Vitamina C Efervescente", "compra": 5.00, "venda": 15.00, "marca": "VitaPlus", "fabricacao": "2025-01-01", "validade": "2026-12-31", "usuario": "Usuario_A"},
        {"nome": "Chocolate Amargo 70%", "compra": 4.00, "venda": 7.00, "marca": "Cacau Puro", "fabricacao": "2025-10-20", "validade": "2026-06-30", "usuario": "Usuario_B"},
        {"nome": "Água Mineral 500ml", "compra": 1.00, "venda": 1.50, "marca": "Cristalina", "fabricacao": "2025-11-20", "validade": "2026-11-20", "usuario": "Usuario_A"},
        {"nome": "Barra de Cereal", "compra": 2.50, "venda": 4.50, "marca": "FitFast", "fabricacao": "2025-11-01", "validade": "2026-05-01", "usuario": "Usuario_B"}, 
        {"nome": "Barra de Cereal", "compra": 2.00, "venda": 4.00, "marca": "FitFast", "fabricacao": "2025-11-01", "validade": "2026-05-01", "usuario": "Usuario_A"},
        {"nome": "Café Solúvel", "compra": 10.00, "venda": 18.00, "marca": "Prime", "fabricacao": "2025-09-01", "validade": "2027-09-01", "usuario": "Usuario_C"},
        {"nome": "Biscoito Salgado", "compra": 3.00, "venda": 5.00, "marca": "Salgadinho", "fabricacao": "2025-09-01", "validade": "2027-09-01", "usuario": "Usuario_C"},
    ]
    
    # Loop para inserir todos os produtos
    for produto in produtos_para_inserir:
        inserir_produto(
            nome=produto["nome"],
            compra=produto["compra"],
            venda=produto["venda"],
            marca=produto["marca"],
            fabricacao=produto["fabricacao"],
            validade=produto["validade"],
            usuario=produto["usuario"]
        )
    
    # Executa a análise de dados
    analisar_e_visualizar_produtos()