package hr.obai.commons.spring;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hr.obai.commons.utils.JsonUtils;
import hr.obai.commons.controller.BaseControllerTemplate;
import hr.obai.commons.controller.http.HttpHeadersHelper;
import hr.obai.commons.repository.criteria.BaseCriteria;

import java.util.List;

import lombok.Value;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ActiveProfiles(SpringProfiles.TEST)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public abstract class TestSupportTemplate {

  @Autowired protected static MockMvc mockMvc;

  protected <T> T createGet(String url, Long id, Class<T> objectType) {
    T resultDto = null;
    try {
      val result =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.get(url + "/" + id)
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andReturn();
      resultDto = JsonUtils.toObject(result.getResponse().getContentAsString(), objectType);
    } catch (Exception e) {
      ExceptionUtils.rethrow(e);
    }

    return resultDto;
  }

  protected <T> List<T> createGet(String url, Class<T> objectType) {
    List<T> resultDto = null;
    try {
      val result =
          mockMvc
              .perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andReturn();
      resultDto = JsonUtils.toList(result.getResponse().getContentAsString(), objectType);
    } catch (Exception e) {
      ExceptionUtils.rethrow(e);
    }

    return resultDto;
  }

  protected <INPUT, OUTPUT> List<OUTPUT> searchPost(
      String url, INPUT dto, Class<OUTPUT> objectType) {
    List<OUTPUT> resultDto = null;
    try {
      val result =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.post(url)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(JsonUtils.toJsonString(dto)))
              .andExpect(status().isOk())
              .andReturn();
      resultDto = JsonUtils.toList(result.getResponse().getContentAsString(), objectType);
    } catch (Exception e) {
      ExceptionUtils.rethrow(e);
    }

    return resultDto;
  }

  protected <INPUT, OUTPUT> OUTPUT createPost(String url, INPUT dto, Class<OUTPUT> objectType) {
    OUTPUT resultDto = null;
    try {
      val result =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.post(url)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(JsonUtils.toJsonString(dto)))
              .andExpect(status().isOk())
              .andReturn();
      resultDto = JsonUtils.toObject(result.getResponse().getContentAsString(), objectType);
    } catch (Exception e) {
      ExceptionUtils.rethrow(e);
    }

    return resultDto;
  }

  protected <T> List<T> search(
      String url,
      BaseCriteria criteria,
      Class<T> objectType,
      ExpectedPagination expectedPagination) {

    try {
      val result =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.post(url + BaseControllerTemplate.SEARCH)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(JsonUtils.toJsonString(criteria)))
              .andExpect(status().isOk())
              .andExpect(header().longValue(HttpHeadersHelper.X_CURRENT_PAGE, expectedPagination.getCurrentPage()))
              .andExpect(
                  header()
                      .longValue(
                          HttpHeadersHelper.X_NUM_CURRENT_PAGE_ELEMENTS,
                          expectedPagination.getNumCurrentPageElements()))
              .andExpect(
                  header()
                      .longValue(HttpHeadersHelper.X_NUM_TOTAL_ELEMENTS, expectedPagination.getNumTotalElements()))
              .andExpect(
                  header().longValue(HttpHeadersHelper.X_NUM_TOTAL_PAGES, expectedPagination.getNumTotalPages()))
              .andReturn();

      return JsonUtils.toList(result.getResponse().getContentAsString(), objectType);

    } catch (Exception e) {
      ExceptionUtils.rethrow(e);
    }
    return null;
  }

  @Value
  protected static class ExpectedPagination {
    public static final ExpectedPagination EMPTY_PAGE = new ExpectedPagination(0, 0, 0, 0);
    private final long currentPage;
    private final long numCurrentPageElements;
    private final long numTotalElements;
    private final long numTotalPages;
  }

  protected <T> T createPut(String url, T dto, Class<T> objectType) {
    T resultDto = null;
    try {
      val result =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.put(url)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(JsonUtils.toJsonString(dto)))
              .andExpect(status().isOk())
              .andReturn();
      resultDto = JsonUtils.toObject(result.getResponse().getContentAsString(), objectType);
    } catch (Exception e) {
      ExceptionUtils.rethrow(e);
    }

    return resultDto;
  }

  protected <T> List<T> createPut(String url, List<T> dto, Class<T> objectType) {
    List<T> resultDto = null;
    try {
      val result =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.put(url)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(JsonUtils.toJsonString(dto)))
              .andExpect(status().isOk())
              .andReturn();
      resultDto = JsonUtils.toList(result.getResponse().getContentAsString(), objectType);
    } catch (Exception e) {
      ExceptionUtils.rethrow(e);
    }

    return resultDto;
  }

  protected void deletePost(String url, Long id) throws UnsupportedOperationException {
    try {
      mockMvc
          .perform(
              MockMvcRequestBuilders.delete(url + "/" + id).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andReturn();

    } catch (Exception e) {
      ExceptionUtils.rethrow(e);
    }
  }
}
