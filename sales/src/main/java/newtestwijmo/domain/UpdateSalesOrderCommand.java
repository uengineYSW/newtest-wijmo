package newtestwijmo.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
public class UpdateSalesOrderCommand {

    private String salesOrderNumber;
    private String salesPerson;
    private String companyId;
    private SalesType salesType;
    private List<SalesItem> salesItems;
}
