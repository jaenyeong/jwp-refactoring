package kitchenpos.domain.menu;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Price {
    private static final int LESS_THAN_ZERO = 0;
    private static final String ERR_TEXT_PRICE_MUST_BE_GREATER_THAN_ZERO = "가격은 0보다 큰 금액이어야 합니다.";
    private static final String ERR_TEXT_ALL_PRICE_MUST_BE_GREATER_THAN_ZERO = "모든 제품의 가격의 총합은 0보다 큰 금액이어야 합니다.";
    private BigDecimal price;

    protected Price() {
    }

    protected Price(final BigDecimal price) {
        this.price = price;
    }

    public static Price of(final BigDecimal price) {
        if (BigDecimal.ZERO.compareTo(price) >= LESS_THAN_ZERO) {
            throw new IllegalArgumentException(ERR_TEXT_PRICE_MUST_BE_GREATER_THAN_ZERO);
        }

        return new Price(price);
    }

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(price.doubleValue());
    }

    public void isMustBeLessThanAllMenuProductsPrice(final Price allMenuProductsPrice) {
        if (this.price.compareTo(allMenuProductsPrice.getPrice()) > LESS_THAN_ZERO) {
            throw new IllegalArgumentException(ERR_TEXT_ALL_PRICE_MUST_BE_GREATER_THAN_ZERO);
        }
    }
}
