package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.service.Nft.NftService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.List;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.fxml.Controller.nft;

public class ItemController implements Initializable{
    @FXML
    private Pane pnItem;
    @FXML
    private ImageView imNft;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblCreationDate;
    @FXML
    private Label lblOwner;
    @FXML
    private Label lblCategory;
    @FXML
    private Label lblSubCategory;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblCurrency;
    @FXML
    private Label lblLikes;
    public static Nft thisNft = nft;
    @FXML
    private Button btnViewNft;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTitle.setText(nft.getTitle());
        lblCategory.setText(nft.getCategory().getName());
        lblCreationDate.setText(nft.getCreationDate()+"");
        lblLikes.setText(nft.getLikes()+"");
        lblOwner.setText(nft.getOwner().getUsername());
        lblPrice.setText(nft.getPrice()+"");
        lblSubCategory.setText(nft.getSubCategory().getName());
        lblCurrency.setText(nft.getCurrency().getCoinCode());
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Nfts\\"+nft.getImage());
            Image image = new Image(inputstream);
            imNft.setImage(image);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
