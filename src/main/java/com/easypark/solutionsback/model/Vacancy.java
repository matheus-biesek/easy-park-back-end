package com.easypark.solutionsback.model;

import com.easypark.solutionsback.enun.EnumStatusVacancy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
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

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL)
    private List<VacancyReserved> vacancyReserved = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private int position;

    @Column(nullable = false)
    private EnumStatusVacancy status;

    public Vacancy(int position, EnumStatusVacancy status){
        this.position = position;
        this.status = status;
    }

    public String calculateStatus() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Verifica se a vaga está reservada para amanhã
        boolean isReservedForTomorrow = this.vacancyReserved.stream()
                .anyMatch(reserved -> reserved.getDateTime().toLocalDate().isEqual(tomorrow));

        // Verifica o status com base nas reservas
        if (isReservedForTomorrow) {
            return EnumStatusVacancy.reserved.name();  // Retorna o nome do enum como string
        } else if (this.getStatus().equals(EnumStatusVacancy.busy)) {  // Corrigida a comparação com o enum
            return EnumStatusVacancy.busy.name();  // Retorna "busy" como string
        } else {
            return EnumStatusVacancy.available.name();  // Retorna "available" como string
        }
    }


}
