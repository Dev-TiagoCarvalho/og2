package dev.online.controller.admin;

import dev.online.config.ApplicationContextProvider;
import dev.online.monitor.method.annot.Audit;
import dev.online.monitor.method.annot.IgnoreOnAudit;
import org.elasticsearch.client.RestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class MainController {

    private final MainService service;

    public MainController(MainService service) {
        this.service = service;
    }

    @Audit
    @PostMapping("/api/v1/admin/test")
    public Input test(@RequestBody @IgnoreOnAudit Input input) {
        if(input == null || input.value() == null || input.value().isEmpty()) throw new IllegalArgumentException("Value must be present!");
        return input;
    }

    @Audit
    @GetMapping("/api/v1/admin/test")
    public int test() {
        return service.test();
    }

    @Audit
    @GetMapping("/api/v1/admin/elastic")
    public String elasticsearch() {
        return "OK";
    }
}
