package project.alexoshiro.registerapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SourceController {

	@Value("${project.github.link}")
	private String github;

	@GetMapping(value = "/source", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> showSource() {
		return ResponseEntity.ok("<a href=\"" + github + "\">" + github + "</a>");
	}
}
