package com.example.demo.reservations;

public record ReservationSearchFilter (
        Long roomId,
        Long userId,
        Integer pageSize,
        Integer pageNumber
) {};
