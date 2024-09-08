package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.AdmAlertRequestDTO;
import com.easypark.solutionsback.dto.response.AdmAlertResponseDTO;
import com.easypark.solutionsback.service.AdmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmController {

    private final AdmService admService;

    @PostMapping("/send-alert")
    public @ResponseBody ResponseEntity<AdmAlertResponseDTO> sendAlert(@RequestBody AdmAlertRequestDTO body){
        return admService.sendAlert(body);
    }
}
