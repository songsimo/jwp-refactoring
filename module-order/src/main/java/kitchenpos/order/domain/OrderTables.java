package kitchenpos.order.domain;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class OrderTables implements Iterable<OrderTable> {

    @OneToMany(mappedBy = "tableGroupId")
    private List<OrderTable> orderTables;

    protected OrderTables(){

    }

    public OrderTables(List<OrderTable> orderTables) {
        this.orderTables = orderTables;
    }

    public List<OrderTable> getOrderTables() {
        return orderTables;
    }

    public List<Long> getOrderTableIds(){
        return this.orderTables.stream()
                .map(orderTable -> orderTable.getId())
                .collect(Collectors.toList());
    }

    public void cancleGroup(){
        this.orderTables.stream()
                .forEach(orderTable -> orderTable.cancelTableGroup());
        this.orderTables = Collections.emptyList();
    }

    @Override
    public Iterator<OrderTable> iterator() {
        return this.orderTables.iterator();
    }
}