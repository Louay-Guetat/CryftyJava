package edu.esprit.cryfty.tests;


import edu.esprit.cryfty.service.payment.CartService;
import edu.esprit.cryfty.service.payment.TransactionService;
import edu.esprit.cryfty.utils.DataSource;

public class ClassMainSirine {
    public static void main(String[]args){

        TransactionService ts=new TransactionService();
        CartService cartService=new CartService();

        //afficheTransaction
        //System.out.println(ts.getTransactions());


        //affiche cart
        //System.out.println(cartService.getCarts());

        //ajouter NFT au cart
        /*Cart c=cartService.getCartById(1);
        Nft nft = new Nft(25,"TestIntellij","testDescrption",(float)13.0);
        nft.setId(4);
        nfts.add(nft);
        System.out.println(nfts);
        Cart carts=new Cart(nfts, c.getId());
        cartService.addNftToCart(carts);*/

        //supprimer nft from cart
        //cartService.supprimerNftFromCart(4);

        //Ajouter transaction
        /*Date d=new Date();
        Client client=cartService.getClientById(12);
        Cart cartAjout=new Cart(0,client,d);
        cartAjout.setId(29);
        Wallet w=ts.getwalletId(8);
        System.out.println(w);
        Transaction transaction=new Transaction(cartAjout,w,d);
        ts.addTransaction(transaction);*/

        //Afficher juste les address wallet of client connecté
        //System.out.println(ts.getwalletIdClient(8));

        //Afficher nft from cart_nft
        //System.out.println(cartService.getNftfromCart());

        TransactionService transactionService=new TransactionService();
        System.out.println(transactionService.getTransactionsByClient(1));

        DataSource d1=DataSource.getInstance();
        System.out.println(d1.hashCode());
    }
}
