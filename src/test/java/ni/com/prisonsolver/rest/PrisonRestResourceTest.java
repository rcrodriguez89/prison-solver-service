package ni.com.prisonsolver.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ni.com.prisonsolver.dto.PrisonDtoRequest;
import ni.com.prisonsolver.persistence.ChallengeStats;
import ni.com.prisonsolver.persistence.PrisonChallenge;
import ni.com.prisonsolver.service.PrisonSolverService;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ni.com.prisonsolver.support.util.AppConstants.ROW_SEPARATOR;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PrisonRestResourceTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PrisonSolverService prisonSolverService;

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||,|||||||||;| ||   P|;||    |*|;|v| |<<*|;|v<<<***|;|v^ ****|;||||S||||,true",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^    <|;||||S||||,|||||||||;| ||   P|;||    | |;|v| |<< |;|v<<<   |;|v^<<<<<|;||||S||||, false"
  })
  void findChallenge_whenIdExists_httpStatusCodeShouldOk(String prisonMap, String solutionMap, boolean canEscape) throws Exception {
    var randomId = UUID.randomUUID();
    var challengeMock = new PrisonChallenge();
    challengeMock.setId(randomId);
    challengeMock.setCanEscape(canEscape);
    challengeMock.setPrison(prisonMap);
    challengeMock.setSolution(solutionMap);
    when(this.prisonSolverService.findById(any()))
        .thenReturn(Optional.of(challengeMock));

    mockMvc.perform(MockMvcRequestBuilders
        .get("/prisoner/{id}", randomId.toString())
        .contentType(APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.canEscape", is(challengeMock.isCanEscape())))
      .andExpect(jsonPath("$.challenge", is(List.of(challengeMock.getPrison().split(ROW_SEPARATOR)))))
      .andExpect(jsonPath("$.solution", is(List.of(challengeMock.getSolution().split(ROW_SEPARATOR)))));
  }

  @Test
  void findChallenge_whenIdNotExists_httpStatusCodeShouldResourceNotFound() throws Exception {
    var randomId = UUID.randomUUID();
    when(this.prisonSolverService.findById(any()))
      .thenReturn(Optional.empty());

    mockMvc.perform(MockMvcRequestBuilders
        .get("/prisoner/{id}", randomId.toString())
        .contentType(APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  void requestChallengeStats_httpStatusCodeShouldOk() throws Exception {
    var statsMock = new ChallengeStats(5, 10);
    when(this.prisonSolverService.getChallengeStats())
      .thenReturn(statsMock);

    mockMvc.perform(MockMvcRequestBuilders
        .get("/prisoner/stats")
        .contentType(APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.count_successful_scape", is(statsMock.countSuccessfulEscape())))
      .andExpect(jsonPath("$.count_unsuccessful_scape", is(statsMock.countUnsuccessfulEscape())))
      .andExpect(jsonPath("$.ratio", is(new BigDecimal("0.50").doubleValue())));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||,|||||||||;| ||   P|;||    |*|;|v| |<<*|;|v<<<***|;|v^ ****|;||||S||||,true",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^    <|;||||S||||,|||||||||;| ||   P|;||    | |;|v| |<< |;|v<<<   |;|v^<<<<<|;||||S||||, false"
  })
  void solveAndSaveChallenge_whenPrisonFormatIsValid_httpStatusCodeShouldOk(String prisonMap, String solutionMap, boolean canEscape) throws Exception {
    var randomId = UUID.randomUUID();
    var challengeMock = new PrisonChallenge();
    challengeMock.setId(randomId);
    challengeMock.setCanEscape(canEscape);
    challengeMock.setPrison(prisonMap);
    challengeMock.setSolution(solutionMap);
    PrisonDtoRequest dto = new PrisonDtoRequest();
    dto.getPrison().addAll(List.of(prisonMap.split(AppConstants.ROW_SEPARATOR)));
    when(this.prisonSolverService.solve(isA(PrisonDtoRequest.class)))
      .thenReturn(challengeMock);

    String requestBody = objectMapper.writeValueAsString(dto);
    mockMvc.perform(MockMvcRequestBuilders
        .post("/prisoner")
        .content(requestBody)
        .contentType(APPLICATION_JSON))
      .andDo(print())
      .andExpect(canEscape ? status().isOk() : status().isForbidden())
      .andExpect(content().string(""))
      .andExpect(header()
        .string(HttpHeaders.LOCATION, containsString(String.format("/prisoner/%s", randomId.toString()))));
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S|||,Rows must have the same number of characters",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^    <|;|||<S||||,The prison walls must have only one Exit (letter S) and rest characters must be pipe"
  })
  void solveAndSaveChallenge_whenPrisonFormatIsValid_httpStatusCodeShouldBadRequest(String prisonMap, String expectedError) throws Exception {
    PrisonDtoRequest dto = new PrisonDtoRequest();
    dto.getPrison().addAll(List.of(prisonMap.split(AppConstants.ROW_SEPARATOR)));

    String requestBody = objectMapper.writeValueAsString(dto);
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
        .post("/prisoner")
        .content(requestBody)
        .contentType(APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isBadRequest())
      .andReturn();

    ResponseBodyError responseBody = objectMapper
      .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
    assertEquals(1, responseBody.errors.size());
    assertEquals(expectedError, responseBody.errors.get(0));
  }

  private record ResponseBodyError(List<String> errors) {}
}
