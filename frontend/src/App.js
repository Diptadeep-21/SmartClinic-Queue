import { useState } from "react";
import PatientForm from "./components/PatientForm";
import BookingForm from "./components/BookingForm";
import QueueList from "./components/QueueList";
import Dashboard from "./components/Dashboard";

function App() {
  const [patientId, setPatientId] = useState(null);

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-3xl font-bold mb-4">
        🏥 Hospital Queue System
      </h1>

      <div className="grid grid-cols-2 gap-4">
        <Dashboard />
        <div>
          <PatientForm setPatientId={setPatientId} />
          {patientId && <BookingForm patientId={patientId} />}
        </div>

        <QueueList />
      </div>
    </div>
  );
}

export default App;