package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.response.VaganciesReservedResponseDTO;
import com.easypark.solutionsback.enun.EnumGate;
import com.easypark.solutionsback.model.Admin;
import com.easypark.solutionsback.enun.EnumStatusVacancy;
import com.easypark.solutionsback.model.ParkingBarrier;
import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.repository.AdmRepository;
import com.easypark.solutionsback.repository.ParkingBarrierRepository;
import com.easypark.solutionsback.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Esp32Service {

    private final VacancyRepository vacancyRepository;
    private final AdmRepository adminRepository;
    private final ParkingBarrierRepository parkingBarrierRepository;

    public List<VaganciesReservedResponseDTO> statusVacanciesReserved() {
        List<Vacancy> allVacancies = vacancyRepository.findAll();
        List<VaganciesReservedResponseDTO> vacanciesReserveds = null;
        for (Vacancy vacancy : allVacancies) {
            if (vacancy.getStatus() == EnumStatusVacancy.reserved) {
                vacanciesReserveds.add(new VaganciesReservedResponseDTO(vacancy.getPosition(), true));
            }
        }
        return vacanciesReserveds;
    }

    public ResponseEntity<String> statusAdmAlert() {
        List<Admin> admins = adminRepository.findAll();
        String admAlert = admins.get(0).getAdmAlert();
        if (admAlert.isEmpty() || admAlert.isBlank()){
            return new ResponseEntity<>("Bem vindo!", HttpStatus.OK);
        }
        return new ResponseEntity<>(admAlert, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> statusParkingBarrier(){
        ParkingBarrier parkingBarrierFund = this.parkingBarrierRepository.findByGate(EnumGate.ONE);
        boolean status = parkingBarrierFund.getStatus();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
