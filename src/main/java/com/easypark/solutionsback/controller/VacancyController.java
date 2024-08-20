package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.VacancyDTO;
import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.service.VacancyService;
import com.easypark.solutionsback.test.ApiResponse;
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
    public @ResponseBody List<Vacancy> getStatusAllVacancy() {
        return this.vacancyService.listStatusVacancy();
    }

    @PostMapping("/vacancy-update")
    public @ResponseBody String updateVacancy(@RequestBody List<VacancyDTO> body) {
        return this.vacancyService.updateStatusVacancy(body);
    }

    @PostMapping("/create-vacancy")
    public @ResponseBody ResponseEntity<String> createVacancy(@RequestBody VacancyDTO body) {
        return this.vacancyService.createVacancy(body);
    }

    @PostMapping("/delete-vacancy")
    public @ResponseBody ResponseEntity<String> deleteVacancy(@RequestBody VacancyDTO body) {
        return this.vacancyService.deleteVacancy(body);
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> test() {
        ApiResponse response = new ApiResponse("Funcionando!");
        return ResponseEntity.ok(response);
    }

}
