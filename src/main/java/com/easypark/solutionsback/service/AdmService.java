package com.easypark.solutionsback.service;

import com.easypark.solutionsback.dto.request.AdmAlertRequestDTO;
import com.easypark.solutionsback.model.Adm;
import com.easypark.solutionsback.repository.AdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdmService {

    private final AdmRepository admRepository;


    public ResponseEntity<String> sendAlert(AdmAlertRequestDTO body) {
        try {
            String alertMessage = body.admAlert();
            if (alertMessage.isBlank()) {
                return new ResponseEntity<>("Caracteres inválidos.", HttpStatus.BAD_REQUEST);
            }

            UUID adminId = UUID.fromString("769bb14e-892d-444d-bb01-821a3aa8427b");
            // prod UUID adminId = UUID.fromString("948d41b6-237c-4071-8c07-eccd6394b60b");

            Optional<Adm> optionalAdm = admRepository.findById(adminId);
            if (optionalAdm.isEmpty()) {
                return new ResponseEntity<>("Não foi encontrado um administrador no banco de dados.", HttpStatus.NOT_FOUND);
            }

            Adm adm = optionalAdm.get();
            adm.setAdmAlert(alertMessage);
            admRepository.save(adm);

            return new ResponseEntity<>("Alerta enviado com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao enviar o alerta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> statusAdmAlert() {
        try {
            UUID adminId = UUID.fromString("769bb14e-892d-444d-bb01-821a3aa8427b");
            // prod UUID adminId = UUID.fromString("948d41b6-237c-4071-8c07-eccd6394b60b");


            Optional<Adm> optionalAdm = admRepository.findById(adminId);
            if (optionalAdm.isEmpty()) {
                return new ResponseEntity<>("Bem-vindo!", HttpStatus.OK);
            }

            String admAlert = optionalAdm.get().getAdmAlert();
            if (admAlert.isBlank()) {
                return new ResponseEntity<>("Não há mensagens!", HttpStatus.OK);
            }

            return new ResponseEntity<>(admAlert, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao verificar o status do alerta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}