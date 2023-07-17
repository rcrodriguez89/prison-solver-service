package ni.com.prisonsolver.support.exception;

public class NotFoundException extends RuntimeException {
  private static final String ENTITY_WITH_FIELD_NOT_FOUND_MESSAGE = "%s with %s [%s] not found";

  private static final String FIELD_ID = "Id";

  public NotFoundException(Class entity, Object id) {
    super(String.format(ENTITY_WITH_FIELD_NOT_FOUND_MESSAGE, entity.getSimpleName(), FIELD_ID, id));
  }

  public NotFoundException(Class entity, String field, Object value) {
    super(String.format(ENTITY_WITH_FIELD_NOT_FOUND_MESSAGE, entity.getSimpleName(), field, value));
  }
}
