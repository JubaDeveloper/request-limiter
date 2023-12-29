package io.github.jubadeveloper.requestlimiter.actors.rest;

import io.github.jubadeveloper.requestlimiter.actors.rest.contracts.DateShowRestControllerContract;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class DateShowRestController implements DateShowRestControllerContract {
    @Override
    public ResponseEntity<String> getCurrentDate() {
        return new ResponseEntity<>(LocalDateTime.now().toString(), HttpStatus.OK);
    }
}
