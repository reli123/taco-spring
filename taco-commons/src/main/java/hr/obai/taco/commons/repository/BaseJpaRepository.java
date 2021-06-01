package hr.obai.taco.commons.repository;

import hr.obai.taco.commons.repository.entity.IdentifiableEntity;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface BaseJpaRepository<ENTITY extends IdentifiableEntity>
    extends JpaRepository<ENTITY, Long>, JpaSpecificationExecutor<ENTITY> {
  /**
   * Soft delete
   *
   * @param ids
   */
  @Transactional
  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Query("update #{#entityName} e set e.flagDeleted='1' where id in (:ids)")
  void deleteByIdIn(@Param("ids") List<Long> ids);
}
