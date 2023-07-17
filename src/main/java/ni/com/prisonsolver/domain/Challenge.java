package ni.com.prisonsolver.domain;

import ni.com.prisonsolver.domain.guard.Guard;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static ni.com.prisonsolver.support.util.AppConstants.ROW_SEPARATOR;

public class Challenge {
  private int rows;

  private int columns;

  private Map<Location, Guard> guards = new LinkedHashMap<>();

  private Location prisonerLocation;

  private Cell[][] solutionMap;

  private Cell[][] prisonMap;

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getColumns() {
    return columns;
  }

  public void setColumns(int columns) {
    this.columns = columns;
  }

  public Map<Location, Guard> getGuards() {
    return guards;
  }

  public Cell[][] getPrisonMap() {
    return prisonMap;
  }

  public void setPrisonMap(Cell[][] prisonMap) {
    this.prisonMap = prisonMap;
  }

  public Cell[][] getSolutionMap() {
    return solutionMap;
  }

  public void setSolutionMap(Cell[][] solutionMap) {
    this.solutionMap = solutionMap;
  }

  public Location getPrisonerLocation() {
    return prisonerLocation;
  }

  public void setPrisonerLocation(Location prisonerLocation) {
    this.prisonerLocation = prisonerLocation;
  }

  public String drawPrisonMap() {
    return Arrays.stream(prisonMap)
      .map(c -> Arrays.stream(c)
        .map(Cell::draw)
        .collect(Collectors.joining()))
      .collect(Collectors.joining(ROW_SEPARATOR));
  }

  public String drawSolutionMap() {
    return Arrays.stream(solutionMap)
      .map(c -> Arrays.stream(c)
        .map(Cell::draw)
        .collect(Collectors.joining()))
      .collect(Collectors.joining(ROW_SEPARATOR));
  }
}
