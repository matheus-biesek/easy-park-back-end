package com.easypark.solutionsback.service;

import com.easypark.solutionsback.enun.EnumGate;
import com.easypark.solutionsback.model.MessageAdm;
import com.easypark.solutionsback.model.ParkingBarrier;
import com.easypark.solutionsback.repository.MessageAdmRepository;
import com.easypark.solutionsback.repository.ParkingBarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingBarrierRepository parkingBarrierRepository;
    private final MessageAdmRepository messageAdmRepository;

    public ResponseEntity<String> changeGateStatus(EnumGate gate, boolean status) {
        try {
            Optional<ParkingBarrier> optionalParkingBarrier = this.parkingBarrierRepository.findByGate(gate);
            if (optionalParkingBarrier.isEmpty()) {
                return new ResponseEntity<>("Portão não encontrado.", HttpStatus.NOT_FOUND);
            }
            ParkingBarrier parkingBarrierFound = optionalParkingBarrier.get();
            if (parkingBarrierFound.getStatus() == status) {
                String statusMessage = status ? "O portão já está aberto!" : "O portão já está fechado!";
                return new ResponseEntity<>(statusMessage, HttpStatus.OK);
            }
            parkingBarrierFound.setStatus(status);
            parkingBarrierRepository.save(parkingBarrierFound);
            String successMessage = status ? "O portão irá abrir!" : "O portão irá fechar!";
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao mudar o status do portão: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean getGateStatus(EnumGate gate) {
        Optional<ParkingBarrier> optionalParkingBarrier = parkingBarrierRepository.findByGate(gate);
        if (optionalParkingBarrier.isEmpty()) {
            throw new RuntimeException("Portão não encontrado.");
        }
        return optionalParkingBarrier.get().getStatus();
    }

    public ResponseEntity<String> sendMessageAdm(String message) {
        try {
            Optional<MessageAdm> optionalMessageAdm = messageAdmRepository.findById(1L);
            if (optionalMessageAdm.isEmpty()) {
                return new ResponseEntity<>("Não foi encontrado um administrador no banco de dados.", HttpStatus.NOT_FOUND);
            }
            MessageAdm messageAdm = optionalMessageAdm.get();
            messageAdm.setMessage(message);
            messageAdmRepository.save(messageAdm);
            return new ResponseEntity<>("Alerta enviado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao enviar o alerta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getMessageAdm() {
        try {
            Optional<MessageAdm> optionalMessageAdm = messageAdmRepository.findById(1L);
            if (optionalMessageAdm.isEmpty()) {
                return new ResponseEntity<>("A mensagem não foi encontrada!", HttpStatus.OK);
            }
            String messageAdm = optionalMessageAdm.get().getMessage();
            if (messageAdm.isBlank()) {
                return new ResponseEntity<>("Não há mensagens!", HttpStatus.OK);
            }
            return new ResponseEntity<>(messageAdm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao verificar o status do alerta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
