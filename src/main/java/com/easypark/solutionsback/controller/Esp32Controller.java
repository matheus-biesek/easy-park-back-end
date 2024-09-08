package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.response.VaganciesReservedResponseDTO;
import com.easypark.solutionsback.service.Esp32Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/esp32")
@RequiredArgsConstructor
public class Esp32Controller {

    private final Esp32Service esp32Service;

    @GetMapping("/vacancies-reserved")
    public @ResponseBody List<VaganciesReservedResponseDTO> esp32StatusVacanciesReserved(){
        return this.esp32Service.statusVacanciesReserved();
    }

    @GetMapping("/adm-alert")
    public @ResponseBody ResponseEntity<String> collectAdmAlert(){
        return this.esp32Service.statusAdmAlert();
    }

    @GetMapping("/status-parking-barrier")
    public @ResponseBody ResponseEntity<Boolean> statusParkingBarrier(){
        return this.esp32Service.statusParkingBarrier();
    }
}
