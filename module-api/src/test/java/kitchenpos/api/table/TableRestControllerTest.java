package kitchenpos.api.table;

import com.fasterxml.jackson.databind.ObjectMapper;

import kitchenpos.common.BaseTest;
import kitchenpos.domain.table.OrderTable;
import kitchenpos.domain.table.OrderTableRepository;
import kitchenpos.domain.table.dto.OrderTableRequest;
import kitchenpos.domain.tablegroup.TableGroup;
import kitchenpos.domain.tablegroup.TableGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("주문 테이블 컨트롤러 테스트")
class TableRestControllerTest extends BaseTest {
    public static final String DEFAULT_TABLES_URI = "/api/tables/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TableGroupRepository tableGroupRepository;

    @Autowired
    private OrderTableRepository orderTableRepository;

    private TableGroup savedTableGroup;
    private OrderTableRequest orderTableRequest;

    @BeforeEach
    void setUp() {
        savedTableGroup = tableGroupRepository.save(TableGroup.createInstance());

        orderTableRequest = new OrderTableRequest();
        orderTableRequest.setTableGroupId(savedTableGroup.getId());
        orderTableRequest.setNumberOfGuests(2);
    }

    @DisplayName("주문 테이블을 생성한다.")
    @Test
    void 주문_테이블_생성() throws Exception {
        final String jsonTypeTableGroup = objectMapper.writeValueAsString(orderTableRequest);

        mockMvc.perform(post(DEFAULT_TABLES_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonTypeTableGroup))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("empty").value(false))
            .andExpect(jsonPath("tableGroupId").value(savedTableGroup.getId()))
            .andExpect(jsonPath("numberOfGuests").value(orderTableRequest.getNumberOfGuests()));
    }

    @DisplayName("주문 테이블을 조회한다.")
    @Test
    void 주문_테이블_조회() throws Exception {
        orderTableRepository.save(OrderTable.of(null, 1, true));

        mockMvc.perform(get(DEFAULT_TABLES_URI)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @DisplayName("빈 테이블 설정한다.")
    @Test
    void 빈_테이블_설정() throws Exception {
        final OrderTable savedOrderTable = orderTableRepository.save(OrderTable.of(null, 2, true));

        orderTableRequest.setTableGroupId(null);
        orderTableRequest.setEmpty(true);
        final String jsonTypeOrderTable = objectMapper.writeValueAsString(orderTableRequest);

        mockMvc.perform(put(DEFAULT_TABLES_URI + savedOrderTable.getId() + "/empty")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonTypeOrderTable))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(savedOrderTable.getId()))
            .andExpect(jsonPath("empty").value(true))
            .andExpect(jsonPath("tableGroupId").doesNotExist())
            .andExpect(jsonPath("numberOfGuests").value(savedOrderTable.getNumberOfGuests()));
    }

    @DisplayName("게스트의 수를 변경한다.")
    @Test
    void 게스트_수_변경() throws Exception {
        final OrderTable savedOrderTable = orderTableRepository.save(OrderTable.of(null, 2, false));

        orderTableRequest.setNumberOfGuests(8);
        final String jsonTypeOrderTable = objectMapper.writeValueAsString(orderTableRequest);

        mockMvc.perform(put(DEFAULT_TABLES_URI + savedOrderTable.getId() + "/number-of-guests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonTypeOrderTable))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(savedOrderTable.getId()))
            .andExpect(jsonPath("empty").value(false))
            .andExpect(jsonPath("tableGroupId").doesNotExist())
            .andExpect(jsonPath("numberOfGuests").value(8));
    }
}
