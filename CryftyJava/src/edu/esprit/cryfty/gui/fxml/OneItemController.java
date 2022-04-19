package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.NftComment;
import edu.esprit.cryfty.service.Nft.NftCommentService;
import edu.esprit.cryfty.service.Nft.NftService;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.Main.currentUser;
import static edu.esprit.cryfty.gui.fxml.Controller.nftClicked;

public class OneItemController implements Initializable {
    @javafx.fxml.FXML
    private Label lblCategory;
    @javafx.fxml.FXML
    private Label lblOwner;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Label lblCreationDate;
    @javafx.fxml.FXML
    private Button btnUpdate;
    @javafx.fxml.FXML
    private Label lblSubCategory;
    @javafx.fxml.FXML
    private Label lblPrice;
    @javafx.fxml.FXML
    private Label lblCurrency;
    @javafx.fxml.FXML
    private Label lblLikes;
    @javafx.fxml.FXML
    private Label lblTitle;
    @javafx.fxml.FXML
    private Label lblDescription;
    @javafx.fxml.FXML
    private Pane paneContent;
    @javafx.fxml.FXML
    private Pane paneComment;
    @javafx.fxml.FXML
    private TextField tfComment;
    @javafx.fxml.FXML
    private ScrollPane scrollPaneComments;
    @javafx.fxml.FXML
    private Button btnComment;
    @javafx.fxml.FXML
    private HBox boxComment;

    private Nft nft = nftClicked;
    public static NftComment comment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!currentUser.equals(nft.getOwner())){
            btnDelete.setVisible(false);
            btnUpdate.setVisible(false);
        }
        else{
            btnDelete.setVisible(true);
            btnUpdate.setVisible(true);
        }
        lblTitle.setText(nft.getTitle());
        lblDescription.setText(nft.getDescription());
        lblCategory.setText(nft.getCategory().getName());
        lblSubCategory.setText(nft.getSubCategory().getName());
        lblCurrency.setText(nft.getCurrency().getCoinCode());
        lblPrice.setText(nft.getPrice() + "");
        lblLikes.setText(nft.getLikes() + "");
        lblCreationDate.setText(nft.getCreationDate() + "");
        lblOwner.setText(nft.getOwner().getUsername());

        NftCommentService nftCommentService = new NftCommentService();
        List<NftComment> comments = nftCommentService.showCommentsByNft(nft);
        System.out.println(comments.size());
        Node nodes[] = new Node[comments.size()];
        for(int i=0; i<comments.size();i++){
            try{
                comment = comments.get(i);
                System.out.println(comment.toString());
                nodes[i]= FXMLLoader.load(getClass().getResource("OneComment.fxml"));
                boxComment.getChildren().add(nodes[i]);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    @javafx.fxml.FXML
    public void deleteNft() {
        NftService nftService = new NftService();
        nftService.deleteNft(nftClicked);
        Stage stage = (Stage) btnDelete.getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void updateNft() {

    }

    @javafx.fxml.FXML
    public void addComment(){
        if(!tfComment.getText().isEmpty()){
            NftCommentService nftCommentService = new NftCommentService();
            NftComment nftComment = new NftComment();
            nftComment.setContent(tfComment.getText());
            nftComment.setNft(nft);
            Date now = new Date();
            nftComment.setPostDate(now);
            nftComment.setUser(currentUser);
            nftCommentService.addComment(nftComment);
        }
        else{
            System.out.println("NOO");
        }
    }
}
