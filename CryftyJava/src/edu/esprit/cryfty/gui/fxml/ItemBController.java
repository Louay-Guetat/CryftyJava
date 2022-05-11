package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.entity.blogs.BlogComment;
import edu.esprit.cryfty.service.blogs.BlogsCommentsService;
import edu.esprit.cryfty.service.blogs.BlogsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.fxml.ListeArticlesController.article;
import static edu.esprit.cryfty.gui.fxml.ListeArticlesController.thisArticle;


public class ItemBController implements Initializable {
   // private BlogArticles article;
    @FXML
    private javafx.scene.control.Label lblCategory;
    @FXML
    private ImageView imNft;
    @FXML
    private javafx.scene.control.Label lblOwner;
    @FXML
    private javafx.scene.control.Label lblCreationDate;
    @FXML
    private javafx.scene.control.Label lblCurrency;
    @FXML
    private javafx.scene.control.Label lblTitle;
    @FXML
    private Pane pnItem;
    @FXML
    private Label ida;
    //private MyListener myListener;
   private String id;
    public static BlogArticles r ;
    public static BlogArticles thisArticle;
    @FXML
    private Button blogd;
    public static BlogArticles Posta;
    public static String ayja;
    @FXML
    private Label aimg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTitle.setText(article.getTitle());
        lblCategory.setText(article.getCategory());
        lblCreationDate.setText(article.getDate()+"");
        aimg.setText(article.getImage());
        ida.setText(String.valueOf(article.getId()));
        id=String.valueOf(article.getId());
        lblOwner.setText(article.getAuthor());
        ayja=article.getImage();
        thisArticle = article;
        Posta=article;
        System.out.println(ayja);
        //Integer.valueOf(id)
        thisArticle.setId(Integer.valueOf(id));
        Posta.setId(Integer.valueOf(id));
        lblCurrency.setText(article.getContents());
        try {
            FileInputStream inputstream = new FileInputStream("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Blogs\\"+ayja);
            Image image = new Image(inputstream);
            imNft.setImage(image);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }




    @FXML
    public void blogdetail(ActionEvent actionEvent) throws IOException {
       // Posta=thisArticle;
        Posta.setId(Integer.valueOf(id));
        Posta.setTitle(lblTitle.getText());
        Posta.setContents(lblCurrency.getText());
        Posta.setCategory(lblCategory.getText());
        Posta.setAuthor(lblOwner.getText());
        Posta.setDate(Date.valueOf(lblCreationDate.getText()));
        Posta.setImage(aimg.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("postdetails.fxml"));
        Parent root2 = loader.load();


        blogd.getScene().setRoot(root2);
    }
}
