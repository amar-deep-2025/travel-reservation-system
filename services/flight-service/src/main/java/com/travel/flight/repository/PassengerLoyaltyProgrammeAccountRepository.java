package com.travel.flight.repository;

import com.travel.flight.entity.PassengerLoyaltyProgrammeAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerLoyaltyProgrammeAccountRepository extends JpaRepository<PassengerLoyaltyProgrammeAccount, Long> {

    List<PassengerLoyaltyProgrammeAccount> findByPassengerId(Long passengerId);

    Optional<PassengerLoyaltyProgrammeAccount> findByPassengerIdAndAirlineIataCode(
            Long passengerId, String airlineIataCode
    );

    boolean existsByPassengerIdAndAirlineIataCodeAndAccountNumber(Long passengerId, String airlineIataCode, String accountNumber);

}
