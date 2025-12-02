package org.itmo.infosec.infosec_lab1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    public record DemoItem(String title, String preview) {}
    public record UserInfo(String email, String note) {}

    @GetMapping("/data")
    public ResponseEntity<List<DemoItem>> loadDemoData() {

        String potentiallyDangerous = "Hello <b>world</b> & welcome!";

        String safeText = HtmlUtils.htmlEscape(potentiallyDangerous);

        List<DemoItem> items = List.of(
                new DemoItem("Welcome message", safeText),
                new DemoItem("System info", HtmlUtils.htmlEscape("Server status: OK <tag>"))
        );

        return ResponseEntity.ok(items);
    }

    @PostMapping("/data")
    public ResponseEntity<String> submitDemoData(@RequestBody String raw) {

        String safe = HtmlUtils.htmlEscape(raw);

        return ResponseEntity.ok(
                "Received & processed: " + safe
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<UserInfo> loadProfile() {

        UserInfo info = new UserInfo(
                "authenticated-user@example.com",
                HtmlUtils.htmlEscape("Your profile is active & secure.")
        );

        return ResponseEntity.ok(info);
    }
}
