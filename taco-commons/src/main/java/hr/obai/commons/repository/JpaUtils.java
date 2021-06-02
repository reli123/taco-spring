package hr.obai.commons.repository;

import hr.obai.commons.repository.entity.IdentifiableEntity;

import java.text.MessageFormat;
import java.util.Arrays;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hibernate.Session;
import org.hibernate.annotations.FilterDef;
import org.springframework.data.jpa.repository.JpaContext;

/** JPA-related utilities. */
@CommonsLog
@UtilityClass
@Slf4j
public final class JpaUtils {
  public static <ENTITY extends IdentifiableEntity> void enableHibernateFiltersOnEntity(
      @NonNull JpaContext jpaContext, @NonNull Class<ENTITY> entityClass) {
    val filterDefs = entityClass.getAnnotationsByType(FilterDef.class);
    if (filterDefs.length > 0) {
      val session = getSession(jpaContext, entityClass);
      Arrays.stream(filterDefs).map(FilterDef::name).forEach(session::enableFilter);
    } else {
      val message = MessageFormat.format("No {0} defined in {1}", FilterDef.class, entityClass);
      log.warn(message);
    }
  }

  public static <ENTITY extends IdentifiableEntity> Session getSession(
      @NonNull JpaContext jpaContext, @NonNull Class<ENTITY> entityClass) {
    return (Session) jpaContext.getEntityManagerByManagedType(entityClass).getDelegate();
  }
}
