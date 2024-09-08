package com.easypark.solutionsback.service;

import com.easypark.solutionsback.enun.EnumGate;
import com.easypark.solutionsback.model.ParkingBarrier;
import com.easypark.solutionsback.repository.ParkingBarrierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingBarrierService {

    private final ParkingBarrierRepository parkingBarrierRepository;

    public ResponseEntity<String> open(){
        EnumGate gate = EnumGate.ONE;
        ParkingBarrier parkingBarrierFound = this.parkingBarrierRepository.findByGate(gate);
        if (parkingBarrierFound.getStatus()){
            new ResponseEntity<>("O portão já está aberto!", HttpStatus.OK);
        }
        parkingBarrierFound.setStatus(true);
        this.parkingBarrierRepository.save(parkingBarrierFound);
        return new ResponseEntity<>("O portão irá abrir!", HttpStatus.OK);
    }

    public ResponseEntity<String> close() {
        EnumGate gate = EnumGate.ONE;
        ParkingBarrier parkingBarrierFound = this.parkingBarrierRepository.findByGate(gate);
        if (!parkingBarrierFound.getStatus()){
            new ResponseEntity<>("O portão já está fechado!", HttpStatus.OK);
        }
        parkingBarrierFound.setStatus(false);
        this.parkingBarrierRepository.save(parkingBarrierFound);
        return new ResponseEntity<>("O portão irá fechar!", HttpStatus.OK);
    }
}
