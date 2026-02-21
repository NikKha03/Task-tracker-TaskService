package NikKha03.TaskService.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task_service/app")
public class AppController {

    @GetMapping()
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Success!");
    }
}
