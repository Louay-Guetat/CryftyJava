package edu.esprit.cryfty.entity;

import javafx.beans.property.*;

import java.util.Objects;

public class Node {
    private int id;
    private final StringProperty nodeLabel;
    private final StringProperty coinCode;
    private final DoubleProperty nodeReward;

    public Node() {
        this.nodeLabel = new SimpleStringProperty("node_label");
        this.coinCode = new SimpleStringProperty("coin_code");
        this.nodeReward = new SimpleDoubleProperty(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodeLabel() {
        return nodeLabel.get();
    }

    public StringProperty nodeLabelProperty() {
        return nodeLabel;
    }

    public void setNodeLabel(String nodeLabel) {
        this.nodeLabel.set(nodeLabel);
    }

    public String getCoinCode() {
        return coinCode.get();
    }

    public StringProperty coinCodeProperty() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode.set(coinCode);
    }

    public double getNodeReward() {
        return nodeReward.get();
    }

    public DoubleProperty nodeRewardProperty() {
        return nodeReward;
    }

    public void setNodeReward(double nodeReward) {
        this.nodeReward.set(nodeReward);
    }

    @Override
    public String toString() {
        return this.getNodeLabel();
    }
}
