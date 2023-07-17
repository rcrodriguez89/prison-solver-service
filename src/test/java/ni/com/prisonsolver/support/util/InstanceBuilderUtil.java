package ni.com.prisonsolver.support.util;

import ni.com.prisonsolver.domain.Challenge;
import ni.com.prisonsolver.dto.PrisonDtoRequest;
import ni.com.prisonsolver.factory.CellFactory;
import ni.com.prisonsolver.factory.GuardFactory;
import ni.com.prisonsolver.support.helper.PrisonMapper;

import java.util.List;

public final class InstanceBuilderUtil {

  private static GuardFactory guardFactory = new GuardFactory();

  private static CellFactory cellFactory = new CellFactory(guardFactory);

  private static PrisonMapper prisonMapper = new PrisonMapper(cellFactory);

  public InstanceBuilderUtil() {}

  public static Challenge buildChallengeInstance(String prisonString) {
    List<String> prison = List.of(prisonString.split(AppConstants.ROW_SEPARATOR));
    PrisonDtoRequest prisonDtoRequest = new PrisonDtoRequest();
    prisonDtoRequest.getPrison().addAll(prison);
    return prisonMapper.transform(prisonDtoRequest);
  }
}
