package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.MessageAdmRequestDTO;
import com.easypark.solutionsback.service.ParkingLotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parking-lot")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping("/collect-message-adm")
    public @ResponseBody ResponseEntity<String> collectAdmAlert(){
        return this.parkingLotService.statusAdmAlert();
    }

    @GetMapping("/status-parking-barrier")
    public @ResponseBody ResponseEntity<Boolean> statusParkingBarrier(){
        return this.parkingLotService.statusParkingBarrier();
    }

    @PutMapping("/open-parking-barrier")
    public @ResponseBody ResponseEntity<String> openParking(){
        return parkingLotService.open();
    }

    @PutMapping("/close-parking-barrier")
    public  @ResponseBody ResponseEntity<String> closeParking(){
        return  parkingLotService.close();
    }

    @PutMapping("/send-adm-alert")
    public @ResponseBody ResponseEntity<String> sendAlert(@Valid @RequestBody MessageAdmRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return parkingLotService.sendAlert(body);
    }
}
