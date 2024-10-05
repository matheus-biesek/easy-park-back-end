package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.PositionVacancyRequestDTO;
import com.easypark.solutionsback.dto.request.VacancyRequestDTO;
import com.easypark.solutionsback.dto.response.StatusVacancyResponseDTO;
import com.easypark.solutionsback.service.VacancyService;
import com.easypark.solutionsback.utils.ValidationErrorHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping("/save-vacancy-history")
    public @ResponseBody ResponseEntity<String> saveVacancyHistory(@Valid @RequestBody VacancyRequestDTO body, BindingResult result) {
        String errors = ValidationErrorHandler.getErrorMessages(result);
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }
        return this.vacancyService.saveHistory(body.getPosition(), body.getStatus());
    }

    @GetMapping("/get-vacancies-status")
    public @ResponseBody ResponseEntity<List<StatusVacancyResponseDTO>> getVacanciesStatus() {
        return this.vacancyService.getVacanciesStatus();
    }

    @PostMapping("/create-vacancy")
    public @ResponseBody ResponseEntity<String> createVacancy(@Valid @RequestBody PositionVacancyRequestDTO body, BindingResult result) {
        String errors = ValidationErrorHandler.getErrorMessages(result);
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }
        return this.vacancyService.createVacancy(body.getPosition());
    }

    @DeleteMapping("/delete-vacancy")
    public @ResponseBody ResponseEntity<String> deleteVacancy(@Valid @RequestBody PositionVacancyRequestDTO body, BindingResult result) {
        String errors = ValidationErrorHandler.getErrorMessages(result);
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }
        return this.vacancyService.deleteVacancy(body.getPosition());
    }

    @PutMapping("/change-vacancies-status")
    public @ResponseBody ResponseEntity<String> changeVacanciesStatus(@Valid @RequestBody List<VacancyRequestDTO> body, BindingResult result) {
        String errors = ValidationErrorHandler.getErrorMessages(result);
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }
        return this.vacancyService.changeVacanciesStatus(body);
    }
}
