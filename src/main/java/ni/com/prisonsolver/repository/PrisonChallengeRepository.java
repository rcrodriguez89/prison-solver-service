package ni.com.prisonsolver.repository;

import ni.com.prisonsolver.persistence.ChallengeStats;
import ni.com.prisonsolver.persistence.PrisonChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrisonChallengeRepository extends JpaRepository<PrisonChallenge, UUID> {

  @Query("""
    SELECT new ChallengeStats(
      CAST(COALESCE(SUM(CASE WHEN pc.canEscape = true THEN 1.0 ELSE 0.0 END), 0) AS INTEGER)
      , CAST(COALESCE(SUM(CASE WHEN pc.canEscape = false THEN 1 ELSE 0.0 END), 0) AS INTEGER))
    FROM PrisonChallenge pc
  """)
  ChallengeStats getChallengeStats();
}
