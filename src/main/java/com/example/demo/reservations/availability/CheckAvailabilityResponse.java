package com.example.demo.reservations.availability;

public record CheckAvailabilityResponse(
        String message,
        AvailabilityStatus status
) {
}
