package com.easypark.solutionsback.service;

import com.easypark.solutionsback.enun.EnumGate;
import com.easypark.solutionsback.model.ParkingBarrier;
import com.easypark.solutionsback.repository.ParkingBarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingBarrierService {

    private final ParkingBarrierRepository parkingBarrierRepository;

    public ResponseEntity<String> open() {
        try {
            EnumGate gate = EnumGate.ONE;
            Optional<ParkingBarrier> optionalParkingBarrier = parkingBarrierRepository.findByGate(gate);

            if (optionalParkingBarrier.isEmpty()) {
                return new ResponseEntity<>("Portão não encontrado.", HttpStatus.NOT_FOUND);
            }

            ParkingBarrier parkingBarrierFound = optionalParkingBarrier.get();

            if (parkingBarrierFound.getStatus()) {
                return new ResponseEntity<>("O portão já está aberto!", HttpStatus.OK);
            }

            parkingBarrierFound.setStatus(true);
            parkingBarrierRepository.save(parkingBarrierFound);
            return new ResponseEntity<>("O portão irá abrir!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao abrir o portão: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> close() {
        try {
            EnumGate gate = EnumGate.ONE;
            Optional<ParkingBarrier> optionalParkingBarrier = parkingBarrierRepository.findByGate(gate);

            if (optionalParkingBarrier.isEmpty()) {
                return new ResponseEntity<>("Portão não encontrado.", HttpStatus.NOT_FOUND);
            }

            ParkingBarrier parkingBarrierFound = optionalParkingBarrier.get();

            if (!parkingBarrierFound.getStatus()) {
                return new ResponseEntity<>("O portão já está fechado!", HttpStatus.OK);
            }

            parkingBarrierFound.setStatus(false);
            parkingBarrierRepository.save(parkingBarrierFound);
            return new ResponseEntity<>("O portão irá fechar!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao fechar o portão: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Boolean> statusParkingBarrier() {
        try {
            Optional<ParkingBarrier> optionalParkingBarrier = parkingBarrierRepository.findByGate(EnumGate.ONE);

            if (optionalParkingBarrier.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            boolean status = optionalParkingBarrier.get().getStatus();
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
