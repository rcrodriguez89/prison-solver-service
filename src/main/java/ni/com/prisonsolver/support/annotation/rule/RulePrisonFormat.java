package ni.com.prisonsolver.support.annotation.rule;

import ni.com.prisonsolver.support.annotation.BeanValidationMessagesKeys;

import java.util.List;

public interface RulePrisonFormat {

  List<BeanValidationMessagesKeys> verify(final List<String> prison);
}
