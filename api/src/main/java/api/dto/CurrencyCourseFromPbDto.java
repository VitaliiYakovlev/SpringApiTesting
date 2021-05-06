package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyCourseFromPbDto {
    private String ccy;
    private String baseCcy;
    private String buy;
    private String sale;

    public CurrencyCourseFromPbDto(String ccy, String baseCcy, String buy, String sale) {
        this.ccy = ccy;
        this.baseCcy = baseCcy;
        this.buy = buy;
        this.sale = sale;
    }

    public CurrencyCourseFromPbDto() {
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBaseCcy() {
        return baseCcy;
    }

    @JsonProperty("base_ccy")
    public void setBaseCcy(String baseCcy) {
        this.baseCcy = baseCcy;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }
}
