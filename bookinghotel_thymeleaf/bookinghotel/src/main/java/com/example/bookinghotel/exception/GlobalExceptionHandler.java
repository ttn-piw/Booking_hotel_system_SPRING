package com.example.bookinghotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Room Exceptions
    @ExceptionHandler
    public ResponseEntity<RoomErrorResponse> handleRoomException(RoomNotFoundException ex) {
        RoomErrorResponse response = new RoomErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(String.valueOf(System.currentTimeMillis()));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    //Hotel Exceptions
    @ExceptionHandler
    public ResponseEntity<HotelErrorResponse> handleHotelNotFoundException(HotelNotFoundException ex) {
        HotelErrorResponse response = new HotelErrorResponse();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(String.valueOf(System.currentTimeMillis()));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //All exceptions
    @ExceptionHandler
    public ResponseEntity<RoomErrorResponse> handleAllException(Exception ex) {
        RoomErrorResponse response = new RoomErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(String.valueOf(System.currentTimeMillis()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
