package kitchenpos.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderTableId(Long orderTableId);

    List<Order> findByOrderTableIdIn(List<Long> orderTableIds);

    List<Order> findAllByIdIn(List<Long> orderIds);
}
