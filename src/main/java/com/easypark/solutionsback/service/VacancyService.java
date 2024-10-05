package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.request.VacancyRequestDTO;
import com.easypark.solutionsback.dto.response.StatusVacancyResponseDTO;
import com.easypark.solutionsback.enun.EnumStatusVacancy;
import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.model.VacancyHistory;
import com.easypark.solutionsback.repository.VacancyHistoryRepository;
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
    private final VacancyHistoryRepository vacancyHistoryRepository;

    public ResponseEntity<String> saveHistory(int position, EnumStatusVacancy status){
        try {
            Vacancy vacancyFound = this.vacancyRepository.findByPosition(position);
            if (vacancyFound == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vaga não encontrada para a posição: " + position);
            }
            VacancyHistory history = new VacancyHistory(vacancyFound, status);
            this.vacancyHistoryRepository.save(history);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Histórico salvo com sucesso!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    public ResponseEntity<String> changeVacanciesStatus(List<VacancyRequestDTO> body) {
        try {
            for (VacancyRequestDTO vacancyRequest : body) {
                Vacancy vacancyFound = this.vacancyRepository.findByPosition(vacancyRequest.getPosition());
                if (vacancyFound == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Erro! Vaga não encontrada na posição: " + vacancyRequest.getPosition());
                }
                vacancyFound.setStatus(vacancyRequest.getStatus());
                this.vacancyRepository.save(vacancyFound);
            }
            return ResponseEntity.ok("Todas as vagas foram atualizadas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    public ResponseEntity<String> createVacancy(int position) {
        try {
            Vacancy vacancyFound = this.vacancyRepository.findByPosition(position);
            if (vacancyFound != null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Erro! Está vaga já existe!");
            }
            Vacancy vacancy = new Vacancy(position, EnumStatusVacancy.available);
            this.vacancyRepository.save(vacancy);
            return ResponseEntity.ok("Vaga criada com sucesso.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro no banco de dados: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteVacancy(int position) {
        try {
            Vacancy vacancyFound = vacancyRepository.findByPosition(position);
            if (vacancyFound == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vaga não encontrada na posição: " + position);
            }
            vacancyRepository.delete(vacancyFound);
            return ResponseEntity.ok("Vaga deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao tentar deletar a vaga: " + e.getMessage());
        }
    }

    public ResponseEntity<List<StatusVacancyResponseDTO>> getVacanciesStatus() {
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
