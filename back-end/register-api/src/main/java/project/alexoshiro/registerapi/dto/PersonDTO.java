package project.alexoshiro.registerapi.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.alexoshiro.registerapi.enums.GenderEnum;
import project.alexoshiro.registerapi.model.Person;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PersonDTO {

	private String id;

	@NotBlank(message = "Nome não pode ser vazio.")
	private String name;
	private GenderEnum gender;
	private String email;

	@JsonProperty("birth_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthDate;

	private String nationality;
	private String citizenship;

	@NotBlank(message = "CPF não pode ser vazio.")
	private String cpf;

	public Person convertToModel() {
		return Person.builder()
				.id(this.id != null ? new ObjectId(this.id) : null)
				.name(this.name)
				.gender(this.gender)
				.email(this.email)
				.birthDate(this.birthDate)
				.nationality(this.nationality)
				.citizenship(this.citizenship)
				.cpf(this.cpf)
				.build();
	}
}
