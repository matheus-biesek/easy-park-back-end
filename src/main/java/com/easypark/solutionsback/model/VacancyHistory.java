package com.easypark.solutionsback.model;

import com.easypark.solutionsback.enun.EnumStatusVacancy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VacancyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private EnumStatusVacancy status;

    @ManyToOne
    @JoinColumn(name = "vacancy_uuid", nullable = false)
    private Vacancy vacancy;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }

    public VacancyHistory(Vacancy vacancy, EnumStatusVacancy status){
        this.vacancy = vacancy;
        this.status = status;
    }
}
