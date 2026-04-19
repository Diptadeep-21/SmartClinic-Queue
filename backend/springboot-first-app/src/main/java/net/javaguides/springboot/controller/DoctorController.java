package net.javaguides.springboot.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import net.javaguides.springboot.model.Doctor;
import net.javaguides.springboot.repository.DoctorRepository;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
public class DoctorController {

    private final DoctorRepository repo;

    public DoctorController(DoctorRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Doctor add(@RequestBody Doctor d) {
        return repo.save(d);
    }

    @GetMapping
    public List<Doctor> getAll() {
        return repo.findAll();
    }
}