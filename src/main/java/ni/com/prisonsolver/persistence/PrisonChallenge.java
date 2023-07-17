package ni.com.prisonsolver.persistence;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ni.com.prisonsolver.support.util.AppConstants.ROW_SEPARATOR;

@Entity
@Table(name = "challenge")
public class PrisonChallenge implements Serializable {
  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id")
  private UUID id;

  @NotNull
  @Column(name = "can_escape")
  private boolean canEscape;

  @NotNull
  @Column(name = "prison", length = 1000)
  private String prison;

  @NotNull
  @Column(name = "solution", length = 1000)
  private String solution;

  @NotNull
  @PastOrPresent
  @Column(name = "created_on", updatable = false)
  private LocalDateTime createdOn;

  @PrePersist
  public void onPrePersist() {
    this.id = UUID.randomUUID();
    this.createdOn = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public boolean isCanEscape() {
    return canEscape;
  }

  public void setCanEscape(boolean canEscape) {
    this.canEscape = canEscape;
  }

  public String getPrison() {
    return prison;
  }

  public void setPrison(String prison) {
    this.prison = prison;
  }

  public String getSolution() {
    return solution;
  }

  public void setSolution(String solution) {
    this.solution = solution;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  @JsonValue
  public Map<String, Object> asMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("canEscape", this.canEscape);
    map.put("challenge", List.of(this.prison.split(ROW_SEPARATOR)));
    map.put("solution", List.of(this.solution.split(ROW_SEPARATOR)));
    return map;
  }
}
