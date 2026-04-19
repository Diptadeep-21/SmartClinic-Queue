import { useEffect, useState } from "react";
import axios from "axios";

function Dashboard() {
  const [stats, setStats] = useState({
    total: 0,
    critical: 0,
  });

  const fetchStats = async () => {
    const res = await axios.get("http://localhost:8080/appointments/queue/1");

    const total = res.data.length;
    const critical = res.data.filter(a => a.priority === "CRITICAL").length;

    setStats({ total, critical });
  };

  useEffect(() => {
    fetchStats();
  }, []);

  return (
    <div className="bg-white p-5 rounded shadow mb-4">
      <h2 className="text-xl font-bold mb-2">Dashboard</h2>

      <p>Total Patients: {stats.total}</p>
      <p>Critical Cases: {stats.critical}</p>
    </div>
  );
}

export default Dashboard;