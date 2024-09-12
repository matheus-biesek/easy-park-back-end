package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.response.VaganciesReservedResponseDTO;
import com.easypark.solutionsback.service.AdmService;
import com.easypark.solutionsback.service.ParkingBarrierService;
import com.easypark.solutionsback.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/esp32")
@RequiredArgsConstructor
public class Esp32Controller {

    private final VacancyService vacancyService;
    private final AdmService admService;
    private final ParkingBarrierService parkingBarrierService;

    @GetMapping("/vacancies-reserved")
    public @ResponseBody ResponseEntity<List<VaganciesReservedResponseDTO>> statusVacanciesReserved(){
        return this.vacancyService.statusVacanciesReserved();
    }

    @GetMapping("/adm-alert")
    public @ResponseBody ResponseEntity<String> collectAdmAlert(){
        return this.admService.statusAdmAlert();
    }

    @GetMapping("/status-parking-barrier")
    public @ResponseBody ResponseEntity<Boolean> statusParkingBarrier(){
        return this.parkingBarrierService.statusParkingBarrier();
    }
}
