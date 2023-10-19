package com.example.testingentities.DAO.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Ordre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    long id;
    @ManyToOne
    Action actionOrdre;
    //@Column(nullable=true)

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = true)
    Option option;
}
