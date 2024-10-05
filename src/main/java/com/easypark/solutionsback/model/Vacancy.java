package com.easypark.solutionsback.model;

import com.easypark.solutionsback.enun.EnumStatusVacancy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VacancyHistory> history = new ArrayList<>();

    public Vacancy(int position, EnumStatusVacancy status){
        this.position = position;
        this.status = status;
    }
}
