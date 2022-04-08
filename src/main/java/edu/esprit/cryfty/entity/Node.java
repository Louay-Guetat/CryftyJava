package edu.esprit.cryfty.entity;

import java.util.Objects;

public class Node {
    private int id;
    private String nodeLabel;
    private String coinCode;
    private double nodeReward;

    public Node() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodeLabel() {
        return nodeLabel;
    }

    public void setNodeLabel(String nodeLabel) {
        this.nodeLabel = nodeLabel;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public double getNodeReward() {
        return nodeReward;
    }

    public void setNodeReward(double nodeReward) {
        this.nodeReward = nodeReward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id && Double.compare(node.nodeReward, nodeReward) == 0 && Objects.equals(nodeLabel, node.nodeLabel) && Objects.equals(coinCode, node.coinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nodeLabel, coinCode, nodeReward);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", nodeLabel='" + nodeLabel + '\'' +
                ", coinCode='" + coinCode + '\'' +
                ", nodeReward=" + nodeReward +
                '}';
    }
}
