package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.NftComment;
import edu.esprit.cryfty.service.Nft.NftCommentService;
import edu.esprit.cryfty.service.Nft.NftService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.Main.currentUser;
import static edu.esprit.cryfty.gui.fxml.Controller.nftClicked;
import static edu.esprit.cryfty.gui.fxml.ItemController.waterMark;

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
    public Button btnComment;
    @javafx.fxml.FXML
    private ImageView imNft;

    private Nft nft = nftClicked;
    @javafx.fxml.FXML
    private VBox boxComment;
    public static NftComment comment;

    private char markTxt = 'A';
    @FXML
    private AnchorPane anchorPane;
    private static final int MIN_PIXELS = 10;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneContent.toFront();
        imNft.setOnMouseClicked(this::onClick);
        imNft.setOnScroll(this::imageScrolled);

        Rectangle clip = new Rectangle(
                imNft.getFitWidth(), imNft.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imNft.setClip(clip);

        createView();
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
            nftComment.setPostDate(LocalDateTime.now());
            nftComment.setUser(currentUser);
            nftCommentService.addComment(nftComment);
            comment = nftComment;
            Node node = FXMLLoader.load(getClass().getResource("OneComment.fxml"));
            boxComment.getChildren().add(node);
            tfComment.clear();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty comment");
            alert.setHeaderText("Please you need to write a comment");
            alert.showAndWait();
        }
    }

    public void createView(){
        if(!currentUser.equals(nft.getOwner())){
            btnDelete.setVisible(false);
            btnUpdate.setVisible(false);
        }
        else{
            btnDelete.setVisible(true);
            btnUpdate.setVisible(true);
        }
        lblTitle.setText(nft.getTitle());
        if(nft.getDescription().isEmpty()){
            lblDescription.setText("Nothing to mention.");
        }else{
            lblDescription.setText(nft.getDescription());
        }
        lblCategory.setText(nft.getCategory().getName());
        lblSubCategory.setText(nft.getSubCategory().getName());
        lblCurrency.setText(nft.getCurrency().getCoinCode());
        lblPrice.setText(nft.getPrice() + "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Created in 'dd/MM/yyyy 'at' HH:mm");
        lblCreationDate.setText(nft.getCreationDate().format(formatter));
        lblOwner.setText(nft.getOwner().getUsername());

        try {
            File watermarkImageFile  = new File("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Nfts\\" + nft.getImage());
            File sourceImage = new File("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\LogoNoText.png");
            Image image;
            if(nft.getOwner().getId() != currentUser.getId()){
                File destinationImage = waterMark(sourceImage,watermarkImageFile);
                image = new Image(new FileInputStream(destinationImage));
                BoxBlur blur = new BoxBlur();
                blur.setHeight(2);
                blur.setWidth(2);
                blur.setIterations(1);
                imNft.setEffect(blur);
            }
            else{
                image = new Image(new FileInputStream(watermarkImageFile));
            }

            imNft.setImage(image);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    private void onKeyPressed(final KeyEvent event) {
        System.out.println(event.getCode().getName());
        if(event.getCode().getName().equals("Enter")){
            try{
                addComment();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    private void imageScrolled(ScrollEvent event) {
        // When holding CTRL mouse wheel will be used for zooming
        imNft.toFront();
        if (event.isControlDown()) {
            double delta = event.getDeltaY();
            double adjust = delta / 1000.0;
            double zoom = Math.min(10, Math.max(0.1, imNft.getScaleX() + adjust));
            setImageZoom(zoom);
            event.consume();
        }
    }

    private void placeMarker(double sceneX, double sceneY) {
        Circle circle = new Circle(2);
        circle.setStroke(Color.WHITE);
        circle.setTranslateY(-12);
        Label marker = new Label(String.valueOf(markTxt), circle);
        marker.setTextFill(Color.WHITE);
        markTxt++;
        Point2D p = anchorPane.sceneToLocal(sceneX, sceneY);
        AnchorPane.setTopAnchor(marker, p.getY());
        AnchorPane.setLeftAnchor(marker, p.getX());
        anchorPane.getChildren().add(marker);
    }

    private void setImageZoom(double factor) {
        imNft.setScaleX(factor);
        imNft.setScaleY(factor);
    }

    public void onClick(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            placeMarker(event.getSceneX(), event.getSceneY());
        }
    }

}
