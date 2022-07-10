package kitchenpos.order.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import kitchenpos.menu.application.MenuService;
import kitchenpos.menu.domain.Menu;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.dto.OrderLineItemRequest;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.order.dto.OrderResponse;
import kitchenpos.order.dto.OrderStatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final MenuService menuService;

    public OrderService(OrderRepository orderRepository,
        OrderValidator orderValidator, MenuService menuService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.menuService = menuService;
    }

    @Transactional
    public OrderResponse create(final OrderRequest orderRequest) {
        orderValidator.validateOrderTable(orderRequest.getOrderTableId());
        orderValidator.validateIfThereIsMenu(orderRequest.toMenuIds());

        List<OrderLineItem> orderLineItemList = convertOrderLineItems(orderRequest);
        Order order = new Order(orderRequest.getOrderTableId(), orderLineItemList);

        return OrderResponse.from(orderRepository.save(order));
    }

    public List<OrderResponse> list() {
        final List<Order> orders = orderRepository.findAll();

        return orders.stream()
            .map(OrderResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse changeOrderStatus(final Long orderId, final OrderStatusRequest orderStatusRequest) {
        final Order savedOrder = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        savedOrder.updateOrderStatus(orderStatusRequest.getOrderStatus());

        return OrderResponse.from(savedOrder);
    }

    private List<OrderLineItem> convertOrderLineItems(OrderRequest orderRequest) {
        final List<OrderLineItemRequest> orderLineItemRequests = orderRequest.getOrderLineItems();

        List<OrderLineItem> orderLineItems = new ArrayList<>();
        for(OrderLineItemRequest orderLineItemRequest: orderLineItemRequests) {
            final Menu menu = menuService.findMenuById(orderLineItemRequest.getMenuId());

            orderLineItems.add(new OrderLineItem(menu.getId(), menu.getName(), menu.getPrice(), orderLineItemRequest.getQuantity()));
        }

        return orderLineItems;
    }
}