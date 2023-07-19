package ni.com.prisonsolver.rest;

import jakarta.validation.constraints.NotNull;
import ni.com.prisonsolver.dto.PrisonDtoRequest;
import ni.com.prisonsolver.persistence.ChallengeStats;
import ni.com.prisonsolver.persistence.PrisonChallenge;
import ni.com.prisonsolver.service.PrisonSolverService;
import ni.com.prisonsolver.support.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "prisoner", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PrisonRestResource {

  private final PrisonSolverService service;

  public PrisonRestResource(PrisonSolverService service) {
    this.service = service;
  }

  @GetMapping(value = "stats", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ChallengeStats getChallengeStats() {
    return this.service.getChallengeStats();
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public PrisonChallenge findById(@NotNull @PathVariable String id) {
    return this.service.findById(id)
      .orElseThrow(() -> new NotFoundException(PrisonChallenge.class, id));
  }

  @PostMapping
  public ResponseEntity<?> prison(@Validated @NotNull @RequestBody PrisonDtoRequest prisonDtoRequest) {
    var newEntity = this.service.solve(prisonDtoRequest);
    var location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(newEntity.getId())
      .toUri();
    var headers = new HttpHeaders();
    headers.setLocation(location);
    return new ResponseEntity<>(headers, newEntity.isCanEscape() ? HttpStatus.OK : HttpStatus.FORBIDDEN);
  }
}
