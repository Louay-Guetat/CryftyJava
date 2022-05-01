package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.service.blogs.BlogsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import static edu.esprit.cryfty.gui.fxml.ItemBController.r;
import static edu.esprit.cryfty.gui.fxml.ListeArticlesController.article;
import static edu.esprit.cryfty.gui.fxml.ItemBController.thisArticle;
import static edu.esprit.cryfty.gui.fxml.BoarticleController.arti;

public class UpdatearticleController implements Initializable {
    @javafx.fxml.FXML
    private DatePicker date;
    @javafx.fxml.FXML
    private TextArea contents;
    @javafx.fxml.FXML
    private ImageView imArt;
    @javafx.fxml.FXML
    private TextField author;
    @javafx.fxml.FXML
    private TextField title;
    @javafx.fxml.FXML
    private ComboBox category;
    ObservableList<String> cat = FXCollections.observableArrayList("Memes","Art","Photography","Music","Crypto");
    @javafx.fxml.FXML
    private Button btnUpdate;
    public static BlogArticles Post;

    public void initialize(URL arg0, ResourceBundle arg1) {
        category.setItems(cat);
      //  thisArticle = article;
        System.out.println(arti.toString());
        title.setText(arti.getTitle());
        contents.setText(arti.getContents());
        category.setValue(arti.getCategory());
        author.setText(arti.getAuthor());
        Post=arti;


    }
    @javafx.fxml.FXML
    public void comboChange(ActionEvent actionEvent) {
    }

    @FXML
    public void updatearticle() throws IOException {
      /*  title.setText(r.getTitle());
        contents.setText(r.getContents());*/

        DatePicker bdate = date;
        //String bcategory = (String) category.getValue();

   //     author.setText(r.getAuthor());

        String btitle = title.getText();
        String bcontents = contents.getText();


        String bcategory = (String) category.getValue();
        String bauthor = author.getText();

        arti.setTitle(title.getText());
        arti.setContents(contents.getText());
        arti.setCategory((String) category.getValue());
        arti.setAuthor(author.getText());
        arti.setDate(Date.valueOf(date.getValue()));
        BlogsService sr = new BlogsService();
        sr.updateArticle(arti,arti.getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boarticle.fxml"));
        Parent root2 = loader.load();
        btnUpdate.getScene().setRoot(root2);
    }
}
