package mathijs.bos.NOVIGarageApi.Receipt;

import org.javamoney.moneta.Money;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

public class TaxCalculator {

    public static MonetaryAmount CalculateTax(BigDecimal originalPrice){
        MonetaryAmount money = Money.of(originalPrice, "EUR");
        return money.multiply(1.21);
    }

}
