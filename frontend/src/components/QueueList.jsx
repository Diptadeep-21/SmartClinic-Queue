import { useState, useEffect } from "react";
import axios from "axios";

function QueueList() {
  const [queue, setQueue] = useState([]);
  const [doctorId, setDoctorId] = useState("");
  const [doctors, setDoctors] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/doctors")
      .then(res => setDoctors(res.data));
  }, []);

  const fetchQueue = async () => {
    let url = "http://localhost:8080/appointments/queue";

    if (doctorId) {
      url += `/${doctorId}`;
    }

    const res = await axios.get(url);
    setQueue(res.data);
  };

  useEffect(() => {
  const fetchQueue = async () => {
    let url = "http://localhost:8080/appointments/queue";

    if (doctorId) {
      url += `/${doctorId}`;
    }

    const res = await axios.get(url);
    setQueue(res.data);
  };

  fetchQueue();

  const interval = setInterval(fetchQueue, 3000);
  return () => clearInterval(interval);

}, [doctorId]);

  const markEmergency = async (id) => {
    await axios.put(`http://localhost:8080/appointments/emergency/${id}`);
    fetchQueue();
  };

  return (
    <div className="bg-white p-5 rounded shadow">
      <h2 className="text-xl font-bold mb-2">Live Queue</h2>

      {/* 🔥 Filter by doctor */}
      <select
        className="input mb-3"
        onChange={(e) => setDoctorId(Number(e.target.value))}
      >
        <option value="">All Doctors</option>
        {doctors.map(d => (
          <option key={d.id} value={d.id}>
            Dr. {d.name}
          </option>
        ))}
      </select>

      {queue.map((a) => (
        <div
          key={a.id}
          className={`p-3 mb-2 rounded ${
            a.priority === "CRITICAL"
              ? "bg-red-300"
              : a.priority === "URGENT"
              ? "bg-yellow-200"
              : "bg-green-200"
          }`}
        >
          <p><b>{a.patient.name}</b></p>
          <p>Doctor: {a.doctor.name}</p>
          <p>{a.priority}</p>
          <p>Wait: {a.estimatedWaitTime} mins</p>

          <button
            onClick={() => markEmergency(a.id)}
            className="bg-red-500 text-white px-2 py-1 mt-2 rounded"
          >
            🚨 Emergency
          </button>
        </div>
      ))}
    </div>
  );
}

export default QueueList;