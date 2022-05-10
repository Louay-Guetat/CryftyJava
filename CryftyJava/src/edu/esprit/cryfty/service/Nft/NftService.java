package edu.esprit.cryfty.service.Nft;

import edu.esprit.cryfty.entity.User.Client;
import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.service.user.ClientService;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

        List<Category>categories = new ArrayList();
        categories = categorySrv.showCategories();

        List<SubCategory> subCategories = new ArrayList();
        subCategories = subCategorySrv.showSubCategories();

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
                nft.setCreationDate(rs.getDate("creation_date"));
                nft.setImage(rs.getString("image"));
                nft.setLikes(rs.getInt("likes"));

                for(int i=0;i<categories.size();i++){
                    if(rs.getInt("category_id") == categories.get(i).getId()){
                        nft.setCategory(categories.get(i));
                    }
                }
                for(int i=0; i<subCategories.size();i++){
                    if(rs.getInt("sub_category_id")==subCategories.get(i).getId()){
                        nft.setSubCategory(subCategories.get(i));
                    }
                }
                //nft.setCurrency(rs.getInt("currency_id"));
                ClientService clientSrv = new ClientService();
                Client client = clientSrv.getClientById(1);
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
}
