package hr.obai.tacoapplication.taco.domain.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
