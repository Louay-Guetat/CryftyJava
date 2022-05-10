package edu.esprit.cryfty.utils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;



/**
 *
 * @author 21626
 */
import com.itextpdf.text.Document;
import edu.esprit.cryfty.entity.User.Reclamation;
import edu.esprit.cryfty.service.user.ReclamationService;

public class ServicePdf {

    public void equipePDF() throws FileNotFoundException, DocumentException {

        ReclamationService so = new ReclamationService();
        String message = "                       La liste des Reclamations \n\n";


        String file_name = "src/PDF/reclamation.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph(message);
        document.add(para);
        List<Reclamation> reclamations = so.showReclamations();
        PdfPTable table = new PdfPTable(3);

        PdfPCell cl = new PdfPCell(new Phrase("id"));
        table.addCell(cl);
        PdfPCell cl1 = new PdfPCell(new Phrase("name"));
        table.addCell(cl1);
        PdfPCell cl2 = new PdfPCell(new Phrase("email"));
        table.addCell(cl2);



        table.setHeaderRows(1);
        document.add(table);

        for (int i = 0; i < reclamations.size(); i++) {
            table.addCell(""+ reclamations.get(i).getId());
            table.addCell("" + reclamations.get(i).getName());
            table.addCell("" + reclamations.get(i).getEmail());


        }
        document.add(table);

        document.close();

    }


}
