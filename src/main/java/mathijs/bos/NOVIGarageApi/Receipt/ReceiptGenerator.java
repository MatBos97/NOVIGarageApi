package mathijs.bos.NOVIGarageApi.Receipt;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReceiptGenerator {

    public void generateReceipt() {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Receipt"));

            document.open();

            Chunk chunk = new Chunk("Hello world");

            document.add(chunk);

            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
