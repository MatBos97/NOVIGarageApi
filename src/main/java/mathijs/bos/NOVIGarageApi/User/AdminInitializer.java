package mathijs.bos.NOVIGarageApi.User;

import mathijs.bos.NOVIGarageApi.Action.Action;
import mathijs.bos.NOVIGarageApi.Action.ActionRepository;
import mathijs.bos.NOVIGarageApi.Car.Car;
import mathijs.bos.NOVIGarageApi.Car.CarRepository;
import mathijs.bos.NOVIGarageApi.CarFile.CarFile;
import mathijs.bos.NOVIGarageApi.CarFile.CarFileRepository;
import mathijs.bos.NOVIGarageApi.CustomAction.CustomAction;
import mathijs.bos.NOVIGarageApi.CustomAction.CustomActionRepository;
import mathijs.bos.NOVIGarageApi.Customer.Customer;
import mathijs.bos.NOVIGarageApi.Customer.CustomerRepository;
import mathijs.bos.NOVIGarageApi.Inspection.Inspection;
import mathijs.bos.NOVIGarageApi.Inspection.InspectionRepository;
import mathijs.bos.NOVIGarageApi.Issue.Issue;
import mathijs.bos.NOVIGarageApi.Issue.IssueRepository;
import mathijs.bos.NOVIGarageApi.Part.Part;
import mathijs.bos.NOVIGarageApi.Part.PartRepository;
import mathijs.bos.NOVIGarageApi.Receipt.Receipt;
import mathijs.bos.NOVIGarageApi.Receipt.ReceiptRepository;
import mathijs.bos.NOVIGarageApi.Repair.Repair;
import mathijs.bos.NOVIGarageApi.Repair.RepairRepository;
import mathijs.bos.NOVIGarageApi.Security.Role;
import mathijs.bos.NOVIGarageApi.Visit.Visit;
import mathijs.bos.NOVIGarageApi.Visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
public class AdminInitializer implements CommandLineRunner {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private CarFileRepository carFileRepository;
    @Autowired
    private InspectionRepository inspectionRepository;
    @Autowired
    private RepairRepository repairRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private CustomActionRepository customActionRepository;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private ActionRepository actionRepository;

    //For writing postman tests i will add one of each to not have to write json constantly but just copy and paste


    @Override
    public void run(String... args) throws Exception {
        userRepository.save(
                new UserEntity(
                        "Admin",
                        "Admin",
                        List.of(
                                new Role("ROLE_ADMIN"),
                                new Role("ROLE_MECHANIC"),
                                new Role("ROLE_RECEPTIONIST"),
                                new Role("ROLE_CASHIER")
                        )
                )
        );

        Customer customer = new Customer("Mathijs", "Bos", "123456789");

        Car car = new Car(customer, "123-456-789");

        HashSet<Issue> issues = new HashSet<>();

        Inspection inspection = new Inspection(new Date(20220101), issues);
        inspection.getIssues().add(new Issue(inspection, "broke sensor", Issue.StatusOfIssue.IN_QUE_FOR_REPAIR));

        Collection<Part> parts = new ArrayList<>();

        Collection<Action> actions = new ArrayList<>();

        Repair repair = new Repair(new Date(20220101), parts, actions);
        repair.getParts().add(new Part("sensor", new BigDecimal(20)));
        repair.getActions().add(new Action("Repair sensor", new BigDecimal(5)));

        Visit visit = new Visit(car, inspection, repair, Visit.StatusOfVisit.QUE_INSPECTION);

        CustomAction customAction = new CustomAction(repair, "Custom action" , 15.95f);

        CarFile carFile = new CarFile(car, "car file", "pdf", new byte[1]);

        Receipt receipt = new Receipt(visit, "receipt", "pdf", new byte[1]);


        customerRepository.save(customer);
        carRepository.save(car);
        inspectionRepository.save(inspection);
    }
}
