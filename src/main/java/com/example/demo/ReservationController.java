package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

//    @GetMapping("/{id}")
//    public Reservation getReservationById(@PathVariable("id") Long id) {
//        log.info("Called getReservationById: id={}", id);
//        return reservationService.getReservationById(id);
//    }

//    @GetMapping()
//    public List<Reservation> getAllReservations() {
//        log.info("Called getAllReservations");
//        return reservationService.findAllReservations();
//    }

//    @PostMapping
//    public Reservation createReservation(
//        @RequestBody Reservation reservationToCreate
//    ) {
//        log.info("Called createReservation");
//        return reservationService.createReservation(reservationToCreate);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        log.info("Called getReservationById: id={}", id);
        try {
            return ResponseEntity
                    .status(HttpStatus.OK) // 200
                    .body(reservationService.getReservationById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        log.info("Called getAllReservations");
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservationToCreate
    ) {
        log.info("Called createReservation");
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201
                .header("test-header", "123")
                .body(reservationService.createReservation(reservationToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable("id") Long id,
            @RequestBody Reservation reservationToUpdate
    ) {
        log.info("Called method updateReservation id={}, reservationToUpdate={}", id, reservationToUpdate);
        var updated = reservationService.updateReservation(id, reservationToUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable("id") Long id
    ) {
        log.info("Called deleteReservation: id={}", id);
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Reservation> approveReservation(@PathVariable("id") Long id) {
        log.info("Called approveReservation: id={}", id);
        var reservation = reservationService.approveReservation(id);
        return ResponseEntity.ok(reservation);
    }

}
