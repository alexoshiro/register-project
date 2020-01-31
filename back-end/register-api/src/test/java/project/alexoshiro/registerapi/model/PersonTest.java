package project.alexoshiro.registerapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import project.alexoshiro.registerapi.dto.PersonDTO;
import project.alexoshiro.registerapi.enums.GenderEnum;

public class PersonTest {
	@Test
	public void convertToDTOFromModel() {
		Person model = Person.builder()
				.id(new ObjectId("5e32fbb40d71210d2c4c2ab5"))
				.name("Teste")
				.gender(GenderEnum.MALE)
				.email("teste@example.com")
				.birthDate(LocalDate.now())
				.nationality("Brasil")
				.citizenship("brasileiro")
				.cpf("946.359.985-17")
				.build();
		PersonDTO dto = model.convertToDTO();
		assertEquals(model.getId().toString(), dto.getId());
		assertEquals(model.getName(), dto.getName());
		assertEquals(model.getGender(), dto.getGender());
		assertEquals(model.getEmail(), dto.getEmail());
		assertEquals(model.getBirthDate(), dto.getBirthDate());
		assertEquals(model.getNationality(), dto.getNationality());
		assertEquals(model.getCitizenship(), dto.getCitizenship());
		assertEquals(model.getCpf(), dto.getCpf());

	}

}
