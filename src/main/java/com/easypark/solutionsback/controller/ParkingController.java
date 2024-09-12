package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.service.ParkingBarrierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/parking-barrier")
@RequiredArgsConstructor
public class ParkingBarrierController {

    private final ParkingBarrierService parkingBarrierService;

    @GetMapping("/open")
    public @ResponseBody ResponseEntity<String> openParking(){
        return parkingBarrierService.open();
    }

    @GetMapping("/close")
    public  @ResponseBody ResponseEntity<String> closeParking(){
        return  parkingBarrierService.close();
    }
}
