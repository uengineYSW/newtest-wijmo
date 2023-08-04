package newtestwijmo.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import newtestwijmo.SalesApplication;
import newtestwijmo.domain.SalesOrderCreated;
import newtestwijmo.domain.SalesOrderDeleted;
import newtestwijmo.domain.SalesOrderUpdated;

@Entity
@Table(name = "SalesOrder_table")
@Data
public class SalesOrder {

    @Id
    private String salesOrderNumber;

    private String salesPerson;

    @OneToOne
    private CompanyId companyId;

    private String salesType;

    @ElementCollection
    private List<SalesItem> salesItems;

    @PostPersist
    public void onPostPersist() {
        SalesOrderCreated salesOrderCreated = new SalesOrderCreated(this);
        salesOrderCreated.publishAfterCommit();

        SalesOrderUpdated salesOrderUpdated = new SalesOrderUpdated(this);
        salesOrderUpdated.publishAfterCommit();

        SalesOrderDeleted salesOrderDeleted = new SalesOrderDeleted(this);
        salesOrderDeleted.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {}

    public static SalesOrderRepository repository() {
        SalesOrderRepository salesOrderRepository = SalesApplication.applicationContext.getBean(
            SalesOrderRepository.class
        );
        return salesOrderRepository;
    }

    public void updateSalesOrder(
        UpdateSalesOrderCommand updateSalesOrderCommand
    ) {
        //implement business logic here:

    }
}
