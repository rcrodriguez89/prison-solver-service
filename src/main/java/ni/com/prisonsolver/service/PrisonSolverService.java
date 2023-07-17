package ni.com.prisonsolver.service;

import ni.com.prisonsolver.dto.PrisonDtoRequest;
import ni.com.prisonsolver.persistence.ChallengeStats;
import ni.com.prisonsolver.persistence.PrisonChallenge;
import ni.com.prisonsolver.repository.PrisonChallengeRepository;
import ni.com.prisonsolver.support.helper.PathwayFinder;
import ni.com.prisonsolver.support.helper.PrisonMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PrisonSolverService {
  private final PrisonMapper prisonMapper;

  private final PathwayFinder pathwayFinder;

  private final PrisonChallengeRepository repository;

  public PrisonSolverService(PrisonMapper prisonMapper, PathwayFinder pathwayFinder,
    PrisonChallengeRepository repository) {
    this.prisonMapper = prisonMapper;
    this.pathwayFinder = pathwayFinder;
    this.repository = repository;
  }

  public Optional<PrisonChallenge> findById(String id) {
    return this.repository.findById(UUID.fromString(id));
  }

  public ChallengeStats getChallengeStats() {
    return this.repository.getChallengeStats();
  }

  public PrisonChallenge solve(PrisonDtoRequest prisonDtoRequest) {
    var challenge = this.prisonMapper.transform(prisonDtoRequest);
    boolean canEscape = this.pathwayFinder.calculatePathWay(challenge);
    var prisonChallenge = new PrisonChallenge();
    prisonChallenge.setCanEscape(canEscape);
    prisonChallenge.setPrison(challenge.drawPrisonMap());
    prisonChallenge.setSolution(challenge.drawSolutionMap());
    this.repository.save(prisonChallenge);
    return prisonChallenge;
  }
}
