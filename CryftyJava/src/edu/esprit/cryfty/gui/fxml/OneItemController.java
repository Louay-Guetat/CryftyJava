//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.NftComment;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.gui.Controller;
import edu.esprit.cryfty.gui.Main;
import edu.esprit.cryfty.service.Nft.NftCommentService;
import edu.esprit.cryfty.service.Nft.NftService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import edu.esprit.cryfty.service.payment.CartService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static edu.esprit.cryfty.service.payment.CartService.nfts;

public class OneItemController implements Initializable {
    @FXML
    private Label lblCategory;
    @FXML
    private Label lblOwner;
    @FXML
    private Button btnDelete;
    @FXML
    private Label lblCreationDate;
    @FXML
    private Button btnUpdate;
    @FXML
    private Label lblSubCategory;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblCurrency;
    @FXML
    private Label lblLikes;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDescription;
    @FXML
    private Pane paneContent;
    private Nft nft;
    public static NftComment comment;
    @FXML
    private Button AddToCart;

    public OneItemController() {
        this.nft = Controller.nftClicked;
    }

    public void initialize(URL location, ResourceBundle resources) {
        if (!Main.currentUser.equals(this.nft.getOwner())) {
            this.btnDelete.setVisible(false);
            this.btnUpdate.setVisible(false);
        } else {
            this.btnDelete.setVisible(true);
            this.btnUpdate.setVisible(true);
        }

        this.lblTitle.setText(this.nft.getTitle());
        this.lblDescription.setText(this.nft.getDescription());
        this.lblCategory.setText(this.nft.getCategory().getName());
        this.lblSubCategory.setText(this.nft.getSubCategory().getName());
        this.lblCurrency.setText(this.nft.getCurrency().getCoinCode());
        this.lblPrice.setText(this.nft.getPrice() + "");
        this.lblLikes.setText(this.nft.getLikes() + "");
        this.lblCreationDate.setText(this.nft.getCreationDate() + "");
        this.lblOwner.setText(this.nft.getOwner().getUsername());
        NftCommentService nftCommentService = new NftCommentService();
        List<NftComment> comments = nftCommentService.showCommentsByNft(this.nft);
        System.out.println(comments.size());
        //Node[] nodes = new Node[comments.size()];

       /* for(int i = 0; i < comments.size(); ++i) {
            try {
                comment = (NftComment)comments.get(i);
                System.out.println(comment.toString());
                nodes[i] = (Node)FXMLLoader.load(this.getClass().getResource("fxml/OneComment.fxml"));
                //this.boxComment.getChildren().add(nodes[i]);
            } catch (IOException var8) {
                System.out.println(var8.getMessage());
            }
        }*/

    }

    @FXML
    public void deleteNft() {
        NftService nftService = new NftService();
        nftService.deleteNft(Controller.nftClicked);
        Stage stage = (Stage)this.btnDelete.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void updateNft() {
    }



    @FXML
    public void addNftToCart(ActionEvent actionEvent) throws IOException {

        ArrayList<Integer> nb=new ArrayList<>();
        CartService cartService=new CartService();
        Cart c=cartService.getCartById(1);
        //System.out.println(nfts);
        nfts.add(nft);
        //System.out.println(nfts);
        Cart carts=new Cart(nfts, c.getId());
        System.out.println(nft);
        for(int i=0;i<cartService.getNftfromCart().size();i++) {

            nb.add(cartService.getNftfromCart().get(i).getId());
        }
            if (nb.contains(nft.getId())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Nft already exists in your cart, choose another nft");
                alert.showAndWait();
                Stage window3 = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window3.hide();
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Stage window4 = new Stage();
                window4.setScene(new Scene(tableViewParent));
                window4.show();
            } else{

                cartService.addNftToCart(carts);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Nft added, go Check your Cart");
                alert.showAndWait();
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.hide();
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);
                //This line gets the Stage information
                Stage window2 = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window2.setScene(tableViewScene);
                window2.show();
            }



    }
}
