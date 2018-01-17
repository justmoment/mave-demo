package bean.datatables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DatatableResponse<T> {

    private Integer draw;

    private Integer recordsTotal;

    private Integer recordsFiltered;

    private List<T> data = Collections.<T>emptyList();


}
