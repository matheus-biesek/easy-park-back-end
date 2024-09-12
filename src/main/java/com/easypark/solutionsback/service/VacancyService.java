package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.request.VacancyRequestDTO;
import com.easypark.solutionsback.dto.response.StatusVacancyResponseDTO;
import com.easypark.solutionsback.dto.response.VaganciesReservedResponseDTO;
import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.model.VacancyReserved;
import com.easypark.solutionsback.repository.VacancyRepository;
import com.easypark.solutionsback.repository.VacancyReservedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyReservedRepository vacancyReservedRepository;

    public ResponseEntity<String> updateStatusVacancy(List<VacancyRequestDTO> body) {
        try {
            for (VacancyRequestDTO vacancyRequest : body) {
                Vacancy vacancyFound = this.vacancyRepository.findByPosition(vacancyRequest.position());
                if (vacancyFound == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Erro! Vaga não encontrada na posição: " + vacancyRequest.position());
                }
                vacancyFound.setStatus(vacancyRequest.status());
                vacancyRepository.save(vacancyFound);
            }
            return ResponseEntity.ok("Todas as vagas foram atualizadas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    public ResponseEntity<String> createVacancy(VacancyRequestDTO body) {
        try {
            if (body == null || body.position() <= 0 || body.status() == null) {
                return ResponseEntity.badRequest().body("Dados inválidos: A posição deve ser maior que 0 e o status não deve ser nulo.");
            }
            Vacancy vacancy = new Vacancy(body.position(), body.status());
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

    public ResponseEntity<String> deleteVacancy(VacancyRequestDTO body) {
        try {
            Vacancy vacancyFound = vacancyRepository.findByPosition(body.position());
            if (vacancyFound == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vaga não encontrada na posição: " + body.position());
            }
            vacancyRepository.delete(vacancyFound);
            return ResponseEntity.ok("Vaga deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao tentar deletar a vaga: " + e.getMessage());
        }
    }

    public ResponseEntity<List<VaganciesReservedResponseDTO>> statusVacanciesReserved() {
        try {
            List<Vacancy> allVacancies = this.vacancyRepository.findAll();
            List<VaganciesReservedResponseDTO> vacanciesReserveds = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (Vacancy vacancy : allVacancies) {
                boolean isReserved = vacancy.getVacancyReserved().stream()
                        .anyMatch(reserved -> reserved.getDateTime().toLocalDate().isEqual(today));
                if (isReserved) {
                    vacanciesReserveds.add(new VaganciesReservedResponseDTO(vacancy.getPosition(), true));
                }
            }
            return new ResponseEntity<>(vacanciesReserveds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> reservedVacancy(VacancyRequestDTO body) {
        try {
            int position = body.position();
            Vacancy vacancyFound = this.vacancyRepository.findByPosition(position);
            if (vacancyFound == null) {
                return new ResponseEntity<>("Vaga não encontrada.", HttpStatus.NOT_FOUND);
            }
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            boolean alreadyReserved = vacancyFound.getVacancyReserved().stream()
                    .anyMatch(reserved -> reserved.getDateTime().toLocalDate().isEqual(tomorrow));
            if (alreadyReserved) {
                return new ResponseEntity<>("Vaga já reservada para amanhã.", HttpStatus.CONFLICT);
            }
            VacancyReserved vacancyReserved = new VacancyReserved();
            vacancyReserved.setVacancy(vacancyFound);
            vacancyReserved.setDateTime(LocalDateTime.now().plusDays(1));
            this.vacancyReservedRepository.save(vacancyReserved);
            return new ResponseEntity<>("Vaga reservada com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao tentar reservar a vaga: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<StatusVacancyResponseDTO>> getAllVacanciesStatus() {
        try {
            List<Vacancy> allVacancies = this.vacancyRepository.findAll();
            List<StatusVacancyResponseDTO> vacancyStatuses = new ArrayList<>();
            for (Vacancy vacancy : allVacancies) {
                vacancyStatuses.add(new StatusVacancyResponseDTO(
                        vacancy.getPosition(),
                        vacancy.getStatus() // Supondo que getStatus retorna EnumStatusVacancy
                ));
            }
            return new ResponseEntity<>(vacancyStatuses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
