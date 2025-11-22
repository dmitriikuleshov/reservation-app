package com.example.demo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    /// Example methods, not used in this projects

    List<ReservationEntity> findAllByStatusIs(ReservationStatus status);

    // Spring breaks this name in parts and understands automatically which query to make
    List<ReservationEntity> findAllByEndDateAndRoomIdAndStartDate(LocalDate endDate,
                                                                  Long roomId,
                                                                  LocalDate startDate);

    // jpql
    @Query("select r from ReservationEntity r where r.status = :status")
    List<ReservationEntity> findAllByStatusIsCustom(ReservationStatus status);

    // sql
    @Query(value = "select * from reservations where r.status = :status", nativeQuery = true)
    List<ReservationEntity> findAllByStatusIsCustom1(ReservationStatus status);

    @Query("select r from ReservationEntity r where r.roomId = :roomId")
    List<ReservationEntity> findAllByRoomId(@Param("roomId") Long roomId);


    // should be transactional because it modifies data (requirement from spring data jpa, hibernate)
    @Transactional
    @Modifying
    @Query("""
            update ReservationEntity r
            set r.userId = :userId,
                r.roomId = :roomId,
                r.startDate = :startDate,
                r.endDate = :endDate,
                r.status = :status
            where r.id = :id
            """)
    int updateAllFields(
            @Param("id") Long id,
            @Param("userId") Long userId,
            @Param("roomId") Long roomId,
            @Param("endDate") LocalDate endDate,
            @Param("status") ReservationStatus status
    );

    ///  Example methods end

    @Transactional
    @Modifying
    @Query("""
            update ReservationEntity r
            set r.status = :status
            where r.id = :id
            """)
    void setStatus(
            @Param("id") Long id,
            @Param("status") ReservationStatus reservationStatus
    );
}
