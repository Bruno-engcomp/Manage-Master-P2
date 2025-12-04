import sqlite3
import os

# Definindo o caminho absoluto para o banco de dados
DB_FILE = 'e:/PROJETOS DE ENGENHARIA 2/produtos.db' 

def criar_banco_e_tabela_produtos():
    """Cria ou conecta-se ao banco de dados e define a tabela 'estoque_produtos'."""
    try:
        with sqlite3.connect(DB_FILE) as conn:
            cursor = conn.cursor()
            
            # Comando SQL com a coluna usuario_responsavel
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS estoque_produtos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome_produto TEXT NOT NULL,
                    preco_compra_unidade REAL NOT NULL,
                    preco_venda_unidade REAL NOT NULL,
                    marca TEXT,
                    data_fabricacao TEXT,
                    data_validade TEXT,
                    usuario_responsavel TEXT 
                );
            """)
            conn.commit() 
            print(f"✅ Tabela 'estoque_produtos' criada/verificada com coluna 'usuario_responsavel'.")

    except sqlite3.Error as e:
        print(f"❌ Erro ao criar a tabela: {e}")

if __name__ == "__main__":
    criar_banco_e_tabela_produtos()