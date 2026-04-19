package net.javaguides.springboot.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import net.javaguides.springboot.model.Patient;
import net.javaguides.springboot.repository.PatientRepository;

@RestController
@RequestMapping("/patients")
@CrossOrigin
public class PatientController {

    private final PatientRepository repo;

    public PatientController(PatientRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Patient add(@RequestBody Patient p) {
        return repo.save(p);
    }

    @GetMapping
    public List<Patient> getAll() {
        return repo.findAll();
    }
}