package edu.esprit.cryfty.service.blogs;

import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.entity.blogs.BlogComment;
import edu.esprit.cryfty.service.user.UserService;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BlogsCommentsService {
    public void addComment(BlogComment comment){
        String request = "insert into blog_comment(comment,post_date,article_id,user_id,likes,dislikes) "
                + "values(?,?,?,?,0,0)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,comment.getComment());
            pst.setObject(2, comment.getPostDate());
            pst.setInt(3, comment.getArticle().getId());
            pst.setInt(4, comment.getUser().getId());

            pst.executeUpdate();
            System.out.println("Comment added");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }
    public void deleteComment(BlogComment comment){
        String request = "delete from blog_comment where id = ?";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1, comment.getId());
            if(pst.executeUpdate()==0)
                System.out.println("Comment does not exist");
            else
                System.out.println("Comment deleted");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<BlogComment> showCommentsByArticle(BlogArticles article){

        BlogsService blogSrv = new BlogsService();
        List<BlogArticles> articles = new ArrayList();
        articles= blogSrv.listerArticles();

        List<BlogComment> comments = new ArrayList();
        String request = "select * from blog_comment where article_id="+article.getId();
        try{
            Statement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            while(rs.next()){
                BlogComment comment = new BlogComment();
                comment.setId(rs.getInt("id"));

                UserService userService = new UserService();
                User user = userService.findUserById(rs.getInt("user_id"));
                comment.setUser(user);

                comment.setComment(rs.getString("comment"));
                comment.setPostDate(rs.getDate("post_date"));
                comment.setLikes(rs.getInt("likes"));
                comment.setDislikes(rs.getInt("dislikes"));
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
