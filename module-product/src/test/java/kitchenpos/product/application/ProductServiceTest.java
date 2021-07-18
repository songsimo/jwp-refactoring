package kitchenpos.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.common.domain.Price;
import kitchenpos.common.exception.PriceEmptyException;
import kitchenpos.common.exception.PriceNegativeException;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import kitchenpos.product.dto.ProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("상품 서비스")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @DisplayName("상품을 등록한다.")
    @Test
    void create() {
        // Given
        Product 피자 = new Product(1L, "피자", Price.wonOf(20000));
        given(productRepository.save(any())).willReturn(피자);

        // When
        productService.create(ProductRequest.of(피자));

        // Then
        verify(productRepository, times(1)).save(any());
    }

    @DisplayName("가격은 필수입력항목이다.")
    @Test
    void create_Fail_01() {
        // Given
        ProductRequest 햄버거 = new ProductRequest("햄버거", null);

        // When & Then
        assertThatThrownBy(() -> productService.create(햄버거))
            .isInstanceOf(PriceEmptyException.class);
        verify(productRepository, never()).save(any());
    }

    @DisplayName("가격은 0원 이상이어야 한다.")
    @Test
    void create_Fail_02() {
        // Given
        ProductRequest 햄버거 = new ProductRequest("햄버거", new BigDecimal(-1));

        // When & Then
        assertThatThrownBy(() -> productService.create(햄버거))
            .isInstanceOf(PriceNegativeException.class);
        verify(productRepository, never()).save(any());
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void list() {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "피자", Price.wonOf(20000)));
        products.add(new Product(2L, "햄버거", Price.wonOf(10000)));
        given(productRepository.findAll()).willReturn(products);

        // When & Then
        assertThat(productService.list()).hasSize(2);
        verify(productRepository, times(1)).findAll();
    }

}