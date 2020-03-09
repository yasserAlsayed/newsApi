package io.cryptoapis.news.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class NewsDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;

	private String description;

	private String text;

	private LocalDateTime date;

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		NewsDto newsResource = (NewsDto) o;
		return Objects.equals(this.title, newsResource.title)
				&& Objects.equals(this.description, newsResource.description)
				&& Objects.equals(this.text, newsResource.text) && Objects.equals(this.date, newsResource.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, description, date, text);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class NewsDto {\n");

		sb.append("    Title: ").append(toIndentedString(title)).append("\n");
		sb.append("    Date: ").append(toIndentedString(date)).append("\n");
		sb.append("    Description: ").append(toIndentedString(description)).append("\n");
		sb.append("    text: ").append(toIndentedString(text)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDate() {
		return date; 
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
