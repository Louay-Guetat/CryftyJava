package edu.esprit.cryfty.service.Nft;

import edu.esprit.cryfty.entity.Nft.Category;
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

            CategoryService categoryService = new CategoryService();
            categoryService.incrementNbrSubCategories(subCategory.getCategory());
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void updateSubCategory(SubCategory subCategory){
        String request = "update sub_category set name='"+subCategory.getName()+
                "', category_id="+subCategory.getCategory().getId()+" where id="+subCategory.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("SubCategory updated");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteSubCategory(SubCategory subCategory){
        String request = "Delete from sub_category where id= ?";
        CategoryService categoryService = new CategoryService();
        Category category = subCategory.getCategory();
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1,subCategory.getId());
            if(pst.executeUpdate()==0)
                System.out.println("SubCategory not found");
            else{
                categoryService.decrementNbrSubCategories(category);
                System.out.println("SubCategory removed");
            }

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<SubCategory> showSubCategories(){
        CategoryService categorySrv = new CategoryService();
        List<Category>categories = categorySrv.showCategories();

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
                for(int i=0;i<categories.size();i++){
                    if(rs.getInt("category_id") == categories.get(i).getId()){
                        subCategory.setCategory(categories.get(i));
                    }
                }
                subCategories.add(subCategory);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return subCategories;
    }

    public void incrementNbrNfts(SubCategory subCategory){
        subCategory.setNbrNfts(subCategory.getNbrNfts()+1);
        String request = "update sub_category set nbr_nft="+subCategory.getNbrNfts()+" where id="+subCategory.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Number of nfts of "+subCategory.getName()+" has been updated.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void decrementNbrNfts(SubCategory subCategory){
        subCategory.setNbrNfts(subCategory.getNbrNfts()-1);
        String request = "update sub_category set nbr_nft="+subCategory.getNbrNfts()+" where id="+subCategory.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Number of nfts of "+subCategory.getName()+" has been updated.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public SubCategory findSubCategoryByName(String name){
        SubCategory subcategory = new SubCategory();
        String request = "select * from sub_category where name LIKE '"+name+"'";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                subcategory.setId(rs.getInt("id"));
                subcategory.setName(rs.getString("name"));
                subcategory.setCreationDate(rs.getDate("creation_date"));
                subcategory.setNbrNfts(rs.getInt("nbr_nft"));
                CategoryService categoryService = new CategoryService();
                categoryService.findCategoryById(rs.getInt("category_id"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return subcategory;
    }

    public SubCategory findSubCategoryById(int id){
        SubCategory subcategory = new SubCategory();
        String request = "select * from sub_category where id="+id;
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                subcategory.setId(rs.getInt("id"));
                subcategory.setName(rs.getString("name"));
                subcategory.setCreationDate(rs.getDate("creation_date"));
                subcategory.setNbrNfts(rs.getInt("nbr_nft"));
                CategoryService categoryService = new CategoryService();
                categoryService.findCategoryById(rs.getInt("category_id"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return subcategory;
    }
}
