package bean.datatables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DatatableColumn {

    private String data;

    private String name;

    private boolean searchable;

    private boolean orderable;

    private DatatableSearch search;
}
