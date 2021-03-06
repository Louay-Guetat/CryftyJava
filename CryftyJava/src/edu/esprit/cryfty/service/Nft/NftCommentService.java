package edu.esprit.cryfty.service.Nft;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.NftComment;
import edu.esprit.cryfty.entity.User.User;
import edu.esprit.cryfty.service.user.UserService;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NftCommentService {

    public void addComment(NftComment comment){
        String request = "insert into nft_comment(comment,post_date,nft_id,user_id,likes,dislikes) "
                + "values(?,?,?,?,0,0)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,comment.getContent());
            pst.setObject(2, comment.getPostDate());
            pst.setInt(3, comment.getNft().getId());
            pst.setInt(4, comment.getUser().getId());

            pst.executeUpdate();
            System.out.println("Comment added");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void updateComment(NftComment comment){
        String request = "update nft_comment set comment='"+comment.getContent()+"' where id ="+comment.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Comment updated");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteComment(NftComment comment){
        String request = "delete from nft_comment where id = ?";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1, comment.getId());
            if(pst.executeUpdate()==0)
                System.out.println("Comment does not exist");
            else
                System.out.println("Comment removed");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<NftComment> showCommentsByNft(Nft nft){

        NftService nftSrv = new NftService();
        List<Nft> nfts = new ArrayList();
        nfts= nftSrv.showNfts();

        List<NftComment> comments = new ArrayList();
        String request = "select * from nft_comment where nft_id="+nft.getId();
        try{
            Statement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            while(rs.next()){
                NftComment comment = new NftComment();
                comment.setId(rs.getInt("id"));

                UserService userService = new UserService();
                User user = userService.findUserById(rs.getInt("user_id"));
                comment.setUser(user);

                comment.setContent(rs.getString("comment"));
                comment.setPostDate((LocalDateTime) rs.getObject("post_date"));
                comment.setLikes(rs.getInt("likes"));
                comment.setDislikes(rs.getInt("dislikes"));
                comment.setNft(nft);

                for(int i=0;i<nfts.size();i++){
                    if(nfts.get(i).getId() == rs.getInt("nft_id"))
                        comment.setNft(nfts.get(i));
                }
                comments.add(comment);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return comments;
    }
}
