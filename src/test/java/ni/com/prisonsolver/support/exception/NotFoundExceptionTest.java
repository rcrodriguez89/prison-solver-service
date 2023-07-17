package ni.com.prisonsolver.support.exception;

import ni.com.prisonsolver.persistence.PrisonChallenge;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundExceptionTest {

  @Test
  void createNotFoundExceptionWithEntityAndIdValue() {
    final String id = UUID.randomUUID().toString();
    NotFoundException nfe = new NotFoundException(PrisonChallenge.class, id);
    assertEquals(String.format("PrisonChallenge with Id [%s] not found", id), nfe.getMessage());
  }

  @Test
  void createNotFoundExceptionWithEntityFieldAndIdValue() {
    final String id = UUID.randomUUID().toString();
    final String field = "Identifier";
    NotFoundException nfe = new NotFoundException(PrisonChallenge.class, field, id);
    assertEquals(String.format("PrisonChallenge with Identifier [%s] not found", id), nfe.getMessage());
  }
}
