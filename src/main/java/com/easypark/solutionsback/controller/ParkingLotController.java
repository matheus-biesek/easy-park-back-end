package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.dto.request.ChangeGateStatusRequestDTO;
import com.easypark.solutionsback.dto.request.GateRequestDTO;
import com.easypark.solutionsback.dto.request.MessageAdmRequestDTO;
import com.easypark.solutionsback.service.ParkingLotService;
import com.easypark.solutionsback.utils.ValidationErrorHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parking-lot")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping("/get-message-adm")
    public @ResponseBody ResponseEntity<String> collectAdmAlert(){
        return this.parkingLotService.getMessageAdm();
    }

    @PostMapping("/get-gate-status")
    public @ResponseBody ResponseEntity<?> statusParkingBarrier(@Valid @RequestBody GateRequestDTO body, BindingResult result) {
        String errors = ValidationErrorHandler.getErrorMessages(result);
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            boolean status = parkingLotService.getGateStatus(body.getGate());
            return ResponseEntity.ok(status);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/change-gate-status")
    public @ResponseBody ResponseEntity<String> changeGateStatus(@Valid @RequestBody ChangeGateStatusRequestDTO body, BindingResult result){
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return parkingLotService.changeGateStatus(body.getGate(), body.isStatus());
    }

    @PutMapping("/send-message-adm")
    public @ResponseBody ResponseEntity<String> sendMessageAdm(@Valid @RequestBody MessageAdmRequestDTO body, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        return parkingLotService.sendMessageAdm(body.getMessage());
    }
}
