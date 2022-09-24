package mathijs.bos.NOVIGarageApi.Issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Issue")
public class IssueController {


    @Autowired
    private IssueRepository issueRepository;

    @Secured("ROLE_MECHANIC")
    @GetMapping("/All")
    List<Issue> all(){
        return issueRepository.findAll();
    }

    @Secured("ROLE_MECHANIC")
    @GetMapping("/{id}")
    Issue findIssue(@PathVariable Long id){
        return issueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No issue found with the id: " + id));
    }

    @Secured("ROLE_MECHANIC")
    @GetMapping("/Inspection/{inspectionId}")
    List<Issue> findInspectionIssues(@PathVariable Long inspectionId){
        return issueRepository.findByInspection_Id(inspectionId);
    }

    @Secured("ROLE_MECHANIC")
    @GetMapping("/Inspection/{inspectionId}/ToRepair")
    List<Issue> findInspectionIssuesThatNeedRepair(@PathVariable Long inspectionId){
        return issueRepository.findByInspection_IdAndStatusOfIssue(inspectionId, Issue.StatusOfIssue.IN_QUE_FOR_REPAIR);
    }

    @Secured("ROLE_MECHANIC")
    @PostMapping()
    Issue newIssue(@RequestBody Issue newIssue){
        return issueRepository.save(newIssue);
    }

    @Secured("ROLE_MECHANIC")
    @PutMapping("/{id}")
    Issue replaceIssue(@RequestBody Issue newIssue, @PathVariable Long id){
        return issueRepository.findById(id)
                .map(issue -> {
                    issue.setDescription(newIssue.getDescription());
                    issue.setInspection(newIssue.getInspection());
                    issue.setStatusOfIssue(newIssue.getStatusOfIssue());
                    return issueRepository.save(issue);
                })
                .orElseGet(() -> {
                    newIssue.setId(id);
                    return issueRepository.save(newIssue);
                });
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    void deleteIssue(@PathVariable Long id){
        issueRepository.deleteById(id);
    }
}
