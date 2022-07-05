package kitchenpos.table.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kitchenpos.AcceptanceTest;
import kitchenpos.table.dto.OrderTableRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("주문 테이블을 관리한다.")
public class TableAcceptanceTest extends AcceptanceTest {
    @Test
    @DisplayName("주문 테이블을 생성한다.")
    void createTable() {
        // when
        ExtractableResponse<Response> response = 주문_테이블_생성_요청(4, true);

        // then
        주문_테이블_생성됨(response);
    }

    public static ExtractableResponse<Response> 주문_테이블_생성_요청(int numberOfGuests, boolean empty) {
        OrderTableRequest orderTableRequest = new OrderTableRequest(numberOfGuests, empty);

        return RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(orderTableRequest)
            .when().post("/api/tables")
            .then().log().all().extract();
    }

    public static void 주문_테이블_생성됨(ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }
}