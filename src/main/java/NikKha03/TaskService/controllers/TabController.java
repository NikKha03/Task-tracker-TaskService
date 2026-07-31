package NikKha03.TaskService.controllers;

import NikKha03.TaskService.DTO.TabRequest;
import NikKha03.TaskService.service.TabService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "tab_controller")
@RestController
@RequestMapping("/task_service/tab")
public class TabController {

    private final TabService tabService;

    public TabController(TabService tabService) {
        this.tabService = tabService;
    }

    @GetMapping("/get/{tabId}")
    public ResponseEntity<?> getTabById(@PathVariable("tabId") Long tabId) {
        return tabService.findTabById(tabId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTab(@RequestBody TabRequest request) {
        return tabService.createTab(request);
    }

    @Transactional
    @PutMapping("/change/{tabId}")
    public ResponseEntity<?> changeTab(@PathVariable("tabId") Long tabId, @RequestBody TabRequest request) {
        return tabService.changeTab(tabId, request);
    }

    @Transactional
    @DeleteMapping("/delete/{tabId}")
    public void deleteTab(@PathVariable("tabId") Long tabId) {
        tabService.deleteTab(tabId);
    }

}
