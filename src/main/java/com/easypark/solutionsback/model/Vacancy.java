package com.easypark.solutionsback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private int position;

    @Column(nullable = false)
    private EnumStatusVacancy status;

    public Vacancy(int position, EnumStatusVacancy status){
        this.position = position;
        this.status = status;
    }
}
