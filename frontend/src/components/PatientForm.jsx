import { useState } from "react";
import axios from "axios";

function PatientForm({ setPatientId }) {
  const [form, setForm] = useState({
    name: "",
    age: "",
    phone: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    const res = await axios.post("http://localhost:8080/patients", form);
    setPatientId(res.data.id);
    alert("Patient registered!");
  };

  return (
    <div className="bg-white p-5 rounded shadow">
      <h2 className="text-xl font-bold mb-2">Register Patient</h2>

      <form onSubmit={handleSubmit}>
        <input placeholder="Name" className="input"
          onChange={(e) => setForm({ ...form, name: e.target.value })} />

        <input placeholder="Age" className="input"
          onChange={(e) => setForm({ ...form, age: e.target.value })} />

        <input placeholder="Phone" className="input"
          onChange={(e) => setForm({ ...form, phone: e.target.value })} />

        <button className="btn-blue mt-2">Register</button>
      </form>
    </div>
  );
}

export default PatientForm;