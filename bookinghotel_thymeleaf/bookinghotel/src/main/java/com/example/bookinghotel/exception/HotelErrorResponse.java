package com.example.bookinghotel.exception;

public class HotelErrorResponse {
    private int statusCode;
    private String message;
    private String timestamp;

    public HotelErrorResponse(){}

    public HotelErrorResponse(int statusCode, String message, String timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
