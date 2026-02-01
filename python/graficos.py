
import plotly.graph_objects as go
import plotly.express as px
from plotly.subplots import make_subplots
from typing import Dict, List
from decimal import Decimal
from estatisticas import Estatisticas


class Graficos:
    """Classe para gerar gráficos interativos com Plotly"""
    
    def __init__(self, estatisticas: Estatisticas):
        """
        Inicializa a classe de gráficos
        
        Args:
            estatisticas: Instância da classe Estatisticas
        """
        self.estatisticas = estatisticas
    
    def grafico_receitas_despesas(self, meses: int = 12) -> go.Figure:
        """
        Cria gráfico de barras comparando receitas e despesas mensais
        
        Args:
            meses: Número de meses para analisar
            
        Returns:
            Figura do Plotly
        """
        lucro_mensal = self.estatisticas.calcular_lucro_mensal(meses)
        
        meses_lista = sorted(lucro_mensal.keys())
        receitas = [float(lucro_mensal[m]['receita']) for m in meses_lista]
        despesas = [float(lucro_mensal[m]['despesa']) for m in meses_lista]
        
        fig = go.Figure()
        
        fig.add_trace(go.Bar(
            x=meses_lista,
            y=receitas,
            name='Receitas',
            marker_color='#2ecc71',
            text=[f'R$ {r:,.2f}'.replace(',', 'X').replace('.', ',').replace('X', '.') for r in receitas],
            textposition='outside'
        ))
        
        fig.add_trace(go.Bar(
            x=meses_lista,
            y=despesas,
            name='Despesas',
            marker_color='#e74c3c',
            text=[f'R$ {d:,.2f}'.replace(',', 'X').replace('.', ',').replace('X', '.') for d in despesas],
            textposition='outside'
        ))
        
        fig.update_layout(
            title='Receitas vs Despesas Mensais',
            xaxis_title='Mês',
            yaxis_title='Valor (R$)',
            barmode='group',
            template='plotly_white',
            hovermode='x unified',
            height=500
        )
        
        return fig
    
    def grafico_lucro_mensal(self, meses: int = 12) -> go.Figure:
        """
        Cria gráfico de linha mostrando o lucro mensal
        
        Args:
            meses: Número de meses para analisar
            
        Returns:
            Figura do Plotly
        """
        lucro_mensal = self.estatisticas.calcular_lucro_mensal(meses)
        
        meses_lista = sorted(lucro_mensal.keys())
        lucros = [float(lucro_mensal[m]['lucro']) for m in meses_lista]
        
        # Definir cor baseada no valor (verde para positivo, vermelho para negativo)
        cores = ['#2ecc71' if l >= 0 else '#e74c3c' for l in lucros]
        
        fig = go.Figure()
        
        fig.add_trace(go.Scatter(
            x=meses_lista,
            y=lucros,
            mode='lines+markers',
            name='Lucro Mensal',
            line=dict(color='#3498db', width=3),
            marker=dict(size=10, color=cores),
            fill='tozeroy',
            fillcolor='rgba(52, 152, 219, 0.2)',
            text=[f'R$ {l:,.2f}'.replace(',', 'X').replace('.', ',').replace('X', '.') for l in lucros],
            textposition='top center'
        ))
        
        # Adicionar linha de referência em zero
        fig.add_hline(
            y=0,
            line_dash="dash",
            line_color="gray",
            annotation_text="Break-even"
        )
        
        fig.update_layout(
            title='Lucro Mensal',
            xaxis_title='Mês',
            yaxis_title='Lucro (R$)',
            template='plotly_white',
            hovermode='x unified',
            height=500
        )
        
        return fig
    
    def grafico_produtos_mais_vendidos(self, limite: int = 10) -> go.Figure:
        """
        Cria gráfico de barras horizontais com os produtos mais vendidos
        
        Args:
            limite: Número máximo de produtos para exibir
            
        Returns:
            Figura do Plotly
        """
        produtos = self.estatisticas.produtos_mais_vendidos(limite)
        
        if not produtos:
            # Criar gráfico vazio com mensagem
            fig = go.Figure()
            fig.add_annotation(
                text="Nenhum dado de venda disponível",
                xref="paper", yref="paper",
                x=0.5, y=0.5,
                showarrow=False,
                font=dict(size=16)
            )
            fig.update_layout(
                title='Produtos Mais Vendidos',
                height=400
            )
            return fig
        
        nomes = [p['nome'] for p in produtos]
        quantidades = [p['quantidade_vendida'] for p in produtos]
        
        fig = go.Figure()
        
        fig.add_trace(go.Bar(
            y=nomes,
            x=quantidades,
            orientation='h',
            marker_color='#3498db',
            text=[f'{q} unidades' for q in quantidades],
            textposition='outside'
        ))
        
        fig.update_layout(
            title='Produtos Mais Vendidos',
            xaxis_title='Quantidade Vendida',
            yaxis_title='Produto',
            template='plotly_white',
            height=max(400, len(produtos) * 50),
            yaxis={'categoryorder': 'total ascending'}
        )
        
        return fig
    
    def grafico_indicadores_desempenho(self) -> go.Figure:
        """
        Cria gráfico de indicadores principais (gauge/donut)
        
        Returns:
            Figura do Plotly
        """
        indicadores = self.estatisticas.calcular_indicadores_desempenho()
        
        # Criar subplots
        fig = make_subplots(
            rows=2, cols=2,
            specs=[[{"type": "indicator"}, {"type": "indicator"}],
                   [{"type": "indicator"}, {"type": "indicator"}]],
            subplot_titles=('Margem de Lucro', 'Saldo Atual', 'Média Receita Mensal', 'Média Lucro Mensal')
        )
        
        # Margem de lucro 
        fig.add_trace(
            go.Indicator(
                mode="gauge+number+delta",
                value=indicadores['margem_lucro_percentual'],
                domain={'x': [0, 1], 'y': [0, 1]},
                title={'text': "Margem (%)"},
                delta={'reference': 20},
                gauge={
                    'axis': {'range': [None, 100]},
                    'bar': {'color': "darkblue"},
                    'steps': [
                        {'range': [0, 20], 'color': "lightgray"},
                        {'range': [20, 50], 'color': "gray"}
                    ],
                    'threshold': {
                        'line': {'color': "red", 'width': 4},
                        'thickness': 0.75,
                        'value': 90
                    }
                }
            ),
            row=1, col=1
        )
        
        # Saldo atual
        fig.add_trace(
            go.Indicator(
                mode="number",
                value=indicadores['saldo_atual'],
                number={'prefix': "R$ ", 'valueformat': ',.2f'},
                title={'text': "Saldo Atual"}
            ),
            row=1, col=2
        )
        
        # Média receita mensal
        fig.add_trace(
            go.Indicator(
                mode="number",
                value=indicadores['media_receita_mensal'],
                number={'prefix': "R$ ", 'valueformat': ',.2f'},
                title={'text': "Média Receita/Mês"}
            ),
            row=2, col=1
        )
        
        # Média lucro mensal
        fig.add_trace(
            go.Indicator(
                mode="number+delta",
                value=indicadores['media_lucro_mensal'],
                number={'prefix': "R$ ", 'valueformat': ',.2f'},
                title={'text': "Média Lucro/Mês"},
                delta={'reference': 0}
            ),
            row=2, col=2
        )
        
        fig.update_layout(
            title='Indicadores de Desempenho',
            template='plotly_white',
            height=600
        )
        
        return fig
    
    def grafico_evolucao_financeira(self, meses: int = 12) -> go.Figure:
        """
        Cria gráfico combinado mostrando receitas, despesas e lucro ao longo do tempo
        
        Args:
            meses: Número de meses para analisar
            
        Returns:
            Figura do Plotly
        """
        lucro_mensal = self.estatisticas.calcular_lucro_mensal(meses)
        
        meses_lista = sorted(lucro_mensal.keys())
        receitas = [float(lucro_mensal[m]['receita']) for m in meses_lista]
        despesas = [float(lucro_mensal[m]['despesa']) for m in meses_lista]
        lucros = [float(lucro_mensal[m]['lucro']) for m in meses_lista]
        
        fig = make_subplots(
            rows=2, cols=1,
            shared_xaxes=True,
            vertical_spacing=0.1,
            subplot_titles=('Receitas e Despesas', 'Lucro Mensal'),
            row_heights=[0.7, 0.3]
        )
        
        # Gráfico de barras para receitas e despesas
        fig.add_trace(
            go.Bar(x=meses_lista, y=receitas, name='Receitas', marker_color='#2ecc71'),
            row=1, col=1
        )
        
        fig.add_trace(
            go.Bar(x=meses_lista, y=despesas, name='Despesas', marker_color='#e74c3c'),
            row=1, col=1
        )
        
        # Gráfico de linha para lucro
        fig.add_trace(
            go.Scatter(
                x=meses_lista,
                y=lucros,
                mode='lines+markers',
                name='Lucro',
                line=dict(color='#3498db', width=3),
                marker=dict(size=8)
            ),
            row=2, col=1
        )
        
        # Adicionar linha zero no gráfico de lucro
        fig.add_hline(y=0, line_dash="dash", line_color="gray", row=2, col=1)
        
        fig.update_xaxes(title_text="Mês", row=2, col=1)
        fig.update_yaxes(title_text="Valor (R$)", row=1, col=1)
        fig.update_yaxes(title_text="Lucro (R$)", row=2, col=1)
        
        fig.update_layout(
            title='Evolução Financeira',
            template='plotly_white',
            height=700,
            barmode='group',
            hovermode='x unified'
        )
        
        return fig
