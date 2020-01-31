package project.alexoshiro.registerapi.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;

import project.alexoshiro.registerapi.dto.PersonDTO;

public class ValidationUtils {

	private static final String cpfPattern = "([0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2})";

	public static List<String> validatePersonRequest(PersonDTO person, boolean updating) {
		List<String> errors = new ArrayList<>();
		if (person.getEmail() != null && !EmailValidator.getInstance().isValid(person.getEmail())) {
			errors.add("Email inválido.");
		}
		if (!updating || (updating && person.getCpf() != null)) {
			if (!person.getCpf().matches(cpfPattern)) {
				errors.add("Formato do CPF inválido");
			} else {
				String cpfWithoutMask = person.getCpf().replaceAll("\\.", "").replaceAll("\\-", "");
				if (!CpfUtils.isValidCpf(cpfWithoutMask)) {
					errors.add("CPF inválido.");
				}
			}
		}

		return errors;
	}
}
