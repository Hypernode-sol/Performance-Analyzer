package com.hypernode.optimization;

import com.hypernode.performance.NodePerformanceMonitor;
import java.util.*;

/**
 * OptimizationEngine - Optimizes network resource allocation based on performance data.
 *
 * This class analyzes metrics from nodes, identifies bottlenecks, and provides strategies
 * to enhance the network's overall performance and efficiency.
 */
public class OptimizationEngine {

    private final NodePerformanceMonitor performanceMonitor;

    /**
     * Constructor to initialize the OptimizationEngine.
     *
     * @param performanceMonitor The monitor providing node performance data.
     */
    public OptimizationEngine(NodePerformanceMonitor performanceMonitor) {
        this.performanceMonitor = performanceMonitor;
    }

    /**
     * Identifies nodes with low resource utilization to better allocate tasks.
     *
     * @param cpuThreshold The CPU usage percentage below which a node is considered underutilized.
     * @return A list of node IDs with low resource utilization.
     */
    public List<String> findUnderutilizedNodes(double cpuThreshold) {
        List<String> underutilizedNodes = new ArrayList<>();
        for (NodePerformanceMonitor.NodeMetrics metrics : performanceMonitor.nodeMetricsMap.values()) {
            if (metrics != null && metrics.cpuUsage < cpuThreshold) {
                underutilizedNodes.add(metrics.nodeId);
            }
        }
        return underutilizedNodes;
    }

    /**
     * Generates a task redistribution plan to balance the workload across nodes.
     *
     * @param highLoadNodes A list of nodes under high load.
     * @param underutilizedNodes A list of nodes with spare capacity.
     * @return A redistribution plan as a map of source node IDs to destination node IDs.
     */
    public Map<String, String> generateRedistributionPlan(List<String> highLoadNodes, List<String> underutilizedNodes) {
        Map<String, String> redistributionPlan = new HashMap<>();

        Iterator<String> underutilizedIterator = underutilizedNodes.iterator();
        for (String highLoadNode : highLoadNodes) {
            if (underutilizedIterator.hasNext()) {
                String underutilizedNode = underutilizedIterator.next();
                redistributionPlan.put(highLoadNode, underutilizedNode);
            } else {
                break;
            }
        }

        return redistributionPlan;
    }

    /**
     * Applies optimization strategies to improve the network's performance.
     *
     * @param cpuHighThreshold The CPU usage threshold to identify high load nodes.
     * @param cpuLowThreshold The CPU usage threshold to identify underutilized nodes.
     * @return A report detailing the optimization actions taken.
     */
    public String optimizeNetwork(double cpuHighThreshold, double cpuLowThreshold) {
        List<String> highLoadNodes = performanceMonitor.findHighLoadNodes(cpuHighThreshold);
        List<String> underutilizedNodes = findUnderutilizedNodes(cpuLowThreshold);

        Map<String, String> redistributionPlan = generateRedistributionPlan(highLoadNodes, underutilizedNodes);

        if (redistributionPlan.isEmpty()) {
            return "[INFO] No optimization needed. All nodes are operating efficiently.";
        }

        StringBuilder report = new StringBuilder("[OPTIMIZATION REPORT]\n");
        report.append("Task redistribution plan:\n");
        for (Map.Entry<String, String> entry : redistributionPlan.entrySet()) {
            report.append(String.format("Move tasks from %s to %s\n", entry.getKey(), entry.getValue()));
        }
        return report.toString();
    }

    public static void main(String[] args) {
        NodePerformanceMonitor monitor = new NodePerformanceMonitor();

        // Example data
        monitor.updateMetrics("Node1", 90.0, 70.0, 150.0);
        monitor.updateMetrics("Node2", 45.0, 60.0, 100.0);
        monitor.updateMetrics("Node3", 30.0, 50.0, 80.0);

        OptimizationEngine engine = new OptimizationEngine(monitor);

        // Perform optimization
        String report = engine.optimizeNetwork(80.0, 40.0);
        System.out.println(report);
    }
}
