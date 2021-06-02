package hr.obai.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageableDto {

  private Integer page = 0;
  private Integer size = 10;
  private List<SortColumn> sortColumns = new ArrayList<>();

  public PageableDto() {
    initSorting();
  }

  public PageableDto(Integer size) {
    this();
    this.size = size;
  }

  public PageableDto(List<SortColumn> sortColumns) {
    this.sortColumns = sortColumns;
    initSorting();
  }

  public PageableDto(Integer page, Integer size, List<SortColumn> sortColumns) {
    this(sortColumns);
    this.page = page;
    this.size = size;
  }

  @JsonIgnore
  public Pageable getPageable() {
    val sort = Sort.by(buildSortOrder());
    val pageable = PageRequest.of(page, size, sort);
    return pageable;
  }

  private List<Sort.Order> buildSortOrder() {
    List<Sort.Order> sortOrder = new ArrayList<>();
    if (!sortColumns.isEmpty()) {
      sortColumns.forEach(
          sortColumn ->
              sortOrder.add(
                  new Sort.Order(
                      sortColumn.isInverse() ? Sort.Direction.DESC : Sort.Direction.ASC,
                      sortColumn.getColumn())));
    }

    return sortOrder;
  }

  private void initSorting() {
    if (sortColumns.isEmpty()) {
      sortColumns.add(new SortColumn("id", true));
    }
  }
}
