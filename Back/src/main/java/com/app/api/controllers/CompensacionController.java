package com.app.api.controllers;

import com.app.api.models.CompensacionModel;
import com.app.api.services.CompensacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/compensaciones")
public class CompensacionController {
    private CompensacionService compensacionService;

    @Autowired
    public CompensacionController(CompensacionService compensacionService) {
        this.compensacionService = compensacionService;
    }

    @GetMapping
    public List<CompensacionModel> getAllCompensaciones() {
        return compensacionService.getAllCompensaciones();
    }
}
