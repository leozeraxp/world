package hello.util;

import hello.exceptions.RegraNegocioException;

import java.util.InputMismatchException;

public class ValidarCpf {

    public void executar(String cpf){
        boolean valido = isCpf(cpf);

        if(!valido){
            throw new RegraNegocioException("CPF_INVALIDO");
        }
    }

    public boolean isCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }

        int[] digits = cpf.chars().map(Character::getNumericValue).toArray();
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }

        int firstDigit = 11 - (sum % 11);

        if (firstDigit > 9) {
            firstDigit = 0;
        }

        sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (11 - i);
        }

        sum += firstDigit * 2;

        int secondDigit = 11 - (sum % 11);

        if (secondDigit > 9) {
            secondDigit = 0;
        }

        return firstDigit == digits[9] && secondDigit == digits[10];
    }
}
