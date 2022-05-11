package edu.esprit.cryfty.tests;

public class ClassMain {
    public static void main(String[]args){
       /* Client client = new Client();
        client.setId(1);
        Node nd = new Node();
        nd.setId(1);
        Category ct = new Category();
        ct.setId(4);
        SubCategory subCt = new SubCategory();
        subCt.setId(6);
        subCt.setName("testUpdate");
        subCt.setCategory(ct);
        LocalDateTime now = LocalDateTime.now();
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

       /* Nft nft = new Nft(25,"","testUpdate","testDescrption",(float)13.0,nd,ct,subCt,client,now,0);
        NftService nftSrv = new NftService();
        System.out.println(nftSrv.showNfts());*/



       //test chat

     /*   MessageService msgService = new MessageService();
        GroupeChatService GrCRUD= new GroupeChatService();
        ArrayList <GroupeChat> groupsUser = GrCRUD.getGroups();
        //Conversation conv= msgService.getConversationById(124);
        for (int i=0;i<groupsUser.size();i++)
        {
            ArrayList <User> Participants = groupsUser.get(i).getParticipants();
            for (int j=0;j<Participants.size();j++)
            {
                if(Participants.get(j).getId()==4)
                {
                    System.out.println(groupsUser.get(i));
                }
            }
        }
        DataSource d1 = DataSource.getInstance();
        Conversation conv2= msgService.getConversationById(6);

        System.out.println(msgService.getMessageByCon(conv2));*/
        //  delete message
       //  msgService.deleteMessage(msgService.getMessageById(66).get(0));
        // System.out.println(msgService.getMessage(conv2));




/*
        User u=   GrCRUD.getUserById(2);
        System.out.println(u);
        // add message
       /* Conversation convmsg= msgService.getConversationById(142);
        Message msg=new Message("test ",convmsg,u);
        msgService.SendMsg(msg);
*/
/*
        ArrayList<User> Participants = new ArrayList<>();
        User u3=   GrCRUD.getUserById(3);
        Participants.add(u3);
        User u2=   GrCRUD.getUserById(4);
        Participants.add(u);
        // add group avec Participants

       /* GroupeChat group = new GroupeChat("testgr",u,Participants);
        GrCRUD.AjouterGroupe(group); */
        //update group

       /*
        conv2.setNom("test update3");
        GrCRUD.updateConversation(conv2);

        */
     //  add private chat
      /*  PrivateChat prv =new PrivateChat(u2,u);
        PrivateChatService prvService = new PrivateChatService();
        prvService.AjouterPrivateChat(prv);
        //affich private chat
        System.out.println(prvService.privateChat());
*/
         //delete group
        //GrCRUD.deleteGroup(msgService.getConversationById(126));

    }
}
