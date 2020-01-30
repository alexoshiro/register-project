package project.alexoshiro.registerapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.alexoshiro.registerapi.dto.ErrorNormalizerDTO;
import project.alexoshiro.registerapi.dto.PersonDTO;
import project.alexoshiro.registerapi.model.Person;
import project.alexoshiro.registerapi.service.IPersonService;
import project.alexoshiro.registerapi.util.ValidationUtils;

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private IPersonService personService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPeople() {
		List<Person> people = personService.getPeople();
		List<PersonDTO> dto = people.stream()
				.map(Person::convertToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPersonById(@PathVariable String id) {
		Optional<Person> result = personService.getPersonById(id);

		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(result.get().convertToDTO());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerPerson(@RequestBody @Valid PersonDTO person) {
		List<String> errors = ValidationUtils.validatePersonRequest(person);
		if (errors.isEmpty()) {
			Person model = person.convertToModel();
			personService.savePerson(model);
			return ResponseEntity.ok(person);
		}
		return ResponseEntity.badRequest()
				.body(new ErrorNormalizerDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), errors));

	}

	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePerson(@PathVariable String id, @RequestBody PersonDTO person) {
		List<String> errors = ValidationUtils.validatePersonRequest(person);

		if (errors.isEmpty()) {
			Person model = person.convertToModel();
			Optional<Person> updatedModel = personService.updatePerson(id, model);

			if (updatedModel.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(updatedModel.get().convertToDTO());
		}
		return ResponseEntity.badRequest()
				.body(new ErrorNormalizerDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), errors));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable String id) {
		Optional<Person> result = personService.deletePersonById(id);

		if (result.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.unprocessableEntity().build();
	}
}
