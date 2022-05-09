package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.entity.blogs.BlogComment;
import edu.esprit.cryfty.entity.blogs.BlogRating;
import edu.esprit.cryfty.service.blogs.BlogRatingService;
import edu.esprit.cryfty.service.blogs.BlogsCommentsService;
import edu.esprit.cryfty.service.blogs.BlogsService;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class BoarticleController implements Initializable {

    @javafx.fxml.FXML
    private TableView table;
    @javafx.fxml.FXML
    private TextField delete;
    @javafx.fxml.FXML
    private TextField idupdate;

   public static BlogArticles arti;
    @javafx.fxml.FXML
    private Button btnAddBA;
    @javafx.fxml.FXML
    private ImageView logo;
    @javafx.fxml.FXML
    private Button addbtn;
    @javafx.fxml.FXML
    private Button deletebtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int usersn;
        double sommerat=0;
        double moy;

        TableColumn<BlogArticles, Integer> id = new TableColumn<BlogArticles, Integer>("Id");
        id.setCellValueFactory(new PropertyValueFactory<BlogArticles, Integer>("id"));
        TableColumn<BlogArticles, String> title = new TableColumn<BlogArticles, String>("Title");
        title.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("title"));

        TableColumn<BlogArticles, String> category = new TableColumn<BlogArticles, String>("Category");
        category.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("category"));

        TableColumn<BlogArticles, Date> date = new TableColumn<BlogArticles, Date>("Date");
        date.setCellValueFactory(new PropertyValueFactory<BlogArticles, Date>("date"));

        TableColumn<BlogArticles, String> contents = new TableColumn<BlogArticles, String>("Contents");
        contents.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("contents"));

        TableColumn<BlogArticles, String> author = new TableColumn<BlogArticles, String>("Author");
        author.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("author"));

        TableColumn<BlogArticles, String> rating = new TableColumn<BlogArticles, String>("Rating");
        rating.setCellValueFactory(new PropertyValueFactory<BlogArticles, String>("ratingMoy"));




        table.getColumns().add(id);
        table.getColumns().add(title);
        table.getColumns().add(contents);
        table.getColumns().add(category);
        table.getColumns().add(date);

        table.getColumns().add(author);
        table.getColumns().add(rating);





        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
     //   tablerat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        BlogsService Service = new BlogsService();
        List<BlogArticles> articles = BlogsService.listerArticles();
        BlogRatingService moyrating = new BlogRatingService();

        for(int i=0;i<articles.size();i++ ){

            List<BlogRating> ratings = moyrating.showRatingsByArticle(articles.get(i));
            usersn=ratings.size();

                    for(int k=0;k<usersn;k++){
                          sommerat=sommerat+ratings.get(k).getRating();
                    }
            moy=(sommerat/(5*usersn))*100;
             sommerat=0;
             articles.get(i).setRatingMoy(String.valueOf(moy)+" %");


            table.getItems().add(articles.get(i));


            System.out.println(moy);




        }






    }


    public void add(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddArticle.fxml"));
        Parent root2 = loader.load();


        addbtn.getScene().setRoot(root2);
    }

    public void delete(javafx.event.ActionEvent actionEvent) throws IOException {
        int i = Integer.valueOf(delete.getText());
        BlogArticles article =null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("do you want to delete  this article?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            BlogsService articleService = new BlogsService();
            BlogsCommentsService articleCommentService = new BlogsCommentsService();
            List<BlogArticles> articles = articleService.listerArticles();
            for(int k=0;k<articles.size();k++){
                if(articles.get(k).getId()==i){
                    article=articles.get(k);

                }
            }

            List<BlogComment> comments = articleCommentService.showCommentsByArticle(article);
            for (int j = 0; j < comments.size(); j++) {
                articleCommentService.deleteComment(comments.get(j));

            }

            articleService.deleteArticle(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("boarticle.fxml"));
            Parent root2 = loader.load();


            deletebtn.getScene().setRoot(root2);


        }
    }






    public void update(javafx.event.ActionEvent actionEvent) throws IOException {
        int i=Integer.valueOf(idupdate.getText());
        BlogsService articleService = new BlogsService();

        List<BlogArticles> articles = articleService.listerArticles();
        for(int k=0;k<articles.size();k++){
            if(articles.get(k).getId()==i){
                arti=articles.get(k);

            }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updatearticle.fxml"));
        Parent root2 = loader.load();


        btnAddBA.getScene().setRoot(root2);

    }







}
