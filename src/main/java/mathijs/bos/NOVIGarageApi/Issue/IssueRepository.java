package mathijs.bos.NOVIGarageApi.Issue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByInspection_Id(Long id);

    List<Issue> findByInspection_IdAndStatusOfIssue(Long id, Issue.StatusOfIssue statusOfIssue);
}