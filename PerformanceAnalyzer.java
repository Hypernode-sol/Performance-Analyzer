package com.hypernode.performance;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PerformanceAnalyzer - Analyzes collected node metrics to identify performance trends
 * and optimization opportunities.
 *
 * This class processes data from the NodePerformanceMonitor and generates
 * insights for the hypernode ecosystem.
 */
public class PerformanceAnalyzer {

    private final NodePerformanceMonitor performanceMonitor;

    /**
     * Constructor to initialize the PerformanceAnalyzer.
     *
     * @param performanceMonitor The monitor providing node metrics.
     */
    public PerformanceAnalyzer(NodePerformanceMonitor performanceMonitor) {
        this.performanceMonitor = performanceMonitor;
    }

    /**
     * Identifies nodes that are under high load based on CPU usage threshold.
     *
     * @param cpuThreshold The CPU usage percentage threshold.
     * @return A list of node IDs under high load.
     */
    public List<String> findHighLoadNodes(double cpuThreshold) {
        List<String> highLoadNodes = new ArrayList<>();
        for (NodePerformanceMonitor.NodeMetrics metrics : performanceMonitor.nodeMetricsMap.values()) {
            if (metrics != null && metrics.cpuUsage > cpuThreshold) {
                highLoadNodes.add(metrics.nodeId);
            }
        }
        return highLoadNodes;
    }

    /**
     * Calculates average performance metrics across all nodes.
     *
     * @return A map containing average CPU usage, memory usage, and bandwidth.
     */
    public Map<String, Double> calculateAverageMetrics() {
        double totalCpuUsage = 0;
        double totalMemoryUsage = 0;
        double totalBandwidth = 0;
        int nodeCount = 0;

        for (NodePerformanceMonitor.NodeMetrics metrics : performanceMonitor.nodeMetricsMap.values()) {
            if (metrics != null) {
                totalCpuUsage += metrics.cpuUsage;
                totalMemoryUsage += metrics.memoryUsage;
                totalBandwidth += metrics.bandwidth;
                nodeCount++;
            }
        }

        if (nodeCount == 0) {
            return Map.of("averageCpuUsage", 0.0, "averageMemoryUsage", 0.0, "averageBandwidth", 0.0);
        }

        return Map.of(
            "averageCpuUsage", totalCpuUsage / nodeCount,
            "averageMemoryUsage", totalMemoryUsage / nodeCount,
            "averageBandwidth", totalBandwidth / nodeCount
        );
    }

    /**
     * Suggests task redistribution for nodes under high load.
     *
     * @param cpuThreshold The CPU usage threshold for high load.
     * @return A suggestion report for task redistribution.
     */
    public String suggestTaskRedistribution(double cpuThreshold) {
        List<String> highLoadNodes = findHighLoadNodes(cpuThreshold);
        if (highLoadNodes.isEmpty()) {
            return "[INFO] All nodes are operating within acceptable CPU usage limits.";
        }

        String report = "[SUGGESTION] Task redistribution needed for high load nodes: \n";
        report += highLoadNodes.stream().map(node -> "- Node ID: " + node).collect(Collectors.joining("\n"));
        return report;
    }

    public static void main(String[] args) {
        NodePerformanceMonitor monitor = new NodePerformanceMonitor();

        // Example data
        monitor.updateMetrics("Node1", 85.0, 60.0, 150.0);
        monitor.updateMetrics("Node2", 45.0, 70.0, 100.0);
        monitor.updateMetrics("Node3", 90.0, 80.0, 200.0);

        PerformanceAnalyzer analyzer = new PerformanceAnalyzer(monitor);

        // Calculate average metrics
        Map<String, Double> averages = analyzer.calculateAverageMetrics();
        System.out.println("[INFO] Average Metrics: " + averages);

        // Find high load nodes
        List<String> highLoadNodes = analyzer.findHighLoadNodes(80.0);
        System.out.println("[INFO] High Load Nodes: " + highLoadNodes);

        // Suggest task redistribution
        String redistributionSuggestion = analyzer.suggestTaskRedistribution(80.0);
        System.out.println(redistributionSuggestion);
    }
}
