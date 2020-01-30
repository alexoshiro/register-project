package project.alexoshiro.registerapi.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
import project.alexoshiro.registerapi.dto.PersonDTO;
import project.alexoshiro.registerapi.enums.GenderEnum;

@Document(collection = "person")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Person {

	@Id
	private ObjectId id;

	@NotNull
	private String name;
	private GenderEnum gender;
	private String email;

	@JsonProperty("birth_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@NotNull
	private LocalDate birthDate;

	private String nationality;
	private String citizenship;

	@NotNull
	private String cpf;

	public PersonDTO convertToDTO() {
		return PersonDTO.builder()
				.id(this.id.toString())
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
