package project.alexoshiro.registerapi.migration;

public class CpfGenerator {

	public static String generateCPF() {
		StringBuilder partial = new StringBuilder();
		String cpf = "";
		int number;

		for (int i = 0; i < 9; i++) {
			number = (int) (Math.random() * 10);
			partial.append(String.valueOf(number));
		}

		cpf = partial + calculateVerificationDigit(partial.toString());

		return formatCPF(cpf);
	}

	private static String formatCPF(String cpf) {
		String block1 = cpf.substring(0, 3);
		String block2 = cpf.substring(3, 6);
		String block3 = cpf.substring(6, 9);
		String block4 = cpf.substring(9, 11);

		return String.format("%s.%s.%s-%s", block1, block2, block3, block4);
	}

	private static String calculateVerificationDigit(String num) {
		int firstDigit, secondDigit, sum = 0, weight = 10;

		for (int i = 0; i < num.length(); i++)
			sum += Integer.parseInt(num.substring(i, i + 1)) * weight--;

		if (sum % 11 == 0 || sum % 11 == 1)
			firstDigit = 0;
		else
			firstDigit = 11 - (sum % 11);

		sum = 0;
		weight = 11;
		for (int i = 0; i < num.length(); i++)
			sum += Integer.parseInt(num.substring(i, i + 1)) * weight--;

		sum += firstDigit * 2;
		if (sum % 11 == 0 || sum % 11 == 1)
			secondDigit = 0;
		else
			secondDigit = 11 - (sum % 11);

		return String.valueOf(firstDigit).concat(String.valueOf(secondDigit));
	}
}
