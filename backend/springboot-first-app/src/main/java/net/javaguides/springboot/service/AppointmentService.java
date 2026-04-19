package net.javaguides.springboot.service;

import org.springframework.stereotype.Service;
import java.util.*;
import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.*;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;

    public AppointmentService(AppointmentRepository a, PatientRepository p, DoctorRepository d) {
        this.appointmentRepo = a;
        this.patientRepo = p;
        this.doctorRepo = d;
    }

    // 🔥 BOOK
    public Appointment book(Long patientId, Long doctorId, String priority) {

        Patient patient = patientRepo.findById(patientId).orElseThrow();

        Doctor doctor;

        if (doctorId != null) {
            // ✅ user selected doctor
            doctor = doctorRepo.findById(doctorId).orElseThrow();
        } else {
            // 🔥 fallback → auto assign least busy
            List<Doctor> doctors = doctorRepo.findAll();
            List<Appointment> all = appointmentRepo.findAll();

            doctor = doctors.stream()
                    .min(Comparator.comparingLong(d ->
                            all.stream()
                                    .filter(a -> a.getDoctor().getId().equals(d.getId()))
                                    .count()
                    ))
                    .orElseThrow();
        }

        List<Appointment> doctorAppointments =
                appointmentRepo.findByDoctorId(doctor.getId());

        Appointment appt = new Appointment();
        appt.setPatient(patient);
        appt.setDoctor(doctor);
        appt.setPriority(priority);
        appt.setStatus("WAITING");

        appt.setTokenNumber(doctorAppointments.size() + 1);

        int waitTime = doctorAppointments.size() * doctor.getAvgConsultTime();
        appt.setEstimatedWaitTime(waitTime);

        return appointmentRepo.save(appt);
    }

    // 🔥 QUEUE (FIXED SIGNATURE)
    public List<Appointment> getQueue(Long doctorId) {

        List<Appointment> list = appointmentRepo.findAll()
                .stream()
                .filter(a -> a.getDoctor().getId().equals(doctorId))
                .toList();

        return list.stream()
                .sorted((a, b) -> getPriorityValue(b.getPriority()) - getPriorityValue(a.getPriority()))
                .toList();
    }

    // 🔥 EMERGENCY (FIXED METHOD)
    public Appointment markEmergency(Long id) {
        Appointment appt = appointmentRepo.findById(id).orElseThrow();
        appt.setPriority("CRITICAL");
        appt.setEstimatedWaitTime(0);
        return appointmentRepo.save(appt);
    }

    private int getPriorityValue(String p) {
        if (p.equalsIgnoreCase("CRITICAL")) return 3;
        if (p.equalsIgnoreCase("URGENT")) return 2;
        return 1;
    }
    
 // ✅ ALL QUEUE
    public List<Appointment> getAllQueue() {
        List<Appointment> list = appointmentRepo.findAll();

        list.sort((a, b) -> getPriorityValue(b.getPriority()) - getPriorityValue(a.getPriority()));

        return list;
    }

    // ✅ PER DOCTOR QUEUE
    public List<Appointment> getQueue(Long doctorId) {
        List<Appointment> list = appointmentRepo.findByDoctorId(doctorId);

        list.sort((a, b) -> getPriorityValue(b.getPriority()) - getPriorityValue(a.getPriority()));

        return list;
    }
}