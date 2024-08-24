package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.request.VacancyRequestDTO;
import com.easypark.solutionsback.model.EnumStatusVacancy;
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

    public String updateStatusVacancy(List<VacancyRequestDTO> body) {
        try {
            for (VacancyRequestDTO vacancy : body) {
                Vacancy vacancyFound = this.vacancyRepository.findByPosition(vacancy.position());

                if (vacancyFound == null) {
                    return "Erro! A vaga não foi encontrada, posição: " + vacancy.position();
                }
                if (vacancyFound.getStatus() == EnumStatusVacancy.reserved) {
                    return "Esta vaga já está reservada";
                }
                vacancyFound.setStatus(vacancy.status());
                vacancyRepository.save(vacancyFound);
            }
            return "Vaga reservada com sucesso!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public ResponseEntity<String> createVacancy(VacancyRequestDTO body) {
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

    public ResponseEntity<String> deleteVacancy(VacancyRequestDTO body) {
        try {
            Vacancy vacancyFound = vacancyRepository.findByPosition(body.position());
            if (vacancyFound == null) {
                // Se a vaga não for encontrada, retornamos uma resposta 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vaga não encontrada para a posição: " + body.position());
            }
            vacancyRepository.delete(vacancyFound);
            // Se a vaga foi deletada com sucesso, retornamos uma resposta 200
            return ResponseEntity.ok("Vaga deletada com sucesso!");
        } catch (Exception e) {
            // Em caso de erro, retornamos uma resposta 500 com detalhes da exceção
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao tentar deletar a vaga: " + e.getMessage());
        }
    }
}
