package mathijs.bos.NOVIGarageApi.ReceiptTest;

import mathijs.bos.NOVIGarageApi.Action.Action;
import mathijs.bos.NOVIGarageApi.Car.Car;
import mathijs.bos.NOVIGarageApi.Customer.Customer;
import mathijs.bos.NOVIGarageApi.Inspection.Inspection;
import mathijs.bos.NOVIGarageApi.Issue.Issue;
import mathijs.bos.NOVIGarageApi.Part.Part;
import mathijs.bos.NOVIGarageApi.Receipt.Receipt;
import mathijs.bos.NOVIGarageApi.Receipt.ReceiptGenerator;
import mathijs.bos.NOVIGarageApi.Repair.Repair;
import mathijs.bos.NOVIGarageApi.Visit.Visit;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ReceiptGeneratorTest {

    @Test
    void generateReceiptPdfTest() {
        ReceiptGenerator receiptGenerator = new ReceiptGenerator();

        Customer customer = new Customer("Mathijs", "Bos", "0631776052");

        Car car = new Car(customer, "123456789");

        Inspection inspection = new Inspection(new Date(20210101), new HashSet<>());
        Issue issue = new Issue(inspection, "Flat tire", Issue.StatusOfIssue.REPAIR_DONE);
        inspection.getIssues().add(issue);

        Part part = new Part("Tire", 30.60f);
        List<Part> partsUsed = new ArrayList<>();
        partsUsed.add(part);

        Action action = new Action("Replaced tire", 10.95f);
        List<Action> actionsDone = new ArrayList<>();
        actionsDone.add(action);

        Repair repair = new Repair(new Date(20210101), partsUsed, actionsDone);

        Visit visit = new Visit(car, inspection, repair, Visit.StatusOfVisit.READY_FOR_PICKUP);


        receiptGenerator.generateReceipt(visit);
    }
}
