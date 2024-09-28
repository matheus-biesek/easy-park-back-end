package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.PositionVacancyRequestDTO;
import com.easypark.solutionsback.dto.request.VacancyRequestDTO;
import com.easypark.solutionsback.dto.response.StatusVacancyResponseDTO;
import com.easypark.solutionsback.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping("/status-all-vacancy")
    public @ResponseBody ResponseEntity<List<StatusVacancyResponseDTO>> getStatusAllVacancy() {
        return this.vacancyService.getAllVacanciesStatus();
    }

    @PostMapping("/create-vacancy")
    public @ResponseBody ResponseEntity<String> createVacancy(@Valid @RequestBody PositionVacancyRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return this.vacancyService.createVacancy(body);
    }

    @DeleteMapping("/delete-vacancy")
    public @ResponseBody ResponseEntity<String> deleteVacancy(@Valid @RequestBody PositionVacancyRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return this.vacancyService.deleteVacancy(body);
    }

    @PutMapping("/update-status-vacancy")
    public @ResponseBody ResponseEntity<String> updateStatusVacancy(@Valid @RequestBody List<VacancyRequestDTO> body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return this.vacancyService.updateStatusVacancy(body);
    }
}
