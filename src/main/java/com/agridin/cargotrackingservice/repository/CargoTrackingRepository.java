package com.agridin.cargotrackingservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agridin.cargotrackingservice.model.CargoTracking;

@Repository
public interface CargoTrackingRepository extends JpaRepository<CargoTracking,UUID> {

    @Query(value = "DELETE FROM cargo_takip WHERE order_id = :orderId", nativeQuery = true)
    public void deleteAllByOrder(UUID orderId);

    boolean existsByOrder_Id(UUID orderId);
}
