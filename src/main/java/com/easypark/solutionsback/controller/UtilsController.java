package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.enun.EnumGate;
import com.easypark.solutionsback.model.MessageAdm;
import com.easypark.solutionsback.model.ParkingBarrier;
import com.easypark.solutionsback.repository.MessageAdmRepository;
import com.easypark.solutionsback.repository.ParkingBarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/utils")
@RequiredArgsConstructor
public class UtilsController {

    private final MessageAdmRepository messageAdmRepository;
    private final ParkingBarrierRepository parkingBarrierRepository;

    @GetMapping("/create-message-barrier")
    public @ResponseBody String createMessageAndBarrier(){
        MessageAdm messageAdm = new MessageAdm();
        messageAdm.setMessage("Primeiro alerta!");
        this.messageAdmRepository.save(messageAdm);
        ParkingBarrier parkingBarrier = new ParkingBarrier();
        parkingBarrier.setGate(EnumGate.ONE);
        parkingBarrier.setStatus(false);
        this.parkingBarrierRepository.save(parkingBarrier);
        return "Entidades mensagem e cancela criada com sucesso!";
    }
}
