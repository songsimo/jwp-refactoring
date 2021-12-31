package kitchenpos.dto.product;


import kitchenpos.product.Product;

public class ProductResponse {

    private Long id;

    private String name;

    private Integer price;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName().getName(),
            product.getPrice().getValue().intValue());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}