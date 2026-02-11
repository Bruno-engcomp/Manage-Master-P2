<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ManageMaster | Dashboard Administrativo</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://cdn.plot.ly/plotly-2.29.1.min.js"></script>
    <link rel="stylesheet" href="../css/dashboard.css">
</head>
<body class="dash-body">

    <header class="dash-header">
        <div class="nav-left">
            <span class="user-badge"><i class="fas fa-chart-line"></i> Painel de gráficos </span>
        </div>

        <div class="nav-logo-container">
            <div class="nav-sigma-icon">&#931;</div>
            <div class="nav-brand-stack">
                <span class="nav-brand-name">ANAGE</span>
                <span class="nav-brand-name">ASTER</span>
            </div>
        </div>

        <div class="nav-right">
            <a href="#" class="btn-refresh"><i class="fas fa-sync"></i> Atualizar Dados</a>
        </div>
    </header>

    <main class="dash-container">
        <div class="dash-title">
            <h1>Visão Geral do Negócio</h1>
            <p>Indicadores processados via servidor.</p>
        </div>

        <div class="dashboard-grid">
            
            <div class="chart-box">
                <div class="chart-header">
                    <h3><i class="fas fa-line-chart"></i> Evolução do Saldo</h3>
                    <span class="badge">Série Temporal</span>
                </div>
                <div class="chart-area">
                    <div id="chart-saldo" class="plotly-chart"></div>
                </div>
            </div>

            <div class="chart-box">
                <div class="chart-header">
                    <h3><i class="fas fa-columns"></i> Receitas vs Despesas</h3>
                    <span class="badge">Comparativo</span>
                </div>
                <div class="chart-area">
                    <div id="chart-receitas-despesas" class="plotly-chart"></div>
                </div>
            </div>

            <div class="chart-box">
                <div class="chart-header">
                    <h3><i class="fas fa-trophy"></i> Top 10 Produtos</h3>
                    <span class="badge">Ranking</span>
                </div>
                <div class="chart-area">
                    <div id="chart-produtos" class="plotly-chart"></div>
                </div>
            </div>

            <div class="chart-box">
                <div class="chart-header">
                    <h3><i class="fas fa-clock"></i> Produtos Próximos do Vencimento</h3>
                    <span class="badge">Risco de Perda</span>
                </div>
                <div class="chart-area">
                    <div id="chart-vencimento" class="plotly-chart"></div>
                </div>
            </div>

            <div class="chart-box">
                <div class="chart-header">
                    <h3><i class="fas fa-boxes-stacked"></i> Nível de Estoque Crítico</h3>
                    <span class="badge">Abaixo do Mínimo</span>
                </div>
                <div class="chart-area">
                    <div id="chart-estoque" class="plotly-chart"></div>
                </div>
            </div>

        </div>
    </main>

    <script src="../js/dashboard.js"></script>
</body>
</html>
