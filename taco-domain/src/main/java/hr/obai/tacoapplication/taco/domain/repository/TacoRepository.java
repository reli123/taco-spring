package hr.obai.tacoapplication.taco.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TacoRepository extends JpaRepository<Taco, Long>, JpaSpecificationExecutor<Taco> {
}
