package com.hypernode.performance;

import java.util.*;

/**
 *
 * NodePerformanceMonitor - Monitors the performance metrics of network nodes in real-time.
 * 
 * This class collects key metrics such as CPU usage, memory utilization, and bandwidth
 * consumption from each node, and stores the data for analysis.
 */
public class NodePerformanceMonitor {

    private final Map<String, NodeMetrics> nodeMetricsMap;

    /**
     * Constructor to initialize the NodePerformanceMonitor.
     */
    public NodePerformanceMonitor() {
        this.nodeMetricsMap = new HashMap<>();
    }

    /**
     * Collects and updates metrics for a specific node.
     * 
     * @param nodeId The unique ID of the node.
     * @param cpuUsage The percentage of CPU being used.
     * @param memoryUsage The percentage of memory being used.
     * @param bandwidth The bandwidth usage in Mbps.
     */
    public void updateMetrics(String nodeId, double cpuUsage, double memoryUsage, double bandwidth) {
        NodeMetrics metrics = nodeMetricsMap.getOrDefault(nodeId, new NodeMetrics(nodeId));
        metrics.setCpuUsage(cpuUsage);
        metrics.setMemoryUsage(memoryUsage);
        metrics.setBandwidth(bandwidth);
        nodeMetricsMap.put(nodeId, metrics);
        System.out.println("[INFO] Metrics updated for node: " + nodeId);
    }

    /**
     * Retrieves the metrics for a specific node.
     * 
     * @param nodeId The unique ID of the node.
     * @return The metrics of the node, or null if the node does not exist.
     */
    public NodeMetrics getMetrics(String nodeId) {
        return nodeMetricsMap.get(nodeId);
    }

    /**
     * Prints the metrics of all nodes to the console.
     */
    public void printAllMetrics() {
        System.out.println("[INFO] Node Performance Metrics:");
        for (NodeMetrics metrics : nodeMetricsMap.values()) {
            System.out.println(metrics);
        }
    }

    /**
     * Inner class to represent the metrics of a node.
     */
    public static class NodeMetrics {
        private final String nodeId;
        private double cpuUsage;
        private double memoryUsage;
        private double bandwidth;

        public NodeMetrics(String nodeId) {
            this.nodeId = nodeId;
        }

        public void setCpuUsage(double cpuUsage) {
            this.cpuUsage = cpuUsage;
        }

        public void setMemoryUsage(double memoryUsage) {
            this.memoryUsage = memoryUsage;
        }

        public void setBandwidth(double bandwidth) {
            this.bandwidth = bandwidth;
        }

        @Override
        public String toString() {
            return String.format("Node ID: %s, CPU Usage: %.2f%%, Memory Usage: %.2f%%, Bandwidth: %.2f Mbps",
                    nodeId, cpuUsage, memoryUsage, bandwidth);
        }
    }

    public static void main(String[] args) {
        NodePerformanceMonitor monitor = new NodePerformanceMonitor();

        // Example usage
        monitor.updateMetrics("Node1", 75.5, 60.2, 100.0);
        monitor.updateMetrics("Node2", 45.3, 80.0, 120.0);
        monitor.updateMetrics("Node3", 90.1, 70.5, 150.0);

        monitor.printAllMetrics();

        // Retrieve metrics for a specific node
        NodeMetrics metrics = monitor.getMetrics("Node1");
        if (metrics != null) {
            System.out.println("[INFO] Retrieved metrics: " + metrics);
        }
    }
}
