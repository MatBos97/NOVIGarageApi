package mathijs.bos.NOVIGarageApi.Receipt;

import mathijs.bos.NOVIGarageApi.Visit.Visit;
import mathijs.bos.NOVIGarageApi.Visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/Receipt")
public class ReceiptController {


    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private VisitRepository visitRepository;

    @Secured({"ROLE_CASHIER"})
    @GetMapping("/All")
    List<Receipt> all(){
        return receiptRepository.findAll();
    }

    @Secured({"ROLE_CASHIER"})
    @GetMapping("/GenerateReceipt/{visitId}")
    ResponseEntity<byte[]> generateReceiptOfVisit(@PathVariable Long visitId){
        try {
            Visit visit = visitRepository.findById(visitId).orElseThrow();
            ReceiptPdfWriter writer = new ReceiptPdfWriter(visit);
            Path path = writer.generateReceipt();
            Receipt receipt = new Receipt(
                    visit,
                    path.getFileName().toString(),
                    "PDF",
                    Files.readAllBytes(path)
            );
            Files.delete(path);
            receiptRepository.save(receipt);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + receipt.getFileName() + "\"")
                    .body(receipt.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Secured({"ROLE_CASHIER"})
    @GetMapping("/{id}")
    ResponseEntity<byte[]> findById(@PathVariable Long id){
        Receipt receipt = receiptRepository.findById(id).orElseThrow();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + receipt.getFileName() + "\"")
                .body(receipt.getData());
    }

    @Secured({"ROLE_CASHIER"})
    @PostMapping("/{visitId}")
    Receipt uploadReceipt(@RequestParam("file")MultipartFile file, @PathVariable Long visitId){
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new EntityNotFoundException("Visit with id: " + visitId + " was not found."));
        try {
            Receipt receipt = new Receipt(
                    visit,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            return receiptRepository.save(receipt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Secured({"ROLE_CASHIER"})
    @PutMapping("/{id}")
    Receipt replaceReceipt(@RequestBody Receipt newReceipt, @PathVariable Long id){
        return receiptRepository.findById(id)
                .map(receipt -> {
                    receipt.setFileName(newReceipt.getFileName());
                    receipt.setFileType(newReceipt.getFileType());
                    receipt.setData(newReceipt.getData());
                    return receiptRepository.save(receipt);
                })
                .orElseGet(() -> {
                    newReceipt.setId(id);
                    return receiptRepository.save(newReceipt);
                });
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    void deleteReceipt(@PathVariable Long id){
        receiptRepository.deleteById(id);
    }
}
