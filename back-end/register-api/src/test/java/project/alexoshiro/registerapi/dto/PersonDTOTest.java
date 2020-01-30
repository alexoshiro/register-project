package project.alexoshiro.registerapi.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import project.alexoshiro.registerapi.enums.GenderEnum;
import project.alexoshiro.registerapi.model.Person;

public class PersonDTOTest {

	@Test
	public void convertToModelDTOWithoutIdModelIdShouldBeNull() {
		PersonDTO dto = PersonDTO.builder()
				.name("Teste")
				.gender(GenderEnum.MALE)
				.email("teste@example.com")
				.birthDate(LocalDate.now())
				.nationality("Brasil")
				.citizenship("brasileiro")
				.cpf("946.359.985-17")
				.build();
		Person model = dto.convertToModel();
		assertEquals(dto.getId(), model.getId());
		assertEquals(dto.getName(), model.getName());
		assertEquals(dto.getGender(), model.getGender());
		assertEquals(dto.getEmail(), model.getEmail());
		assertEquals(dto.getBirthDate(), model.getBirthDate());
		assertEquals(dto.getNationality(), model.getNationality());
		assertEquals(dto.getCitizenship(), model.getCitizenship());
		assertEquals(dto.getCpf(), model.getCpf());
	}

	@Test
	public void convertToModelDTOWithIdModelShouldHaveId() {
		PersonDTO dto = PersonDTO.builder()
				.id("5e32fbb40d71210d2c4c2ab5")
				.name("Teste")
				.gender(GenderEnum.MALE)
				.email("teste@example.com")
				.birthDate(LocalDate.now())
				.nationality("Brasil")
				.citizenship("brasileiro")
				.cpf("946.359.985-17")
				.build();
		Person model = dto.convertToModel();
		assertEquals(dto.getId(), model.getId().toString());
		assertEquals(dto.getName(), model.getName());
		assertEquals(dto.getGender(), model.getGender());
		assertEquals(dto.getEmail(), model.getEmail());
		assertEquals(dto.getBirthDate(), model.getBirthDate());
		assertEquals(dto.getNationality(), model.getNationality());
		assertEquals(dto.getCitizenship(), model.getCitizenship());
		assertEquals(dto.getCpf(), model.getCpf());
	}
}
