package com.sigfe.backend.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
// BusinessException e uma classe que nos criamos para representar erros que nao sao falhas do sistema,
// mas sim violacoes das regras de negocia