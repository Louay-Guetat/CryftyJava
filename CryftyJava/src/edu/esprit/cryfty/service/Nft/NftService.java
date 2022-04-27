package edu.esprit.cryfty.service.Nft;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.service.ClientService;
import edu.esprit.cryfty.service.NodeService;
import edu.esprit.cryfty.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class NftService {

    public void addNft(Nft nft){
        String request ="insert into nft(image,title,description,price,currency_id,category_id,sub_category_id,owner_id,"
                + "creation_date,likes) values(?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,nft.getImage());
            pst.setString(2,nft.getTitle());
            pst.setString(3,nft.getDescription());
            pst.setFloat(4,nft.getPrice());
            pst.setInt(5,nft.getCurrency().getId());
            pst.setInt(6,nft.getCategory().getId());
            pst.setInt(7,nft.getSubCategory().getId());
            pst.setInt(8,nft.getOwner().getId());
            pst.setObject(9,nft.getCreationDate());
            pst.setInt(10,0);

            pst.executeUpdate();
            System.out.println("Nft added successfully :)\n");

            CategoryService categorySrv = new CategoryService();
            categorySrv.incrementNbrNfts(nft.getCategory());

            SubCategoryService subCategoryService = new SubCategoryService();
            subCategoryService.incrementNbrNfts(nft.getSubCategory());

        }catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public void updateNft(Nft nft){
        String request = "update nft set title=?, description=?, category_id=?, sub_category_id=?, currency_id=?, price=?" +
                "where nft.id = "+nft.getId();
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,nft.getTitle());
            pst.setString(2,nft.getDescription());
            pst.setInt(3,nft.getCategory().getId());
            pst.setInt(4,nft.getSubCategory().getId());
            pst.setInt(5,nft.getCurrency().getId());
            pst.setFloat(6,nft.getPrice());

            pst.executeUpdate();
            System.out.println("Nft updated");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteNft(Nft nft){
        String request = "delete from nft where id = ?";
        CategoryService categoryService = new CategoryService();
        Category category = nft.getCategory();

        SubCategoryService subCategoryService = new SubCategoryService();
        SubCategory subCategory = nft.getSubCategory();
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1,nft.getId());
            if(pst.executeUpdate()==0)
                System.out.println("Nft non existant");
            else{
                System.out.println("Nft supprimé");
                categoryService.decrementNbrNfts(category);
                subCategoryService.decrementNbrNfts(subCategory);
            }

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<Nft> showNfts(){
        CategoryService categorySrv = new CategoryService();
        SubCategoryService subCategorySrv = new SubCategoryService();
        NodeService nodeService = new NodeService();
        ClientService clientSrv = new ClientService();

        List<Nft> nfts = new ArrayList();
        String request = "select * from nft";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Nft nft = new Nft();
                nft.setId(rs.getInt("id"));
                nft.setTitle(rs.getString("title"));
                nft.setDescription(rs.getString("description"));
                nft.setPrice(rs.getFloat("price"));
                nft.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
                nft.setImage(rs.getString("image"));
                nft.setLikes(rs.getInt("likes"));

                nft.setCategory(categorySrv.findCategoryById(rs.getInt("category_id")));
                nft.setSubCategory(subCategorySrv.findSubCategoryById(rs.getInt("sub_category_id")));
                nft.setCurrency(nodeService.getNodeById(rs.getInt("currency_id")));

                Client client = clientSrv.getClientById(rs.getInt("owner_id"));
                nft.setOwner(client);

                nfts.add(nft);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return nfts;
    }

    public List<Nft> nftsByCategory(){
        CategoryService categorySrv = new CategoryService();
        SubCategoryService subCategorySrv = new SubCategoryService();
        NodeService nodeService = new NodeService();
        ClientService clientSrv = new ClientService();

        List<Nft> nfts = new ArrayList();
        String request = "";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()) {
                Nft nft = new Nft();
                nft.setId(rs.getInt("id"));
                nft.setTitle(rs.getString("title"));
                nft.setDescription(rs.getString("description"));
                nft.setPrice(rs.getFloat("price"));
                nft.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
                nft.setImage(rs.getString("image"));
                nft.setLikes(rs.getInt("likes"));

                nft.setCategory(categorySrv.findCategoryById(rs.getInt("category_id")));
                nft.setSubCategory(subCategorySrv.findSubCategoryById(rs.getInt("sub_category_id")));
                nft.setCurrency(nodeService.getNodeById(rs.getInt("currency_id")));

                Client client = clientSrv.getClientById(rs.getInt("owner_id"));
                nft.setOwner(client);

                nfts.add(nft);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return nfts;
    }

    public List<Nft> nftsBySubCategory(){
        CategoryService categorySrv = new CategoryService();
        SubCategoryService subCategorySrv = new SubCategoryService();
        NodeService nodeService = new NodeService();
        ClientService clientSrv = new ClientService();

        List<Nft> nfts = new ArrayList();
        String request = "";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()) {
                Nft nft = new Nft();
                nft.setId(rs.getInt("id"));
                nft.setTitle(rs.getString("title"));
                nft.setDescription(rs.getString("description"));
                nft.setPrice(rs.getFloat("price"));
                nft.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
                nft.setImage(rs.getString("image"));
                nft.setLikes(rs.getInt("likes"));

                nft.setCategory(categorySrv.findCategoryById(rs.getInt("category_id")));
                nft.setSubCategory(subCategorySrv.findSubCategoryById(rs.getInt("sub_category_id")));
                nft.setCurrency(nodeService.getNodeById(rs.getInt("currency_id")));

                Client client = clientSrv.getClientById(rs.getInt("owner_id"));
                nft.setOwner(client);

                nfts.add(nft);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return nfts;
    }

    public List<Nft> nftsByCurrency(){
        CategoryService categorySrv = new CategoryService();
        SubCategoryService subCategorySrv = new SubCategoryService();
        NodeService nodeService = new NodeService();
        ClientService clientSrv = new ClientService();

        List<Nft> nfts = new ArrayList();
        String request = "";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()) {
                Nft nft = new Nft();
                nft.setId(rs.getInt("id"));
                nft.setTitle(rs.getString("title"));
                nft.setDescription(rs.getString("description"));
                nft.setPrice(rs.getFloat("price"));
                nft.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
                nft.setImage(rs.getString("image"));
                nft.setLikes(rs.getInt("likes"));

                nft.setCategory(categorySrv.findCategoryById(rs.getInt("category_id")));
                nft.setSubCategory(subCategorySrv.findSubCategoryById(rs.getInt("sub_category_id")));
                nft.setCurrency(nodeService.getNodeById(rs.getInt("currency_id")));

                Client client = clientSrv.getClientById(rs.getInt("owner_id"));
                nft.setOwner(client);

                nfts.add(nft);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return nfts;
    }

    public void addLike(Nft nft){
        String request = "update nft set likes="+(nft.getLikes()+1)+" where id="+nft.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Like added");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void removeLike(Nft nft){
        String request = "update nft set likes="+(nft.getLikes()-1)+" where id="+nft.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Like added");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<Nft> getNftsByTitle(String title){
        CategoryService categorySrv = new CategoryService();
        SubCategoryService subCategorySrv = new SubCategoryService();
        NodeService nodeService = new NodeService();
        ClientService clientSrv = new ClientService();

        ObservableList<Nft> nfts = FXCollections.observableArrayList();

        String request = "select * from nft where title like '%"+title+"%'";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Nft nft = new Nft();
                nft.setId(rs.getInt("id"));
                nft.setTitle(rs.getString("title"));
                nft.setDescription(rs.getString("description"));
                nft.setPrice(rs.getFloat("price"));
                nft.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
                nft.setImage(rs.getString("image"));
                nft.setLikes(rs.getInt("likes"));

                nft.setCategory(categorySrv.findCategoryById(rs.getInt("category_id")));
                nft.setSubCategory(subCategorySrv.findSubCategoryById(rs.getInt("sub_category_id")));
                nft.setCurrency(nodeService.getNodeById(rs.getInt("currency_id")));

                Client client = clientSrv.getClientById(rs.getInt("owner_id"));
                nft.setOwner(client);
                nfts.add(nft);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return nfts;
    }


}
