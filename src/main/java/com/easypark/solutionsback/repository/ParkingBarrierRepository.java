package com.easypark.solutionsback.repository;

import com.easypark.solutionsback.enun.EnumGate;
import com.easypark.solutionsback.model.ParkingBarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ParkingBarrierRepository extends JpaRepository<ParkingBarrier, UUID> {

    Optional<ParkingBarrier> findByGate(EnumGate gate);
}
