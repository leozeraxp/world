package hello.util;

import hello.exceptions.RegraNegocioException;

public class ValidarCpf {

    public void executar(String cpf){
        isCPF(cpf);
        char dig10 = calcularPrimeiroDigitoVerificadorCpf(cpf);
        calcularSegundoDigitoVerificadorCpf(cpf,dig10);
    }

    private void isCPF(String cpf) {
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") ||
                cpf.equals("33333333333") ||
                cpf.equals("44444444444") ||
                cpf.equals("55555555555") ||
                cpf.equals("66666666666") ||
                cpf.equals("77777777777") ||
                cpf.equals("88888888888") ||
                cpf.equals("99999999999") ||
                cpf.length() != 11) {
            throw new RegraNegocioException("CPF_INVALIDO");
        }
    }

    public char calcularPrimeiroDigitoVerificadorCpf(String cpf) {
        int sm = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            sm += num * peso;
            peso--;
        }
        int r = 11 - (sm % 11);
        char dig10;
        if (r == 10 || r == 11) {
            return dig10 = '0';
        } else {
            return dig10 = (char) (r + '0');
        }
    }

    public void calcularSegundoDigitoVerificadorCpf(String cpf, char dig10) {
        int sm = 0;
        int peso = 11;
        for (int i = 0; i < 10; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            sm += num * peso;
            peso--;
        }
        int r = 11 - (sm % 11);
        char dig11;
        if (r == 10 || r == 11) {
            dig11 = '0';
        } else {
            dig11 = (char) (r + '0');
        }

        if (dig10 != cpf.charAt(9) && dig11 != cpf.charAt(10)) {
            throw new RegraNegocioException("CPF inválido!");
        }
    }
}
