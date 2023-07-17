package ni.com.prisonsolver.support.hibernate;

import io.hypersistence.utils.hibernate.type.util.ClassImportIntegrator;
import ni.com.prisonsolver.persistence.ChallengeStats;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.List;

public class ClassImportIntegratorProvider implements IntegratorProvider {

  @Override
  public List<Integrator> getIntegrators() {
    return List.of(new ClassImportIntegrator(List.of(ChallengeStats.class)));
  }
}
