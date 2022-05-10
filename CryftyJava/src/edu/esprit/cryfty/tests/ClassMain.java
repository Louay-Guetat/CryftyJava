package edu.esprit.cryfty.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.esprit.cryfty.entity.Client;
import edu.esprit.cryfty.entity.Coin;
import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.entity.Node;
import edu.esprit.cryfty.service.Nft.NftService;
import edu.esprit.cryfty.service.NodeService;
import edu.esprit.cryfty.utils.MailUtil;
import edu.esprit.cryfty.utils.ReadJson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClassMain {
    public static void main(String[]args) throws IOException {
        try {
            MailUtil.sendMail("1");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        /*Nft nft = new Nft(25,"","TestIntellij","testDescrption",(float)13.0,nd,ct,subCt,client,now,0);
        NftService nftSrv = new NftService();

        System.out.println(nftSrv.showNfts());
        CategoryService categoryService = new CategoryService();
        System.out.println(categoryService.showCategories());
        SubCategoryService subCategoryService = new SubCategoryService();
        System.out.println(subCategoryService.showSubCategories());
        NftCommentService commentSrv = new NftCommentService();
        System.out.println(commentSrv.showCommentsByNft(nft));*/

        /*Client cl = new Client(1,"username","[\"ROLE_USER\"]","123456","Louay","Guetat","hhhh@gmail.com",55160398,24,"hhhh","h","h");
        ClientService clientSrv = new ClientService();
        clientSrv.addClient(cl);*/


    }
}
