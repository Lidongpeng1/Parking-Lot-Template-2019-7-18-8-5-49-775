package com.thoughtworks.parking_lot.advice;

import com.thoughtworks.parking_lot.exception.NoPositionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ParkingOrderControllerAdvice {
    @ExceptionHandler(NoPositionException.class)
    @ResponseBody
    public ResponseEntity<Object> defultExcepitonHandler(Exception e) {
        return ResponseEntity.badRequest().body("停车场已经满");
    }
}
