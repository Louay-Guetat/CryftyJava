package edu.esprit.cryfty.service.Nft;

import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryService {

    public void addSubCategory(SubCategory subCategory){
        String request = "insert into sub_category(name,creation_date,category_id,nbr_nft) values(?,?,?,0) ";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,subCategory.getName());
            pst.setObject(2,subCategory.getCreationDate());
            pst.setInt(3,subCategory.getCategory().getId());
            pst.executeUpdate();
            System.out.println("Subcategory ajouté.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void updateSubCategory(SubCategory category){

    }

    public void deleteSubCategory(SubCategory subCategory){
        String request = "Delete from sub_category where id= ?";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1,subCategory.getId());
            if(pst.executeUpdate()==0)
                System.out.println("SubCategory not found");
            else
                System.out.println("SubCategory removed");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<SubCategory> showSubCategories(){
        List subCategories = new ArrayList();
        String request = "select * from sub_category";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                SubCategory subCategory = new SubCategory();
                subCategory.setId(rs.getInt("id"));
                subCategory.setName(rs.getString("name"));
                subCategory.setCreationDate(rs.getDate("creation_date"));
                subCategory.setNbrNfts(rs.getInt("nbr_nft"));
                // subCategory.setCategory((Category)rs.getObject("category_id"));

                subCategories.add(subCategory);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return subCategories;
    }
}
