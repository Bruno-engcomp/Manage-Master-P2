"""
Cliente HTTP para comunicação com a API Spring Boot
"""
import requests
from typing import List, Dict, Optional
from decimal import Decimal
import os

class APIClient:
    """Cliente para fazer requisições à API do Spring Boot"""
    
    def __init__(self, base_url: str = None):
        """
        Inicializa o cliente da API
        
        Args:
            base_url: URL base da API (padrão: http://localhost:8080)
        """
        self.base_url = base_url or os.getenv('SPRING_BOOT_URL', 'http://localhost:8080')
        self.session = requests.Session()
        self.session.headers.update({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        })
    
    def _get(self, endpoint: str) -> Optional[Dict]:
        """Faz uma requisição GET"""
        try:
            response = self.session.get(f"{self.base_url}{endpoint}")
            response.raise_for_status()
            return response.json()
        except requests.exceptions.RequestException as e:
            print(f"Erro ao fazer requisição GET para {endpoint}: {e}")
            return None
    
    def _get_list(self, endpoint: str) -> List[Dict]:
        """Faz uma requisição GET que retorna uma lista"""
        try:
            response = self.session.get(f"{self.base_url}{endpoint}")
            response.raise_for_status()
            return response.json() if response.json() else []
        except requests.exceptions.RequestException as e:
            print(f"Erro ao fazer requisição GET para {endpoint}: {e}")
            return []
    
    # PRODUTOS 
    def listar_produtos(self) -> List[Dict]:
        """Lista todos os produtos"""
        return self._get_list("/api/produtos")
    
    def buscar_produto(self, produto_id: int) -> Optional[Dict]:
        """Busca um produto por ID"""
        return self._get(f"/api/produtos/{produto_id}")
    
    # VENDAS 
    def listar_vendas(self) -> List[Dict]:
        """Lista todas as vendas"""
        return self._get_list("/api/vendas")
    
    def buscar_venda(self, venda_id: int) -> Optional[Dict]:
        """Busca uma venda por ID"""
        return self._get(f"/api/vendas/{venda_id}")
    
    #  COMPRAS 
    def listar_compras(self) -> List[Dict]:
        """Lista todas as compras"""
        return self._get_list("/api/compras")
    
    def buscar_compra(self, compra_id: int) -> Optional[Dict]:
        """Busca uma compra por ID"""
        return self._get(f"/api/compras/{compra_id}")
    
    #  RELATÓRIOS 
    def saldo_financeiro(self) -> Decimal:
        """Obtém o saldo financeiro atual"""
        try:
            response = self.session.get(f"{self.base_url}/api/relatorios/financeiro/saldo")
            response.raise_for_status()
            return Decimal(str(response.json()))
        except requests.exceptions.RequestException as e:
            print(f"Erro ao obter saldo financeiro: {e}")
            return Decimal('0')
    
    def total_vendas(self) -> Decimal:
        """Obtém o total de vendas"""
        try:
            response = self.session.get(f"{self.base_url}/api/relatorios/vendas/total")
            response.raise_for_status()
            return Decimal(str(response.json()))
        except requests.exceptions.RequestException as e:
            print(f"Erro ao obter total de vendas: {e}")
            return Decimal('0')
    
    def estoque_baixo(self) -> List[Dict]:
        """Lista produtos com estoque baixo"""
        return self._get_list("/api/relatorios/estoque/baixo")
    
    #  CATEGORIAS 
    def listar_categorias(self) -> List[Dict]:
        """Lista todas as categorias"""
        return self._get_list("/api/categorias")
    
    #  FORNECEDORES
    def listar_fornecedores(self) -> List[Dict]:
        """Lista todos os fornecedores"""
        return self._get_list("/api/fornecedores")
