package project.alexoshiro.registerapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeoplePaginationResultDTO {
	private List<PersonDTO> payload;
	private LinkDTO links;
}
