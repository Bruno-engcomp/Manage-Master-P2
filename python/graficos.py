# Codigo da criação do banco de dados teste em SQLite3 e plotagem de gráficos, criado em dezembro(será atualizado para PostgreSQL 16)
import sqlite3
import pandas as pd
import matplotlib.pyplot as plt
import streamlit as st
import os

st.set_page_config(page_title="ManageMaster - Estoque", layout="wide")

DB_FILE = 'produtos.db' 
TABLE_NAME = 'estoque_produtos'

def get_connection():
    return sqlite3.connect(DB_FILE)

def criar_tabela():
    try:
        with get_connection() as conn:
            cursor = conn.cursor()
            cursor.execute(f"""
                CREATE TABLE IF NOT EXISTS {TABLE_NAME} (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome_produto TEXT,
                    preco_compra_unidade REAL,
                    preco_venda_unidade REAL,
                    marca TEXT,
                    data_fabricacao TEXT,
                    data_validade TEXT,
                    usuario_responsavel TEXT
                )
            """)
        return True, "✅ Banco de dados pronto!"
    except Exception as e:
        return False, f"❌ Erro: {e}"

def resetar_banco():
    try:
        if os.path.exists(DB_FILE):
            os.remove(DB_FILE)
        return criar_tabela()
    except Exception as e:
        return False, f"❌ Erro ao resetar: {e}"

def inserir_dados_teste():
    try:
        criar_tabela()
        with get_connection() as conn:
            cursor = conn.cursor()
            dados = [
                ("Arroz Branco 5kg", 18.50, 26.90, "Tio João", "2025-01-10", "2026-01-10", "Gerente Carlos"),
                ("Feijão Carioca 1kg", 6.00, 9.50, "Camil", "2025-02-01", "2026-02-01", "Repositora Ana"),
                ("Óleo de Soja 900ml", 4.50, 7.99, "Liza", "2025-01-01", "2026-01-01", "Gerente Carlos"),
                ("Detergente Líquido", 1.80, 2.99, "Ypê", "2025-01-15", "2027-01-01", "Caixa João"),
                ("Bolacha Recheada", 2.00, 3.50, "Bono", "2025-03-01", "2025-09-01", "Repositora Ana"),
                ("Refrigerante 2L", 6.50, 9.90, "Coca-Cola", "2025-01-01", "2025-06-01", "Gerente Carlos"),
            ]
            cursor.executemany(f"""
                INSERT INTO {TABLE_NAME} (
                    nome_produto, preco_compra_unidade, preco_venda_unidade, 
                    marca, data_fabricacao, data_validade, usuario_responsavel
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """, dados)
        return True, "🛒 Produtos de mercado inseridos com sucesso!"
    except Exception as e:
        return False, f"❌ Erro ao inserir: {e}"

def main():
    st.title("🛒 ManageMaster - Gestão de Estoque")
    st.markdown("---")

    col1, col2, col3, col4 = st.columns(4)

    with col1:
        if st.button("🛠️ Criar Tabela", use_container_width=True):
            sucesso, msg = criar_tabela()
            if sucesso: st.success(msg)
            else: st.error(msg)

    with col2:
        if st.button("⚠️ RESETAR ESTOQUE", use_container_width=True):
            sucesso, msg = resetar_banco()
            if sucesso: 
                st.warning(msg)
                st.rerun()
            else: st.error(msg)

    with col3:
        if st.button("📦 Inserir Produtos Teste", use_container_width=True):
            sucesso, msg = inserir_dados_teste()
            if sucesso: 
                st.success(msg)
                st.rerun()
            else: st.error(msg)

    with col4:
        if st.button("🔄 Atualizar Lista", use_container_width=True):
            st.rerun()

    if not os.path.exists(DB_FILE):
        st.info("ℹ️ O sistema está vazio. Clique em 'Criar Tabela' e depois em 'Inserir Produtos Teste'.")
        return

    try:
        with get_connection() as conn:
            df = pd.read_sql_query(f"SELECT * FROM {TABLE_NAME}", conn)

        if df.empty:
            st.warning("⚠️ Estoque vazio. Clique em 'Inserir Produtos Teste' para ver o exemplo.")
            return

        df['Lucro (R$)'] = df['preco_venda_unidade'] - df['preco_compra_unidade']
        df['Margem (%)'] = (df['Lucro (R$)'] / df['preco_venda_unidade']) * 100

        with st.expander("📋 Ver Estoque Completo", expanded=True):
            st.dataframe(df)

        st.markdown("---")
        
        st.subheader("📊 Desempenho de Vendas por usuário")

        usuarios = df['usuario_responsavel'].unique()
        user_select = st.selectbox("Selecione o usuário:", usuarios)

        if user_select:
            df_user = df[df['usuario_responsavel'] == user_select].copy()
            
            fig, ax = plt.subplots(figsize=(10, 4))
            df_user = df_user.sort_values(by='Margem (%)', ascending=False)
            
            barras = ax.bar(df_user['nome_produto'], df_user['Margem (%)'], color='#2ecc71')
            
            for bar in barras:
                height = bar.get_height()
                ax.text(bar.get_x() + bar.get_width()/2, height, f'{height:.1f}%', ha='center', va='bottom')
            
            ax.set_title(f"Margem de Lucro dos Produtos cadastrados por: {user_select}")
            ax.set_ylabel("Margem (%)")
            ax.spines['top'].set_visible(False)
            ax.spines['right'].set_visible(False)
            
            st.pyplot(fig)

    except Exception as e:
        st.error(f"Erro no sistema: {e}")

if __name__ == "__main__":
    main()
