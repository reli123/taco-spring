package hr.obai.tacoapplication.ingredient.domain.repository;

import hr.obai.commons.repository.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Where(clause = "FLAG_DELETED is NULL")
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Float price;
    @Enumerated(EnumType.STRING)
    private IngredientType type;
}
