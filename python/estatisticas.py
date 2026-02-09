
from decimal import Decimal
from datetime import datetime, timedelta
from typing import List, Dict, Optional
from collections import defaultdict
from api_client import APIClient


class Estatisticas:
    """Classe para realizar cálculos estatísticos e análises"""
    
    def __init__(self, api_client: APIClient):
        """
        Inicializa a classe de estatísticas
        
        Args:
            api_client: Instância do cliente da API
        """
        self.api = api_client
    
    def calcular_receitas_mensais(self, meses: int = 12) -> Dict[str, Decimal]:
        """
        Calcula as receitas (vendas) por mês
        
        Args:
            meses: Número de meses para analisar (padrão: 12)
            
        Returns:
            Dicionário com chave no formato 'YYYY-MM' e valor como Decimal
        """
        vendas = self.api.listar_vendas()
        receitas = defaultdict(Decimal)
        
        # Calcular data limite
        data_limite = datetime.now() - timedelta(days=meses * 30)
        
        for venda in vendas:
            if 'data' in venda:
                try:
                    # Parse da data (formato ISO do Spring Boot)
                    data_venda_str = venda['data']
                    # Remover 'Z' e ajustar formato se necessário
                    if 'T' in data_venda_str:
                        data_venda = datetime.fromisoformat(data_venda_str.replace('Z', '+00:00'))
                    else:
                        # Tentar formato apenas data
                        data_venda = datetime.fromisoformat(data_venda_str)
                    
                    if data_venda >= data_limite:
                        mes_ano = data_venda.strftime('%Y-%m')
                        
                        # Tentar obter valor total da venda
                        # Nota: O DTO atual não retorna valorTotal, então vamos usar uma estimativa
                        # baseada no total geral dividido pelo número de vendas no período
                        # Para uma solução completa, seria necessário adicionar valorTotal ao DTO
                        venda_detalhes = self.api.buscar_venda(venda['id'])
                        if venda_detalhes:
                            # Tentar diferentes campos possíveis
                            valor = None
                            for campo in ['valorTotal', 'total', 'valor']:
                                if campo in venda_detalhes:
                                    valor = Decimal(str(venda_detalhes[campo]))
                                    break
                            
                            # Se não encontrou valor, usar média estimada
                            if valor is None:
                                # Calcular média baseada no total de vendas
                                total_vendas = self.api.total_vendas()
                                num_vendas_periodo = len([v for v in vendas if 'data' in v])
                                if num_vendas_periodo > 0:
                                    valor = total_vendas / num_vendas_periodo
                                else:
                                    valor = Decimal('0')
                            
                            receitas[mes_ano] += valor
                except (ValueError, KeyError, AttributeError) as e:
                    print(f"Erro ao processar venda {venda.get('id')}: {e}")
                    continue
        
        return dict(receitas)
    
    def calcular_despesas_mensais(self, meses: int = 12) -> Dict[str, Decimal]:
        """
        Calcula as despesas (compras) por mês
        
        Args:
            meses: Número de meses para analisar (padrão: 12)
            
        Returns:
            Dicionário com chave no formato "YYYY-MM" e valor como Decimal
        """
        compras = self.api.listar_compras()
        despesas = defaultdict(Decimal)
        
        # Calcular data limite
        data_limite = datetime.now() - timedelta(days=meses * 30)
        
        for compra in compras:
            if 'data' in compra or 'dataTransacao' in compra:
                try:
                    # Tentar diferentes formatos de data
                    data_str = compra.get('data') or compra.get('dataTransacao')
                    if data_str:
                        data_compra = datetime.fromisoformat(data_str.replace('Z', '+00:00'))
                        
                        if data_compra >= data_limite:
                            mes_ano = data_compra.strftime('%Y-%m')
                            
                            # Usar valorTotal da compra
                            if 'valorTotal' in compra:
                                despesas[mes_ano] += Decimal(str(compra['valorTotal']))
                except (ValueError, KeyError) as e:
                    print(f"Erro ao processar compra {compra.get('id')}: {e}")
                    continue
        
        return dict(despesas)
    
    def calcular_lucro_mensal(self, meses: int = 12) -> Dict[str, Dict[str, Decimal]]:
        """
        Calcula lucro mensal (receitas - despesas)
        
        Args:
            meses: Número de meses para analisar
            
        Returns:
            Dicionário com estrutura: {'YYYY-MM': {'receita': Decimal, 'despesa': Decimal, 'lucro': Decimal}}
        """
        receitas = self.calcular_receitas_mensais(meses)
        despesas = self.calcular_despesas_mensais(meses)
        
        # Unir todos os meses
        todos_meses = set(receitas.keys()) | set(despesas.keys())
        
        resultado = {}
        for mes in sorted(todos_meses):
            receita = receitas.get(mes, Decimal('0'))
            despesa = despesas.get(mes, Decimal('0'))
            lucro = receita - despesa
            
            resultado[mes] = {
                'receita': receita,
                'despesa': despesa,
                'lucro': lucro
            }
        
        return resultado

    def calcular_saldo_acumulado(self, meses: int = 12) -> Dict[str, Decimal]:
        """
        Calcula o saldo acumulado ao longo dos meses (soma cumulativa do lucro)

        Args:
            meses: Numero de meses para analisar

        Returns:
            Dicionario com chave 'YYYY-MM' e valor Decimal de saldo acumulado
        """
        lucro_mensal = self.calcular_lucro_mensal(meses)
        saldo_acumulado = {}
        saldo = Decimal('0')

        for mes in sorted(lucro_mensal.keys()):
            saldo += lucro_mensal[mes]['lucro']
            saldo_acumulado[mes] = saldo

        return saldo_acumulado
    
    def produtos_mais_vendidos(self, limite: int = 10) -> List[Dict]:
        """
        Identifica os produtos mais vendidos
        
        Nota: Como o DTO atual não retorna os itens da venda, este método
        retorna uma lista vazia. Para funcionar completamente, seria necessário
        adicionar os itens ao VendaResponseDTO ou criar um endpoint específico.
        
        Args:
            limite: Número máximo de produtos para retornar
            
        Returns:
            Lista de dicionários com informações do produto e quantidade vendida
        """
        # TODO: Implementar quando o DTO incluir itens ou criar endpoint específico
        # Por enquanto, retornar lista vazia para não quebrar a aplicação
        vendas = self.api.listar_vendas()
        produtos_vendidos = defaultdict(int)
        produtos_info = {}
        
        # Buscar informações dos produtos
        produtos = self.api.listar_produtos()
        for produto in produtos:
            produtos_info[produto['id']] = produto
        
        # Tentar obter itens das vendas
        for venda in vendas:
            venda_detalhes = self.api.buscar_venda(venda['id'])
            if venda_detalhes:
                # Tentar diferentes estruturas possíveis
                itens = venda_detalhes.get('itens') or venda_detalhes.get('items') or []
                for item in itens:
                    produto_id = None
                    quantidade = 0
                    
                    # Tentar diferentes formatos de item
                    if isinstance(item, dict):
                        produto_id = item.get('produtoId') or item.get('produto_id')
                        if not produto_id and 'produto' in item:
                            produto_obj = item['produto']
                            if isinstance(produto_obj, dict):
                                produto_id = produto_obj.get('id')
                        quantidade = item.get('quantidade', 0)
                    
                    if produto_id and quantidade:
                        produtos_vendidos[produto_id] += quantidade
        
        # Se não encontrou nenhum produto vendido, retornar lista vazia
        if not produtos_vendidos:
            return []
        
        # Ordenar por quantidade vendida
        produtos_ordenados = sorted(
            produtos_vendidos.items(),
            key=lambda x: x[1],
            reverse=True
        )[:limite]
        
        # Montar resultado
        resultado = []
        for produto_id, quantidade in produtos_ordenados:
            produto_info = produtos_info.get(produto_id, {})
            preco = produto_info.get('preco', 0)
            # Converter Decimal para float se necessário
            if hasattr(preco, '__float__'):
                preco = float(preco)
            
            resultado.append({
                'id': produto_id,
                'nome': produto_info.get('nome', 'Produto Desconhecido'),
                'marca': produto_info.get('marca', ''),
                'quantidade_vendida': quantidade,
                'preco': preco
            })
        
        return resultado

    def produtos_proximos_vencimento(self, dias: int = 30, limite: int = 10) -> List[Dict]:
        """
        Lista produtos com validade dentro do periodo informado

        Args:
            dias: Quantos dias a frente considerar
            limite: Numero maximo de produtos a retornar

        Returns:
            Lista de produtos com dias para vencer
        """
        produtos = self.api.listar_produtos()
        hoje = datetime.now().date()
        data_limite = hoje + timedelta(days=dias)
        resultado = []

        for produto in produtos:
            validade_str = produto.get('validade')
            if not validade_str:
                continue
            try:
                if 'T' in validade_str:
                    validade = datetime.fromisoformat(validade_str.replace('Z', '+00:00')).date()
                else:
                    validade = datetime.fromisoformat(validade_str).date()
            except ValueError:
                continue

            if hoje <= validade <= data_limite:
                dias_para_vencer = (validade - hoje).days
                resultado.append({
                    'id': produto.get('id'),
                    'nome': produto.get('nome', 'Produto'),
                    'marca': produto.get('marca', ''),
                    'validade': validade.isoformat(),
                    'dias_para_vencer': dias_para_vencer,
                    'quantidade': produto.get('quantidade', 0)
                })

        resultado.sort(key=lambda x: x['dias_para_vencer'])
        return resultado[:limite]

    def produtos_estoque_critico(self, limite: int = 5) -> List[Dict]:
        """
        Lista produtos com estoque abaixo de um limite definido

        Args:
            limite: Limite minimo de estoque

        Returns:
            Lista de produtos criticos
        """
        produtos_criticos = self.api.estoque_baixo()

        if produtos_criticos:
            return [{
                'id': p.get('id'),
                'nome': p.get('nome', 'Produto'),
                'quantidade': p.get('quantidade', 0),
                'limite': limite
            } for p in produtos_criticos]

        produtos = self.api.listar_produtos()
        resultado = []
        for produto in produtos:
            quantidade = produto.get('quantidade', 0)
            if quantidade <= limite:
                resultado.append({
                    'id': produto.get('id'),
                    'nome': produto.get('nome', 'Produto'),
                    'quantidade': quantidade,
                    'limite': limite
                })

        resultado.sort(key=lambda x: x['quantidade'])
        return resultado
    
    def calcular_indicadores_desempenho(self) -> Dict[str, any]:
        """
        Calcula indicadores de desempenho do negócio
        
        Returns:
            Dicionário com vários indicadores
        """
        receitas = self.calcular_receitas_mensais(12)
        despesas = self.calcular_despesas_mensais(12)
        lucro_mensal = self.calcular_lucro_mensal(12)
        
        # Calcular totais
        total_receitas = sum(receitas.values())
        total_despesas = sum(despesas.values())
        total_lucro = total_receitas - total_despesas
        
        # Calcular médias mensais
        meses_com_dados = len([m for m in lucro_mensal.values() if m['receita'] > 0 or m['despesa'] > 0])
        media_receita_mensal = total_receitas / meses_com_dados if meses_com_dados > 0 else Decimal('0')
        media_despesa_mensal = total_despesas / meses_com_dados if meses_com_dados > 0 else Decimal('0')
        media_lucro_mensal = total_lucro / meses_com_dados if meses_com_dados > 0 else Decimal('0')
        
        # Calcular margem de lucro
        margem_lucro = (total_lucro / total_receitas * 100) if total_receitas > 0 else Decimal('0')
        
        # Obter saldo atual
        saldo_atual = self.api.saldo_financeiro()
        
        # Produtos mais vendidos
        top_produtos = self.produtos_mais_vendidos(5)
        
        return {
            'total_receitas': float(total_receitas),
            'total_despesas': float(total_despesas),
            'total_lucro': float(total_lucro),
            'margem_lucro_percentual': float(margem_lucro),
            'saldo_atual': float(saldo_atual),
            'media_receita_mensal': float(media_receita_mensal),
            'media_despesa_mensal': float(media_despesa_mensal),
            'media_lucro_mensal': float(media_lucro_mensal),
            'meses_analisados': meses_com_dados,
            'top_produtos': top_produtos
        }
