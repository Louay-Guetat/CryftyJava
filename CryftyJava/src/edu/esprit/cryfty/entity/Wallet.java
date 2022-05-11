package edu.esprit.cryfty.entity;

import edu.esprit.cryfty.entity.User.Client;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.Objects;

public class Wallet {
    private int id;
    private final StringProperty walletAddress;
    private final StringProperty walletLabel;
    private final StringProperty walletImageFileName;
    private final DoubleProperty balance;
    private final BooleanProperty isMain;
    private final BooleanProperty isActive;
    private final ObjectProperty<Client> client;
    private final ObjectProperty<Node> node;
    private ArrayList<Block> blocks;


    public Wallet() {
        this(null,null);
    }

    public Wallet(String walletLabel, String walletAddress) {
        this.walletLabel = new SimpleStringProperty(walletLabel);
        this.walletAddress = new SimpleStringProperty(walletAddress);

        this.walletImageFileName = new SimpleStringProperty("image.png");
        this.balance = new SimpleDoubleProperty(10);
        this.isMain = new SimpleBooleanProperty(false);
        this.isActive = new SimpleBooleanProperty(true);
        this.client = new SimpleObjectProperty<>(new Client());
        this.node = new SimpleObjectProperty<>(new Node());
        this.blocks = new ArrayList<>();

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWalletAddress() {
        return walletAddress.get();
    }

    public StringProperty walletAddressProperty() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress.set(walletAddress);
    }

    public String getWalletLabel() {
        return walletLabel.get();
    }

    public StringProperty walletLabelProperty() {
        return walletLabel;
    }

    public void setWalletLabel(String walletLabel) {
        this.walletLabel.set(walletLabel);
    }

    public String getWalletImageFileName() {
        return walletImageFileName.get();
    }

    public StringProperty walletImageFileNameProperty() {
        return walletImageFileName;
    }

    public void setWalletImageFileName(String walletImageFileName) {
        this.walletImageFileName.set(walletImageFileName);
    }

    public double getBalance() {
        return balance.get();
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public boolean isIsMain() {
        return isMain.get();
    }

    public BooleanProperty isMainProperty() {
        return isMain;
    }

    public void setIsMain(boolean isMain) {
        this.isMain.set(isMain);
    }

    public boolean isIsActive() {
        return isActive.get();
    }

    public BooleanProperty isActiveProperty() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive.set(isActive);
    }

    public Client getClient() {
        return client.get();
    }

    public ObjectProperty<Client> clientProperty() {
        return client;
    }

    public void setClient(Client client) {
        this.client.set(client);
    }

    public Node getNode() {
        return node.get();
    }

    public ObjectProperty<Node> nodeProperty() {
        return node;
    }

    public void setNode(Node node) {
        this.node.set(node);
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) && Objects.equals(walletAddress, wallet.walletAddress) && Objects.equals(walletLabel, wallet.walletLabel) && Objects.equals(walletImageFileName, wallet.walletImageFileName) && Objects.equals(balance, wallet.balance) && Objects.equals(isMain, wallet.isMain) && Objects.equals(isActive, wallet.isActive) && Objects.equals(client, wallet.client) && Objects.equals(node, wallet.node) && Objects.equals(blocks, wallet.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, walletAddress, walletLabel, walletImageFileName, balance, isMain, isActive, client, node, blocks);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", walletAddress=" + walletAddress +
                ", walletLabel=" + walletLabel +
                ", walletImageFileName=" + walletImageFileName +
                ", balance=" + balance +
                ", isMain=" + isMain +
                ", isActive=" + isActive +
                ", client=" + client +
                ", node=" + node +
                ", blocks=" + blocks +
                '}';
    }
}
