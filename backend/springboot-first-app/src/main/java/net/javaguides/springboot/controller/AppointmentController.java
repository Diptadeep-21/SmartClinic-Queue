package net.javaguides.springboot.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import net.javaguides.springboot.model.Appointment;
import net.javaguides.springboot.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
@CrossOrigin
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // 🔥 BOOK APPOINTMENT
    @PostMapping("/book")
    public Appointment book(
            @RequestParam Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam String priority) {

        return service.book(patientId, doctorId, priority);
    }

 // 🔥 ALL DOCTORS QUEUE
    @GetMapping("/queue")
    public List<Appointment> allQueue() {
        return service.getAllQueue();
    }

    // 🔥 SPECIFIC DOCTOR
    @GetMapping("/queue/{doctorId}")
    public List<Appointment> queue(@PathVariable Long doctorId) {
        return service.getQueue(doctorId);
    }

    // 🔥 MARK EMERGENCY
    @PutMapping("/emergency/{id}")
    public Appointment emergency(@PathVariable Long id) {
        return service.markEmergency(id);
    }
    
    
}