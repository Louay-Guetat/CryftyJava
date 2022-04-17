package edu.esprit.cryfty.tests;

import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.NftCommentService;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import edu.esprit.cryfty.utils.DataSource;

import java.util.Date;

public class ClassMain {
    public static void main(String[]args){
        Client client = new Client();
        client.setId(1);
        Node nd = new Node();
        nd.setId(1);
        Category ct = new Category();
        ct.setId(1);
        SubCategory subCt = new SubCategory();
        subCt.setId(1);
        Date now = new Date();
        Nft nft = new Nft(25,"","TestIntellij","testDescrption",(float)13.0,nd,ct,subCt,client,now,0);
        NftService nftSrv = new NftService();

        System.out.println(nftSrv.showNfts());
        CategoryService categoryService = new CategoryService();
        System.out.println(categoryService.showCategories());
        SubCategoryService subCategoryService = new SubCategoryService();
        System.out.println(subCategoryService.showSubCategories());
        NftCommentService commentSrv = new NftCommentService();
        System.out.println(commentSrv.showCommentsByNft(nft));
    }
}