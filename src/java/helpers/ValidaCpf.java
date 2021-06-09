/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author Kissy de Melo
 */
public class ValidaCpf {
      public static boolean isValidCPF(String cpf) {
        try {
            int d1, d2, soma = 0, peso = 10, resto, dig2;
        
            dig2 = Integer.parseInt(cpf.substring(10,11));

            if (cpf.equals("11111111111") 
                || cpf.equals("22222222222")
                || cpf.equals("33333333333")
                || cpf.equals("44444444444")
                || cpf.equals("55555555555")
                || cpf.equals("66666666666")
                || cpf.equals("77777777777")
                || cpf.equals("88888888888")
                || cpf.equals("99999999999"))
                    return false;

            for (int i = 0; i < cpf.length()-2; i++)
               soma += Integer.parseInt(cpf.substring(i, i + 1)) * peso--;

            resto = (soma % 11);
            d1 = resto < 2 ? 0 :  11 - resto;

            if(d1 != Integer.parseInt(cpf.substring(9,10)))
                return false;

            soma = 0;
            peso = 11;

            cpf = cpf.substring(0, 9);
            cpf = cpf + Integer.toString(d1);
            for (int i = 0; i < cpf.length(); i++)
                   soma += Integer.parseInt(cpf.substring(i, i + 1)) * peso--;

            resto = (soma % 11);
            d2 = resto < 2 ? 0 :  11 - resto;
            return d2 == dig2;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
