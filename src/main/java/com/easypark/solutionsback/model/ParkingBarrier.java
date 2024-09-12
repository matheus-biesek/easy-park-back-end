package com.easypark.solutionsback.model;

import com.easypark.solutionsback.enun.EnumGate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ParkingBarrier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private EnumGate gate;


    @Column(nullable = false)
    private Boolean status;

}
