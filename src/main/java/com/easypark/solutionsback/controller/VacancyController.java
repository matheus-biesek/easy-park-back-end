package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.VacancyRequestDTO;
import com.easypark.solutionsback.dto.response.StatusVacancyResponseDTO;
import com.easypark.solutionsback.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api-vacancy")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping("/status-all-vacancy")
    public @ResponseBody ResponseEntity<List<StatusVacancyResponseDTO>> getStatusAllVacancy() {
        return this.vacancyService.getAllVacanciesStatus();
    }

    @PostMapping("/vacancy-update")
    public @ResponseBody ResponseEntity<String> updateVacancy(@RequestBody List<VacancyRequestDTO> body) {
        return this.vacancyService.updateStatusVacancy(body);
    }

    @PostMapping("/reserved-vacancy")
    public @ResponseBody ResponseEntity<String> reservedVacancy(@RequestBody VacancyRequestDTO body){
        return this.vacancyService.reservedVacancy(body);
    }

    @PostMapping("/create-vacancy")
    public @ResponseBody ResponseEntity<String> createVacancy(@RequestBody VacancyRequestDTO body) {
        return this.vacancyService.createVacancy(body);
    }

    @PostMapping("/delete-vacancy")
    public @ResponseBody ResponseEntity<String> deleteVacancy(@RequestBody VacancyRequestDTO body) {
        return this.vacancyService.deleteVacancy(body);
    }
}


