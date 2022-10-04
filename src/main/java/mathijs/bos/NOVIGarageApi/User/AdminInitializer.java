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

    }
}
