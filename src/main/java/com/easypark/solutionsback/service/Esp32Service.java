package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.response.VaganciesReservedResponseDTO;
import com.easypark.solutionsback.model.Admin;
import com.easypark.solutionsback.model.EnumStatusVacancy;
import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.repository.VacanciesReservedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Esp32Service{

    private final VacanciesReservedRepository vacanciesReservedRepository;

    public List<VaganciesReservedResponseDTO> vacanciesReservedStatus(){
        List<Vacancy> allVacancies = vacanciesReservedRepository.findByAll();
        List<VaganciesReservedResponseDTO> vacanciesReserveds = null;
        for (Vacancy vacancy: allVacancies) {
            if (vacancy.getStatus() == EnumStatusVacancy.reserved){
                vacanciesReserveds.add(new VaganciesReservedResponseDTO(vacancy.getPosition(), true));
            }
        }
        return vacanciesReserveds;
    }

    public String collectAdmAlert(){
        Admin admAlert = //Criar repositorio que coleta oque o alerta do admnistrador
    }

}
