package com.template.validator;

public class DificuldadeValidador implements Validador<String> {
    private String dificuldade;

    public DificuldadeValidador(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    @Override
    public boolean validar(String valor) {
        try {
            Integer.parseInt(dificuldade.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "A dificuldade deve ser um número inteiro válido.";
    }

    @Override
    public String getValor() {
        return dificuldade;
    }
}
