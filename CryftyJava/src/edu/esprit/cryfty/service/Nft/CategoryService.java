package edu.esprit.cryfty.service.Nft;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    public void addCategory(Category category){
        String request = "insert into category(name,creation_date,nbr_nft,nbr_sub_category) values(?,?,0,0) ";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setString(1,category.getName());
            pst.setObject(2,category.getCreationDate());
            pst.executeUpdate();
            System.out.println("Category ajouté.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void updateCategory(Category category){
        String request = "update category set name='"+category.getName()+"' where id="+category.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Category updated");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteCategory(Category category){
        String request = "Delete from category where id= ?";
        try{
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            pst.setInt(1,category.getId());
            if(pst.executeUpdate()==0)
                System.out.println("Category not found");
            else
                System.out.println("Category removed");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<Category> showCategories(){
        List categories = new ArrayList();
        String request = "select * from category";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
                category.setNbrNfts(rs.getInt("nbr_nft"));
                category.setNbrSubCategories(rs.getInt("nbr_sub_category"));

                categories.add(category);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return categories;
    }

    public void incrementNbrNfts(Category category){
        category.setNbrNfts(category.getNbrNfts()+1);
        String request = "update category set nbr_nft="+category.getNbrNfts()+" where id="+category.getId();
            try{
                Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
                st.executeUpdate(request);
                System.out.println("Number of nfts of "+category.getName()+" has been updated.");
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
    }

    public void incrementNbrSubCategories(Category category){
        category.setNbrSubCategories(category.getNbrSubCategories()+1);
        String request = "update category set nbr_sub_category="+category.getNbrSubCategories()+" where id="+category.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Number of subCategories of "+category.getName()+" has been updated.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void decrementNbrNfts(Category category){
        category.setNbrNfts(category.getNbrNfts()-1);
        String request = "update category set nbr_nft="+category.getNbrNfts()+" where id="+category.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Number of nfts of "+category.getName()+" has been updated.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void decrementNbrSubCategories(Category category){
        category.setNbrSubCategories(category.getNbrSubCategories()-1);
        String request = "update category set nbr_sub_category="+category.getNbrSubCategories()+" where id="+category.getId();
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            st.executeUpdate(request);
            System.out.println("Number of subCategories of "+category.getName()+" has been updated.");
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Category findCategoryByName(String name){
        Category category = new Category();
        String request = "select * from category where name LIKE '"+name+"'";
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
                category.setNbrNfts(rs.getInt("nbr_nft"));
                category.setNbrSubCategories(rs.getInt("nbr_sub_category"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return category;
    }

    public Category findCategoryById(int id){
        Category category = new Category();
        String request = "select * from category where id="+id;
        try{
            Statement st = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = st.executeQuery(request);
            while(rs.next()){
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setCreationDate((LocalDateTime) rs.getObject("creation_date"));
                category.setNbrNfts(rs.getInt("nbr_nft"));
                category.setNbrSubCategories(rs.getInt("nbr_sub_category"));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return category;
    }
}
