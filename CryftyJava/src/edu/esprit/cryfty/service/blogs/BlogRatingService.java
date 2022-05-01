package edu.esprit.cryfty.service.blogs;

import edu.esprit.cryfty.entity.User;
import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.entity.blogs.BlogComment;
import edu.esprit.cryfty.entity.blogs.BlogRating;
import edu.esprit.cryfty.service.UserService;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BlogRatingService {
    public void addRating(BlogRating comment){
        String request = "insert into blog_rating(rating,article_id,user_id) "
                + "values(?,?,?)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setDouble(1,comment.getRating());
            pst.setInt(2, comment.getArticle().getId());
            pst.setInt(3, comment.getUser().getId());

            pst.executeUpdate();
            System.out.println("Rating added");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }
    public void deleteRating(BlogRating comment){
        String request = "delete from blog_rating where id = ?";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1, comment.getId());
            if(pst.executeUpdate()==0)
                System.out.println("rating does not exist");
            else
                System.out.println("rating deleted");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }


    public List<BlogRating> showRatingsByArticle(BlogArticles article){

        BlogsService blogSrv = new BlogsService();
        List<BlogArticles> articles = new ArrayList();
        articles= blogSrv.listerArticles();

        List<BlogRating> comments = new ArrayList();
        String request = "select * from blog_rating where article_id="+article.getId();
        try{
            Statement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            while(rs.next()){
                BlogRating comment = new BlogRating();
                comment.setId(rs.getInt("id"));

                UserService userService = new UserService();
                User user = userService.findUserById(rs.getInt("user_id"));
                comment.setUser(user);

                comment.setRating(rs.getDouble("rating"));

                comment.setArticle(article);

                for(int i=0;i<articles.size();i++){
                    if(articles.get(i).getId() == rs.getInt("article_id"))
                        comment.setArticle(articles.get(i));
                }
                comments.add(comment);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return comments;
    }
}
