
from decimal import Decimal
from typing import Union


def formatar_moeda(valor: Union[Decimal, float, int]) -> str:
    """
    Formata um valor numérico como moeda brasileira
    
    Args:
        valor: Valor a ser formatado
        
    Returns:
        String formatada como R$ X.XXX,XX
    """
    if isinstance(valor, Decimal):
        valor = float(valor)
    
    return f"R$ {valor:,.2f}".replace(',', 'X').replace('.', ',').replace('X', '.')


def formatar_percentual(valor: Union[Decimal, float], casas_decimais: int = 2) -> str:
    """
    Formata um valor como percentual
    
    Args:
        valor: Valor a ser formatado (ex: 15.5 para 15.5%)
        casas_decimais: Número de casas decimais
        
    Returns:
        String formatada como X,XX%
    """
    if isinstance(valor, Decimal):
        valor = float(valor)
    
    return f"{valor:.{casas_decimais}f}%"
