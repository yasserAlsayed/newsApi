package io.cryptoapis.news.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cryptoapis.news.dto.NewsDto;

/**
 * ApiModelNews
 */
@Validated
public class ApiModelNews  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("data")
  @Valid
  private List<NewsDto> data = null;

  @JsonProperty("paging")
  private ApiModelPageAndSort paging = null;

  public ApiModelNews data(List<NewsDto> data) {
    this.data = data;
    return this;
  }

  public ApiModelNews addDataItem(NewsDto dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  @Valid

  public List<NewsDto> getData() {
    return data;
  }

  public void setData(List<NewsDto> data) {
    this.data = data;
  }

  public ApiModelNews paging(ApiModelPageAndSort paging) {
    this.paging = paging;
    return this;
  }


  @Valid

  public ApiModelPageAndSort getPaging() {
    return paging;
  }

  public void setPaging(ApiModelPageAndSort paging) {
    this.paging = paging;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiModelNews news = (ApiModelNews) o;
    return Objects.equals(this.data, news.data) &&
        Objects.equals(this.paging, news.paging);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, paging);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiModelNews {\n");
    
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    paging: ").append(toIndentedString(paging)).append("\n");
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
}

