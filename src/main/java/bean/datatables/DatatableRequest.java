package bean.datatables;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DatatableRequest {
    private Integer draw;

    private Integer start;

    private Integer length;

    DatatableSearch search;

    private List<DatatableColumn> columns = Collections.<DatatableColumn>emptyList();

    private List<DatatableOrder> order = Collections.<DatatableOrder>emptyList();

    private Map<String, Object> paramMap = Collections.emptyMap();

}
