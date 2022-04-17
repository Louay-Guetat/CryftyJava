package edu.esprit.cryfty.entity;

import java.util.Objects;

public class Block {
    private int id;
    private Node node;
    private Wallet wallet;
    private String hash;
    private String previousHash;

    public Block() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return id == block.id && Objects.equals(node, block.node) && Objects.equals(wallet, block.wallet) && Objects.equals(hash, block.hash) && Objects.equals(previousHash, block.previousHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, node, wallet, hash, previousHash);
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", node=" + node +
                ", wallet=" + wallet +
                ", hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                '}';
    }
}
