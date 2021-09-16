package com.digitalnx.resource.api.settings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsController {
    SettingsRepository repository;
    SettingsController(SettingsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/settings")
    public Settings
    settings() {
        return repository.findAll().get(0);
    }

    @PostMapping("/settings/change")
    public String changeSettings(@RequestParam String securityMode) {
        Settings currentSettings = repository.findAll().get(0);
        currentSettings.setSecurityMode(SecurityMode.valueOf(securityMode));
        repository.save(currentSettings);
        return "Changed";
    }
}
