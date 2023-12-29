package io.github.jubadeveloper.requestlimiter.actors.rest.contracts;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public interface DateShowRestControllerContract {
    @GetMapping("/date")
    ResponseEntity<String> getCurrentDate ();
}
