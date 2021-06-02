package hr.obai.commons.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements IdentifiableEntity {

  @CreationTimestamp
  @Column(name = "CREATED_DATE")
  private LocalDateTime createdDate;

  @Column(name = "CREATED_BY")
  private String createdBy;

  @Column(name = "MODIFIED_DATE")
  private LocalDateTime modifiedDate;

  @Column(name = "MODIFIED_BY")
  private String modifiedBy;

  @Column(name = "FLAG_DELETED")
  private String flagDeleted;
}
