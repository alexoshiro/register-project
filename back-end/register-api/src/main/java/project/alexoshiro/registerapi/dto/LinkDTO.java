package project.alexoshiro.registerapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class LinkDTO {

	@JsonProperty("page_items")
	private Integer pageItems;

	@JsonProperty("total_pages")
	private Integer totalPages;

	@JsonProperty("page_number")
	private Integer pageNumber;

	private String next;

	private String previous;
	
	@JsonProperty("total_items")
	private long totalItems;

	public LinkDTO(String baseUrl, int pageItemsQuantity, int totalPages, int actualPage, int pageItems, long totalItems) {
		this.pageItems = pageItemsQuantity;
		this.totalPages = totalPages;
		this.pageNumber = actualPage;
		this.next = actualPage < totalPages ? createUrl(baseUrl, actualPage + 1, pageItems) : null;
		this.previous = actualPage > 1 ? createUrl(baseUrl, actualPage - 1, pageItems) : null;
		this.totalItems = totalItems;
	}

	private String createUrl(String baseUrl, int page, int pageItems) {
		StringBuilder query = new StringBuilder(baseUrl + "?");
		query.append("page_items=" + pageItems);
		query.append("&page=" + page);
		return query.toString();
	}
}
