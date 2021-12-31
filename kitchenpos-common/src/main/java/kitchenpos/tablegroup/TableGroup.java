package kitchenpos.tablegroup;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kitchenpos.table.OrderTables;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
public class TableGroup extends AbstractAggregateRoot<TableGroup> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDate;

    public TableGroup() {
    }

    public TableGroup(Long id) {
        this.id = id;
    }

    private TableGroup(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public TableGroup(Long id, LocalDateTime createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    public static TableGroup of(Long id) {
        return new TableGroup(id);
    }

    public static TableGroup create() {
        return new TableGroup(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void Ungroup(OrderTables orderTables) {
        orderTables.ungroup();
        registerEvent(orderTables);
    }
}