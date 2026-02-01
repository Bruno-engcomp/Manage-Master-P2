"""
API Flask para servir gráficos e relatórios
"""
from flask import Flask, jsonify, send_file, request
from flask_cors import CORS
from io import BytesIO
import os
from datetime import datetime
from api_client import APIClient
from estatisticas import Estatisticas
from graficos import Graficos
from relatorios import RelatorioPDF

app = Flask(__name__)
CORS(app)  # Permitir CORS para integração com frontend

# Inicializar componentes
api_client = APIClient(base_url=os.getenv('SPRING_BOOT_URL', 'http://localhost:8080'))
estatisticas = Estatisticas(api_client)
graficos = Graficos(estatisticas)
relatorio_pdf = RelatorioPDF(estatisticas, graficos)


@app.route('/', methods=['GET'])
def index():
    """Página inicial com lista de endpoints disponíveis"""
    return jsonify({
        'service': 'Python Analytics API',
        'version': '1.0.0',
        'endpoints': {
            'health': '/health',
            'graficos': {
                'receitas_despesas': '/api/graficos/receitas-despesas?meses=12',
                'lucro_mensal': '/api/graficos/lucro-mensal?meses=12',
                'produtos_vendidos': '/api/graficos/produtos-vendidos?limite=10',
                'indicadores': '/api/graficos/indicadores',
                'evolucao_financeira': '/api/graficos/evolucao-financeira?meses=12'
            },
            'estatisticas': {
                'indicadores': '/api/estatisticas/indicadores',
                'lucro_mensal': '/api/estatisticas/lucro-mensal?meses=12',
                'produtos_vendidos': '/api/estatisticas/produtos-vendidos?limite=10'
            },
            'relatorios': {
                'pdf': '/api/relatorios/pdf'
            }
        }
    })


@app.route('/health', methods=['GET'])
def health():
    """Endpoint de health check"""
    return jsonify({'status': 'ok', 'service': 'python-analytics'})


@app.route('/api/graficos/receitas-despesas', methods=['GET'])
def get_grafico_receitas_despesas():
    """
    Retorna gráfico de receitas vs despesas em formato JSON (Plotly)
    """
    try:
        meses = int(request.args.get('meses', 12))
        fig = graficos.grafico_receitas_despesas(meses)
        return jsonify(fig.to_dict())
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/graficos/lucro-mensal', methods=['GET'])
def get_grafico_lucro_mensal():
    """
    Retorna gráfico de lucro mensal em formato JSON (Plotly)
    """
    try:
        meses = int(request.args.get('meses', 12))
        fig = graficos.grafico_lucro_mensal(meses)
        return jsonify(fig.to_dict())
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/graficos/produtos-vendidos', methods=['GET'])
def get_grafico_produtos_vendidos():
    """
    Retorna gráfico de produtos mais vendidos em formato JSON (Plotly)
    """
    try:
        limite = int(request.args.get('limite', 10))
        fig = graficos.grafico_produtos_mais_vendidos(limite)
        return jsonify(fig.to_dict())
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/graficos/indicadores', methods=['GET'])
def get_grafico_indicadores():
    """
    Retorna gráfico de indicadores de desempenho em formato JSON (Plotly)
    """
    try:
        fig = graficos.grafico_indicadores_desempenho()
        return jsonify(fig.to_dict())
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/graficos/evolucao-financeira', methods=['GET'])
def get_grafico_evolucao():
    """
    Retorna gráfico de evolução financeira em formato JSON (Plotly)
    """
    try:
        meses = int(request.args.get('meses', 12))
        fig = graficos.grafico_evolucao_financeira(meses)
        return jsonify(fig.to_dict())
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/estatisticas/indicadores', methods=['GET'])
def get_indicadores():
    """
    Retorna indicadores de desempenho em formato JSON
    """
    try:
        indicadores = estatisticas.calcular_indicadores_desempenho()
        return jsonify(indicadores)
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/estatisticas/lucro-mensal', methods=['GET'])
def get_lucro_mensal():
    """
    Retorna dados de lucro mensal em formato JSON
    """
    try:
        meses = int(request.args.get('meses', 12))
        lucro_mensal = estatisticas.calcular_lucro_mensal(meses)
        
        # Converter Decimal para float para serialização JSON
        resultado = {}
        for mes, valores in lucro_mensal.items():
            resultado[mes] = {
                'receita': float(valores['receita']),
                'despesa': float(valores['despesa']),
                'lucro': float(valores['lucro'])
            }
        
        return jsonify(resultado)
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/estatisticas/produtos-vendidos', methods=['GET'])
def get_produtos_vendidos():
    """
    Retorna lista de produtos mais vendidos em formato JSON
    """
    try:
        limite = int(request.args.get('limite', 10))
        produtos = estatisticas.produtos_mais_vendidos(limite)
        return jsonify(produtos)
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/relatorios/pdf', methods=['GET'])
def gerar_relatorio_pdf():
    """
    Gera e retorna um relatório completo em PDF
    """
    try:
        pdf_buffer = relatorio_pdf.gerar_relatorio_completo()
        pdf_buffer.seek(0)
        
        return send_file(
            pdf_buffer,
            mimetype='application/pdf',
            as_attachment=True,
            download_name=f'relatorio_desempenho_{datetime.now().strftime("%Y%m%d_%H%M%S")}.pdf'
        )
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    port = int(os.getenv('PORT', 5000))
    debug = os.getenv('DEBUG', 'False').lower() == 'true'
    app.run(host='0.0.0.0', port=port, debug=debug)
