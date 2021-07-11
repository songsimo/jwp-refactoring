package kitchenpos.order.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class OrderLineItems {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderLineItem> orderLineItems;

    public OrderLineItems() {
        orderLineItems = new ArrayList<>();
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return Collections.unmodifiableList(orderLineItems);
    }

    public void add(OrderLineItem orderLineItem) {
        orderLineItems.add(orderLineItem);
    }
}