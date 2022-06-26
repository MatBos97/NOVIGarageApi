package mathijs.bos.NOVIGarageApi.Receipt;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mathijs.bos.NOVIGarageApi.Action.Action;
import mathijs.bos.NOVIGarageApi.Issue.Issue;
import mathijs.bos.NOVIGarageApi.Part.Part;
import mathijs.bos.NOVIGarageApi.Visit.Visit;
import org.javamoney.moneta.Money;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.money.MonetaryAmount;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReceiptPdfWriter {

    private MonetaryAmount totalPrice;
    private final Document document;
    private final Visit visit;

    public ReceiptPdfWriter(Visit visit) {
        this.totalPrice = Money.of(0, "EUR");
        this.document = new Document();
        this.visit = visit;
    }

    public Path generateReceipt() {

        Money totalPrice = Money.of(0, "EUR");

        String fileName = "src/Receipt_" + visit.getId() +
                visit.getCar().getId() +
                visit.getInspection().getId() +
                visit.getRepair().getId() +
                ".pdf";


        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();

            ///Customer & car info
            document.add(new Paragraph("Customer & car information:"));
            document.add(CreateCustomerAndCarInfoTable());
            document.add(Chunk.NEWLINE);

            ///Issues found
            document.add(new Paragraph("Issues found:"));
            document.add(CreateIssueTable());
            document.add(Chunk.NEWLINE);

            ///Parts used
            document.add(new Paragraph("Parts used:"));
            document.add(CreatePartTable());
            document.add(Chunk.NEWLINE);

            ///Actions take
            document.add(new Paragraph("Actions performed:"));
            document.add(CreateActionTable());
            document.add(Chunk.NEWLINE);

            ///Total price
            document.add(new Paragraph("Total price: " + this.totalPrice));

            document.close();

            return Path.of(fileName);

        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PdfPTable CreateCustomerAndCarInfoTable(){
        PdfPTable customerInfoTable = new PdfPTable( 2);
        customerInfoTable.addCell("Name");
        customerInfoTable.addCell(visit.getCar().getCustomer().getFirstName() + " " + visit.getCar().getCustomer().getLastName());
        customerInfoTable.addCell("Cellphone");
        customerInfoTable.addCell(visit.getCar().getCustomer().getPhoneNumber());
        customerInfoTable.addCell("Car numberplate");
        customerInfoTable.addCell(visit.getCar().getNumberPlate());
        return customerInfoTable;
    }

    private PdfPTable CreateIssueTable(){
        PdfPTable issuesTable = new PdfPTable(2);
        for (Issue issue : visit.getInspection().getIssues()){
            issuesTable.addCell(issue.getDescription());
            issuesTable.addCell(issue.getStatusOfIssue().toString());
        }
        return issuesTable;
    }

    private PdfPTable CreatePartTable(){
        PdfPTable partsUsed = new PdfPTable(2);
        for (Part part : visit.getRepair().getParts()){
            partsUsed.addCell(part.getPartName());
            MonetaryAmount partPrice = TaxCalculator.CalculateTax(part.getPrice());
            totalPrice = addToTotal(totalPrice, partPrice);
            partsUsed.addCell(partPrice.toString());
        }
        return partsUsed;
    }

    private PdfPTable CreateActionTable(){
        PdfPTable actionsTaken = new PdfPTable(2);
        actionsTaken.addCell("Car inspection");
        MonetaryAmount inspectionPrice = TaxCalculator.CalculateTax(new BigDecimal(45));
        totalPrice = addToTotal(totalPrice, inspectionPrice);
        actionsTaken.addCell(inspectionPrice.toString());
        for (Action action : visit.getRepair().getActions()){
            actionsTaken.addCell(action.getActionDescription());
            MonetaryAmount actionPrice = TaxCalculator.CalculateTax(action.getPrice());
            totalPrice = addToTotal(totalPrice, actionPrice);
            actionsTaken.addCell(actionPrice.toString());
        }
        return actionsTaken;
    }

    private MonetaryAmount addToTotal(MonetaryAmount currentTotal, MonetaryAmount toAdd){
        return currentTotal.add(toAdd);
    }
}
