package project.alexoshiro.registerapi.util;

import java.util.InputMismatchException;

public class CpfUtils {
	
	public static boolean isValidCpf(String cpf) {
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11))
			return false;

		char dig10;
		char dig11;
		int iterator;
		int num;

		try {
			int sum = 0;
			int weight = 10;
			for (iterator = 0; iterator < 9; iterator++) {
				num = (cpf.charAt(iterator) - 48);
				sum = sum + (num * weight);
				weight = weight - 1;
			}

			int result = 11 - (sum % 11);
			if ((result == 10) || (result == 11))
				dig10 = '0';
			else
				dig10 = (char) (result + 48);

			sum = 0;
			weight = 11;
			for (iterator = 0; iterator < 10; iterator++) {
				num = (cpf.charAt(iterator) - 48);
				sum = sum + (num * weight);
				weight = weight - 1;
			}

			result = 11 - (sum % 11);
			if ((result == 10) || (result == 11))
				dig11 = '0';
			else
				dig11 = (char) (result + 48);

			return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
		} catch (InputMismatchException ex) {
			return false;
		}
	}
	
}
