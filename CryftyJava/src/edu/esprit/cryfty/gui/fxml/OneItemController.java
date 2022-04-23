package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.NftComment;
import edu.esprit.cryfty.service.Nft.NftCommentService;
import edu.esprit.cryfty.service.Nft.NftService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.Main.currentUser;
import static edu.esprit.cryfty.gui.fxml.Controller.nft;
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
    public static TextField tfComment;
    @javafx.fxml.FXML
    private ScrollPane scrollPaneComments;
    @javafx.fxml.FXML
    public Button btnComment;
    @javafx.fxml.FXML
    private ImageView imNft;

    private Nft nft = nftClicked;
    @javafx.fxml.FXML
    private VBox boxComment;
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

        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Nfts\\"+nft.getImage());
            Image image = new Image(inputstream);
            imNft.setImage(image);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        NftCommentService nftCommentService = new NftCommentService();
        List<NftComment> comments = nftCommentService.showCommentsByNft(nft);
        if(comments.size()==0){
            Label label = new Label("No comments here, please feel free to share your thought :).");
            label.setTextFill(Color.web("white"));
            boxComment.getChildren().add(label);
        }
        else{
            Node nodes[] = new Node[comments.size()];
            for(int i=0; i<comments.size();i++){
                try{
                    comment = comments.get(i);
                    nodes[i]= FXMLLoader.load(getClass().getResource("OneComment.fxml"));
                    boxComment.getChildren().add(nodes[i]);
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    @javafx.fxml.FXML
    public void deleteNft() throws IOException {
        NftService nftService = new NftService();
        nftService.deleteNft(nftClicked);
        Stage stage = (Stage) btnDelete.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @javafx.fxml.FXML
    public void updateNft() throws IOException {
        Scene scene = btnUpdate.getScene();
        scene.getWindow().hide();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("UpdateNft.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @javafx.fxml.FXML
    public void addComment() throws IOException {
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty comment");
            alert.setHeaderText("Please you need to write a comment");
            alert.showAndWait();
        }
    }

    public void setTfComment(String comment){
        tfComment.setText(comment);
    }
}
