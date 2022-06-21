package mathijs.bos.NOVIGarageApi.ReceiptTest;

import mathijs.bos.NOVIGarageApi.Receipt.ReceiptGenerator;
import org.junit.jupiter.api.Test;

public class ReceiptGeneratorTest {

    @Test
    void generateReceiptPdfTest() {
        ReceiptGenerator receiptGenerator = new ReceiptGenerator();

        receiptGenerator.generateReceipt();
    }
}
