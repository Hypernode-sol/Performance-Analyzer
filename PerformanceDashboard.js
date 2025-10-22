// PerformanceDashboard.js

// Dashboard for visualizing and interacting with the performance metrics of nodes in the network.

document.addEventListener("DOMContentLoaded", () => {
    const apiEndpoint = "/api/performance"; // Example API endpoint for fetching performance data

    // Fetch performance data from the server
    async function fetchPerformanceData() {
        try {
            const response = await fetch(apiEndpoint);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching performance data:", error);
            return null;
        }
    }

    // Render performance metrics in the dashboard
    function renderPerformanceMetrics(data) {
        const container = document.getElementById("performance-metrics");
        container.innerHTML = "";

        data.forEach(node => {
            const nodeElement = document.createElement("div");
            nodeElement.className = "node-metric";
            nodeElement.innerHTML = `
                <h3>Node ID: ${node.nodeId}</h3>
                <p>CPU Usage: ${node.cpuUsage}%</p>
                <p>Memory Usage: ${node.memoryUsage} MB</p>
                <p>Task Load: ${node.taskLoad}</p>
            `;
            container.appendChild(nodeElement);
        });
    }

    // Initialize optimization actions
    function setupOptimizationActions() {
        const optimizeButton = document.getElementById("optimize-network");
        optimizeButton.addEventListener("click", async () => {
            try {
                const response = await fetch("/api/optimize", { method: "POST" });
                if (!response.ok) {
                    throw new Error("Failed to optimize network");
                }
                const result = await response.json();
                alert("Optimization completed: " + result.message);
            } catch (error) {
                console.error("Error during optimization:", error);
                alert("Optimization failed. Check logs for details.");
            }
        });
    }

    // Refresh dashboard data periodically
    async function refreshDashboard() {
        const data = await fetchPerformanceData();
        if (data) {
            renderPerformanceMetrics(data);
        }
    }

    // Set up initial dashboard
    async function initializeDashboard() {
        setupOptimizationActions();
        await refreshDashboard();

        // Refresh data every 60 seconds
        setInterval(refreshDashboard, 60000);
    }

    initializeDashboard();
});
