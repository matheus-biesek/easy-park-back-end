package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.request.AdmAlertRequestDTO;
import com.easypark.solutionsback.dto.response.AdmAlertResponseDTO;
import com.easypark.solutionsback.model.Admin;
import com.easypark.solutionsback.repository.AdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmService {

    private final AdmRepository admRepository;

    public ResponseEntity<AdmAlertResponseDTO> sendAlert(AdmAlertRequestDTO body){
        if (body.admAlert().isBlank() || body.admAlert().isEmpty()){
            return new ResponseEntity<>(new AdmAlertResponseDTO("Caracteres inválidos."), HttpStatus.BAD_REQUEST);
        }
        //colocar está funcão no repository pois o repository pode encontrar o admin
        List<Admin> admins = this.admRepository.findAll();
        if (admins.isEmpty()){
            return new ResponseEntity<>(new AdmAlertResponseDTO("Não é possivel realizar a operação, o banco de dados não achou a coluna admAlert!."), HttpStatus.BAD_REQUEST);
        }
        Admin adm = admins.get(0);
        adm.setAdmAlert(body.admAlert());
        this.admRepository.save(adm);
        return new ResponseEntity<>(new AdmAlertResponseDTO("Alerta enviado com sucesso"), HttpStatus.OK);
    }
}
