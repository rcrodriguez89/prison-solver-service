package ni.com.prisonsolver.service;

import ni.com.prisonsolver.dto.PrisonDtoRequest;
import ni.com.prisonsolver.factory.CellFactory;
import ni.com.prisonsolver.factory.GuardFactory;
import ni.com.prisonsolver.factory.guardviewrange.GuardViewRangeStrategyFactory;
import ni.com.prisonsolver.persistence.ChallengeStats;
import ni.com.prisonsolver.persistence.PrisonChallenge;
import ni.com.prisonsolver.repository.PrisonChallengeRepository;
import ni.com.prisonsolver.support.helper.PathwayFinder;
import ni.com.prisonsolver.support.helper.PrisonMapper;
import ni.com.prisonsolver.support.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrisonSolverServiceTest {

  private GuardFactory guardFactory = new GuardFactory();

  private CellFactory cellFactory = new CellFactory(guardFactory);

  private GuardViewRangeStrategyFactory guardViewRangeStrategyFactory = new GuardViewRangeStrategyFactory();

  private PrisonMapper prisonMapper;

  private PathwayFinder pathwayFinder;

  @Mock
  private PrisonChallengeRepository prisonChallengeRepository;

  private PrisonSolverService prisonSolverService;

  @BeforeEach
  void setup() {
    this.prisonMapper = new PrisonMapper(cellFactory);
    this.pathwayFinder = new PathwayFinder(guardViewRangeStrategyFactory);
    this.prisonSolverService = new PrisonSolverService(prisonMapper, pathwayFinder, prisonChallengeRepository);
  }

  @Test
  void requestChallengeStats() {
    var statsMock = new ChallengeStats(5, 10);
    when(this.prisonChallengeRepository.getChallengeStats())
      .thenReturn(statsMock);
    var result = this.prisonSolverService.getChallengeStats();
    verify(this.prisonChallengeRepository, times(1))
      .getChallengeStats();
    assertNotNull(result);
    assertEquals(5, result.countSuccessfulEscape());
    assertEquals(10, result.countUnsuccessfulEscape());
    assertEquals(new BigDecimal("0.50"), result.getRatio());
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||,|||||||||;| ||   P|;||    |*|;|v| |<<*|;|v<<<***|;|v^ ****|;||||S||||,true",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^    <|;||||S||||,|||||||||;| ||   P|;||    | |;|v| |<< |;|v<<<   |;|v^<<<<<|;||||S||||, false"
  })
  void findChallengeById(String prisonMap, String solutionMap, boolean canEscape) {
    var randomId = UUID.randomUUID();
    var challengeMock = new PrisonChallenge();
    challengeMock.setId(randomId);
    challengeMock.setCanEscape(canEscape);
    challengeMock.setPrison(prisonMap);
    challengeMock.setSolution(solutionMap);
    when(this.prisonChallengeRepository.findById(isA(UUID.class)))
      .thenReturn(Optional.of(challengeMock));

    var resultOpt = this.prisonSolverService.findById(randomId.toString());
    verify(this.prisonChallengeRepository, times(1))
      .findById(any(UUID.class));
    assertTrue(resultOpt.isPresent());
    assertEquals(randomId, resultOpt.get().getId());
    assertEquals(prisonMap, resultOpt.get().getPrison());
    assertEquals(solutionMap, resultOpt.get().getSolution());
  }

  @ParameterizedTest
  @CsvSource({
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^     |;||||S||||,|||||||||;| ||   P|;||    |*|;|v| |<<*|;|v<<<***|;|v^ ****|;||||S||||,true",
    "|||||||||;| ||   P|;||    | |;|v| | < |;|   <   |;| ^    <|;||||S||||,|||||||||;| ||   P|;||    | |;|v| |<< |;|v<<<   |;|v^<<<<<|;||||S||||, false"
  })
  void prisonerChallenge(String prisonMap, String solutionMap, boolean canEscape) {
    var challengeMock = new PrisonChallenge();
    challengeMock.setCanEscape(canEscape);
    challengeMock.setPrison(prisonMap);
    challengeMock.setSolution(solutionMap);
    when(this.prisonChallengeRepository.save(isA(PrisonChallenge.class)))
      .thenReturn(challengeMock);
    PrisonDtoRequest dto = new PrisonDtoRequest();
    dto.getPrison().addAll(List.of(prisonMap.split(AppConstants.ROW_SEPARATOR)));
    var result = this.prisonSolverService.solve(dto);
    verify(this.prisonChallengeRepository, times(1))
      .save(any(PrisonChallenge.class));
    assertNotNull(result);
    assertEquals(canEscape, result.isCanEscape());
    assertEquals(prisonMap, result.getPrison());
    assertEquals(solutionMap, result.getSolution());
  }
}
