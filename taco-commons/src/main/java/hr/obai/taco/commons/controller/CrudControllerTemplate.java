package hr.obai.taco.commons.controller;

import hr.obai.taco.commons.repository.criteria.BaseCriteria;
import hr.obai.taco.commons.repository.criteria.CriteriaWrapper;
import hr.obai.taco.commons.service.CrudServiceTemplate;
import hr.obai.taco.commons.dto.BaseDto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Template for a controller */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CrudControllerTemplate<DTO extends BaseDto, CRITERIA extends BaseCriteria>
    extends BaseControllerTemplate<DTO, CRITERIA> {

  private CrudServiceTemplate service;

  @PostMapping(
      path = {SEARCH},
      headers = ACCEPT_APPLICATION_JSON_UTF8_VALUE)
  protected ResponseEntity<List<DTO>> search(@RequestBody(required = false) CRITERIA criteria) {
    val wrapper = new CriteriaWrapper<>(criteria);
    return new RequestHandler().handle(() -> service.search(wrapper));
  }

  @PostMapping(
      path = {""},
      headers = ACCEPT_APPLICATION_JSON_UTF8_VALUE)
  protected ResponseEntity<DTO> save(@RequestBody DTO dto) {
    return new RequestHandler<DTO>().handle(() -> service.create(dto));
  }

  @PutMapping(
      path = {""},
      headers = ACCEPT_APPLICATION_JSON_UTF8_VALUE)
  protected ResponseEntity<DTO> update(@RequestBody DTO dto) {
    return new RequestHandler<DTO>().handle(() -> service.update(dto));
  }

  @GetMapping(value = "/{id}")
  protected ResponseEntity<DTO> findById(@PathVariable Long id) {
    return new RequestHandler<DTO>().handle(() -> service.findById(id));
  }

  @DeleteMapping(path = "/{id}")
  protected void deleteById(@PathVariable Long id) {
    service.deleteById(id);
  }

  @DeleteMapping(
      path = {""},
      headers = ACCEPT_APPLICATION_JSON_UTF8_VALUE)
  protected void deleteByIds(@RequestBody List<Long> ids) {
    service.deleteByIds(ids);
  }

  /*  @GetMapping(
      path = {"/export/{type}"},
      headers = ACCEPT_APPLICATION_JSON_UTF8_VALUE)
  protected ResponseEntity<Base64Response> export(
      @PathVariable ExportType type, CRITERIA criteria) {
    val wrapper = new CriteriaWrapper<>(criteria);
    return new RequestHandler<Base64Response>().handle(() -> service.export(type, wrapper));
  }*/
}
