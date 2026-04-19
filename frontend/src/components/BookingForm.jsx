import { useState, useEffect } from "react";
import axios from "axios";

function BookingForm({ patientId }) {
  const [priority, setPriority] = useState("NORMAL");
  const [doctorId, setDoctorId] = useState("");
  const [doctors, setDoctors] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/doctors")
      .then(res => setDoctors(res.data));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    await axios.post("http://localhost:8080/appointments/book", null, {
      params: { patientId, doctorId, priority },
    });

    alert("Appointment booked!");
  };

  return (
    <div className="bg-white p-5 rounded shadow mt-4">
      <h2 className="text-xl font-bold mb-2">Book Appointment</h2>

      <form onSubmit={handleSubmit}>

        {/* 🔥 Doctor Dropdown */}
        <select
          className="input mb-2"
          onChange={(e) => setDoctorId(e.target.value)}
        >
          <option value="">Auto Assign Doctor</option>
          {doctors.map(d => (
            <option key={d.id} value={d.id}>
              Dr. {d.name} ({d.specialization})
            </option>
          ))}
        </select>

        {/* Priority */}
        <select
          className="input"
          onChange={(e) => setPriority(e.target.value)}
        >
          <option value="NORMAL">Normal</option>
          <option value="URGENT">Urgent</option>
          <option value="CRITICAL">Critical</option>
        </select>

        <button className="btn-green mt-2">Book</button>
      </form>
    </div>
  );
}

export default BookingForm;