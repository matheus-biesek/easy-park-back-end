package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.request.PositionVacancyRequestDTO;
import com.easypark.solutionsback.dto.request.VacancyRequestDTO;
import com.easypark.solutionsback.dto.response.StatusVacancyResponseDTO;
import com.easypark.solutionsback.enun.EnumStatusVacancy;
import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    public ResponseEntity<String> updateStatusVacancy(List<VacancyRequestDTO> body) {
        try {
            for (VacancyRequestDTO vacancyRequest : body) {
                Vacancy vacancyFound = this.vacancyRepository.findByPosition(vacancyRequest.getPosition());
                if (vacancyFound == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Erro! Vaga não encontrada na posição: " + vacancyRequest.getPosition());
                }
                vacancyFound.setStatus(vacancyRequest.getStatus());
                vacancyRepository.save(vacancyFound);
            }
            return ResponseEntity.ok("Todas as vagas foram atualizadas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    public ResponseEntity<String> createVacancy(PositionVacancyRequestDTO body) {
        try {
            Vacancy vacancyFound = this.vacancyRepository.findByPosition(body.getPosition());
            if (vacancyFound != null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Erro! Está vaga já existe!");
            }
            Vacancy vacancy = new Vacancy(body.getPosition(), EnumStatusVacancy.available);
            vacancyRepository.save(vacancy);
            return ResponseEntity.ok("Vaga criada com sucesso.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no banco de dados: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteVacancy(PositionVacancyRequestDTO body) {
        try {
            Vacancy vacancyFound = vacancyRepository.findByPosition(body.getPosition());
            if (vacancyFound == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vaga não encontrada na posição: " + body.getPosition());
            }
            vacancyRepository.delete(vacancyFound);
            return ResponseEntity.ok("Vaga deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao tentar deletar a vaga: " + e.getMessage());
        }
    }

    public ResponseEntity<List<StatusVacancyResponseDTO>> getAllVacanciesStatus() {
        try {
            List<Vacancy> allVacancies = this.vacancyRepository.findAll();
            List<StatusVacancyResponseDTO> vacancyStatuses = new ArrayList<>();
            for (Vacancy vacancy : allVacancies) {
                vacancyStatuses.add(new StatusVacancyResponseDTO(
                        vacancy.getPosition(),
                        vacancy.getStatus()
                ));
            }
            return new ResponseEntity<>(vacancyStatuses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
