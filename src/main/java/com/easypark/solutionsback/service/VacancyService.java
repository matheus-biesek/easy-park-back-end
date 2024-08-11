package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.VacancyDTO;
import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    public List<Vacancy> listStatusVacancy() {
        return this.vacancyRepository.findAll();
    }

    public String updateStatusVacancy(List<VacancyDTO> body) {
        try {
            for (VacancyDTO vacancy : body) {
                Vacancy vacancyFound = this.vacancyRepository.findByPosition(vacancy.position());
                if (vacancyFound != null) {
                    vacancyFound.setStatus(vacancy.status());
                    vacancyRepository.save(vacancyFound);
                } else {
                    return "Error: Vacancy not found for position " + vacancy.position();
                }
            }
            return "success";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    public ResponseEntity<String> createVacancy(VacancyDTO body) {
        if (body == null || body.position() <= 0 || body.status() == null) {
            return ResponseEntity.badRequest().body("O valor enviado é inválido");
        }
        try {
            Vacancy vacancy = new Vacancy(body.position(), body.status());
            vacancyRepository.save(vacancy);
            return ResponseEntity.ok("Vaga criada com sucesso!");
        } catch (DataAccessException e) {
            // DataAccessException é uma exceção específica para problemas relacionados ao banco de dados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error: " + e.getMessage());
        } catch (Exception e) {
            // Captura qualquer outra exceção não esperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
