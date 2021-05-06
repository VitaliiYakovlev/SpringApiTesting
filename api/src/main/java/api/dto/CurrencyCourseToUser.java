package api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CurrencyCourseToUser {

    private final String ccy;
    private final String baseCcy;
    private final String buy;
    private final String sale;

    public CurrencyCourseToUser(CurrencyCourseFromPbDto currencyCourseFromPbDto) {
        this.ccy = currencyCourseFromPbDto.getCcy();
        this.baseCcy = currencyCourseFromPbDto.getBaseCcy();
        this.buy = currencyCourseFromPbDto.getBuy();
        this.sale = currencyCourseFromPbDto.getSale();
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CurrencyCourseToUser(@JsonProperty("ccy") String ccy,
                                @JsonProperty("baseCcy") String baseCcy,
                                @JsonProperty("buy") String buy,
                                @JsonProperty("sale") String sale) {
        this.ccy = ccy;
        this.baseCcy = baseCcy;
        this.buy = buy;
        this.sale = sale;
    }

    public String getCcy() {
        return ccy;
    }

    public String getBaseCcy() {
        return baseCcy;
    }

    public String getBuy() {
        return buy;
    }

    public String getSale() {
        return sale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CurrencyCourseToUser that = (CurrencyCourseToUser) o;
        return ccy.equals(that.ccy) && baseCcy.equals(that.baseCcy) && buy.equals(that.buy) && sale.equals(that.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccy, baseCcy, buy, sale);
    }
}
