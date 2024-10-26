package com.frightfox.poc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frightfox.poc.model.PincodeLocation;

public interface PincodeLocationRepository extends JpaRepository<PincodeLocation, String> {
    Optional<PincodeLocation> findByPincode(String pincode);
}
