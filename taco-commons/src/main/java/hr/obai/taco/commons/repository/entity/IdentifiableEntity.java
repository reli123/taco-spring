package hr.obai.taco.commons.repository.entity;

/** Contract for an identifiable entity. */
public interface IdentifiableEntity {
  Long getId();

  void setId(Long id);
}
