package project.alexoshiro.registerapi.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import project.alexoshiro.registerapi.dto.ErrorNormalizerDTO;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		ErrorNormalizerDTO body = new ErrorNormalizerDTO(String.valueOf(status.value()), errors);

		return new ResponseEntity<>(body, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		List<String> errors = new ArrayList<>();
		if (ex.getLocalizedMessage().contains("java.time.LocalDate")
				&& ex.getLocalizedMessage().contains("birth_date")) {
			errors.add("Data de nascimento é inválido.");
		} else {
			errors.add(ex.getLocalizedMessage());
		}

		ErrorNormalizerDTO body = new ErrorNormalizerDTO(String.valueOf(status.value()), errors);

		return new ResponseEntity<>(body, headers, status);
	}

}
