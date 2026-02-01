
from reportlab.lib.pagesizes import A4
from reportlab.lib import colors
from reportlab.lib.units import inch
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, PageBreak, Image
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.enums import TA_CENTER, TA_LEFT, TA_RIGHT
from io import BytesIO
import plotly.graph_objects as go
from typing import Optional
from datetime import datetime
from estatisticas import Estatisticas
from graficos import Graficos


class RelatorioPDF:
    """Classe para gerar relatórios em PDF"""
    
    def __init__(self, estatisticas: Estatisticas, graficos: Graficos):
        """
        Inicializa a classe de relatórios
        
        Args:
            estatisticas: Instância da classe Estatisticas
            graficos: Instância da classe Graficos
        """
        self.estatisticas = estatisticas
        self.graficos = graficos
        self.styles = getSampleStyleSheet()
        self._criar_estilos_customizados()
    
    def _criar_estilos_customizados(self):
        """Cria estilos customizados para o PDF"""
        self.styles.add(ParagraphStyle(
            name='Titulo',
            parent=self.styles['Heading1'],
            fontSize=24,
            textColor=colors.HexColor('#2c3e50'),
            spaceAfter=30,
            alignment=TA_CENTER
        ))
        
        self.styles.add(ParagraphStyle(
            name='Subtitulo',
            parent=self.styles['Heading2'],
            fontSize=16,
            textColor=colors.HexColor('#34495e'),
            spaceAfter=12,
            spaceBefore=12
        ))
        
        self.styles.add(ParagraphStyle(
            name='Texto',
            parent=self.styles['Normal'],
            fontSize=10,
            textColor=colors.HexColor('#555555'),
            spaceAfter=6
        ))
    
    def _figura_para_imagem(self, fig: go.Figure, width: float = 7 * inch, height: float = 4 * inch) -> Image:
        """
        Converte uma figura do Plotly em uma imagem para o PDF
        
        Args:
            fig: Figura do Plotly
            width: Largura da imagem em pontos
            height: Altura da imagem em pontos
            
        Returns:
            Objeto Image do ReportLab
        """
        # Converter figura para imagem PNG
        img_bytes = fig.to_image(format="png", width=int(width), height=int(height))
        img_buffer = BytesIO(img_bytes)
        
        return Image(img_buffer, width=width, height=height)
    
    def gerar_relatorio_completo(self, output_path: str = None) -> BytesIO:
        """
        Gera um relatório completo em PDF com gráficos e indicadores
        
        Args:
            output_path: Caminho para salvar o arquivo (opcional)
            
        Returns:
            BytesIO com o conteúdo do PDF
        """
        buffer = BytesIO()
        doc = SimpleDocTemplate(buffer, pagesize=A4, rightMargin=72, leftMargin=72, topMargin=72, bottomMargin=18)
        
        # Container para elementos do PDF
        elements = []
        
        # Título
        elements.append(Paragraph("Relatório de Desempenho", self.styles['Titulo']))
        elements.append(Spacer(1, 0.2 * inch))
        
        # Data de geração
        data_geracao = datetime.now().strftime("%d/%m/%Y %H:%M:%S")
        elements.append(Paragraph(f"Gerado em: {data_geracao}", self.styles['Texto']))
        elements.append(Spacer(1, 0.3 * inch))
        
        # Indicadores de Desempenho
        elements.append(Paragraph("Indicadores de Desempenho", self.styles['Subtitulo']))
        
        indicadores = self.estatisticas.calcular_indicadores_desempenho()
        
        # Tabela de indicadores
        dados_indicadores = [
            ['Indicador', 'Valor'],
            ['Total de Receitas', f"R$ {indicadores['total_receitas']:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')],
            ['Total de Despesas', f"R$ {indicadores['total_despesas']:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')],
            ['Total de Lucro', f"R$ {indicadores['total_lucro']:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')],
            ['Margem de Lucro', f"{indicadores['margem_lucro_percentual']:.2f}%"],
            ['Saldo Atual', f"R$ {indicadores['saldo_atual']:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')],
            ['Média Receita Mensal', f"R$ {indicadores['media_receita_mensal']:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')],
            ['Média Lucro Mensal', f"R$ {indicadores['media_lucro_mensal']:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')],
        ]
        
        tabela_indicadores = Table(dados_indicadores, colWidths=[4 * inch, 2 * inch])
        tabela_indicadores.setStyle(TableStyle([
            ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#34495e')),
            ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
            ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
            ('FONTNAME', (0, 0), (-1, 0), 'Helvetica-Bold'),
            ('FONTSIZE', (0, 0), (-1, 0), 12),
            ('BOTTOMPADDING', (0, 0), (-1, 0), 12),
            ('BACKGROUND', (0, 1), (-1, -1), colors.beige),
            ('GRID', (0, 0), (-1, -1), 1, colors.black),
            ('FONTSIZE', (0, 1), (-1, -1), 10),
            ('ROWBACKGROUNDS', (0, 1), (-1, -1), [colors.white, colors.HexColor('#f8f9fa')])
        ]))
        
        elements.append(tabela_indicadores)
        elements.append(Spacer(1, 0.3 * inch))
        
        # Gráfico de Indicadores
        fig_indicadores = self.graficos.grafico_indicadores_desempenho()
        elements.append(self._figura_para_imagem(fig_indicadores, width=7 * inch, height=5 * inch))
        elements.append(PageBreak())
        
        # Gráfico de Lucro Mensal
        elements.append(Paragraph("Lucro Mensal", self.styles['Subtitulo']))
        fig_lucro = self.graficos.grafico_lucro_mensal(12)
        elements.append(self._figura_para_imagem(fig_lucro))
        elements.append(Spacer(1, 0.2 * inch))
        
        # Gráfico de Receitas vs Despesas
        elements.append(Paragraph("Receitas vs Despesas", self.styles['Subtitulo']))
        fig_receitas_despesas = self.graficos.grafico_receitas_despesas(12)
        elements.append(self._figura_para_imagem(fig_receitas_despesas))
        elements.append(PageBreak())
        
        # Produtos Mais Vendidos
        elements.append(Paragraph("Produtos Mais Vendidos", self.styles['Subtitulo']))
        
        produtos = self.estatisticas.produtos_mais_vendidos(10)
        
        if produtos:
            dados_produtos = [['Posição', 'Produto', 'Quantidade Vendida', 'Preço Unitário']]
            for idx, produto in enumerate(produtos, 1):
                dados_produtos.append([
                    str(idx),
                    produto['nome'],
                    str(produto['quantidade_vendida']),
                    f"R$ {produto['preco']:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')
                ])
            
            tabela_produtos = Table(dados_produtos, colWidths=[0.5 * inch, 3 * inch, 1.5 * inch, 1.5 * inch])
            tabela_produtos.setStyle(TableStyle([
                ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#34495e')),
                ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
                ('ALIGN', (0, 0), (-1, -1), 'CENTER'),
                ('FONTNAME', (0, 0), (-1, 0), 'Helvetica-Bold'),
                ('FONTSIZE', (0, 0), (-1, 0), 11),
                ('BOTTOMPADDING', (0, 0), (-1, 0), 12),
                ('BACKGROUND', (0, 1), (-1, -1), colors.beige),
                ('GRID', (0, 0), (-1, -1), 1, colors.black),
                ('FONTSIZE', (0, 1), (-1, -1), 9),
                ('ALIGN', (1, 1), (1, -1), 'LEFT'),
                ('ROWBACKGROUNDS', (0, 1), (-1, -1), [colors.white, colors.HexColor('#f8f9fa')])
            ]))
            
            elements.append(tabela_produtos)
            elements.append(Spacer(1, 0.3 * inch))
            
            # Gráfico de produtos mais vendidos
            fig_produtos = self.graficos.grafico_produtos_mais_vendidos(10)
            elements.append(self._figura_para_imagem(fig_produtos, width=7 * inch, height=5 * inch))
        else:
            elements.append(Paragraph("Nenhum dado de venda disponível.", self.styles['Texto']))
        
        # Rodapé
        elements.append(Spacer(1, 0.5 * inch))
        elements.append(Paragraph("Relatório gerado automaticamente pelo sistema ManageMaster", 
                                  self.styles['Texto']))
        
        
        doc.build(elements)
        
       
        buffer.seek(0)
        
       
        if output_path:
            with open(output_path, 'wb') as f:
                f.write(buffer.read())
            buffer.seek(0)
        
        return buffer
