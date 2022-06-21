package mathijs.bos.NOVIGarageApi.Receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Receipt")
public class ReceiptController {


    @Autowired
    private ReceiptRepository receiptRepository;

    @GetMapping("/All")
    List<Receipt> all(){
        return receiptRepository.findAll();
    }

    @GetMapping("/{id}")
    Receipt findReceipt(@PathVariable Long id){
        return receiptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receipt with id: " + id + " was not found."));
    }

    @PostMapping()
    Receipt newReceipt(@RequestBody Receipt newReceipt){
        return receiptRepository.save(newReceipt);
    }

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

    @DeleteMapping("/{id}")
    void deleteReceipt(@PathVariable Long id){
        receiptRepository.deleteById(id);
    }
}
