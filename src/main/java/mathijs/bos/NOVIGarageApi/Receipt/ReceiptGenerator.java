package mathijs.bos.NOVIGarageApi.Receipt;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import mathijs.bos.NOVIGarageApi.Action.Action;
import mathijs.bos.NOVIGarageApi.Issue.Issue;
import mathijs.bos.NOVIGarageApi.Part.Part;
import mathijs.bos.NOVIGarageApi.Visit.Visit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReceiptGenerator {

    public void generateReceipt(Visit visit) {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Receipt.pdf"));

            document.open();

            ///Customer info
            Chunk customerInfoTitle = new Chunk("Customer info:");
            document.add(customerInfoTitle);

            PdfPTable customerInfoTable = new PdfPTable( 2);
            customerInfoTable.addCell("Name");
            customerInfoTable.addCell(visit.getCar().getCustomer().getFirstName() + " " + visit.getCar().getCustomer().getLastName());
            customerInfoTable.addCell("Cellphone");
            customerInfoTable.addCell(visit.getCar().getCustomer().getPhoneNumber());
            document.add(customerInfoTable);

            ///Car info
            Chunk carInfoTitle = new Chunk("Car info:");
            document.add(carInfoTitle);

            PdfPTable carInfoTable = new PdfPTable(2);
            carInfoTable.addCell("Numberplate");
            carInfoTable.addCell(visit.getCar().getNumberPlate());
            document.add(carInfoTable);

            ///Issues found
            Chunk issuesInfoTile = new Chunk("Issues found:");
            document.add(issuesInfoTile);

            PdfPTable issuesTable = new PdfPTable(2);
            for (Issue issue : visit.getInspection().getIssues()){
                issuesTable.addCell(issue.getDescription());
                issuesTable.addCell(issue.getStatusOfIssue().toString());
            }
            document.add(issuesTable);

            ///Parts used
            Chunk partsTitle = new Chunk("Parts used:");
            document.add(partsTitle);

            PdfPTable partsUsed = new PdfPTable(2);
            for (Part part : visit.getRepair().getParts()){
                partsUsed.addCell(part.getPartName());
                partsUsed.addCell(part.getPrice().toString());
            }
            document.add(partsUsed);

            ///Actions take
            Chunk actionsTitle = new Chunk("Actions taken:");
            document.add(actionsTitle);

            PdfPTable actionsTaken = new PdfPTable(2);
            for (Action action : visit.getRepair().getActions()){
                actionsTaken.addCell(action.getActionDescription());
                actionsTaken.addCell(action.getPrice().toString());
            }
            document.add(actionsTaken);

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
