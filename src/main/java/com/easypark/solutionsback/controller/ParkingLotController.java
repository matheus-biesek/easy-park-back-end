package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.AdmAlertRequestDTO;
import com.easypark.solutionsback.service.AdmService;
import com.easypark.solutionsback.service.ParkingBarrierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingBarrierService parkingBarrierService;
    private final AdmService admService;

    @GetMapping("/open")
    public @ResponseBody ResponseEntity<String> openParking(){
        return parkingBarrierService.open();
    }

    @GetMapping("/close")
    public  @ResponseBody ResponseEntity<String> closeParking(){
        return  parkingBarrierService.close();
    }

    @PostMapping("/send-adm-alert")
    public @ResponseBody ResponseEntity<String> sendAlert(@RequestBody AdmAlertRequestDTO body){
        return admService.sendAlert(body);
    }
}
