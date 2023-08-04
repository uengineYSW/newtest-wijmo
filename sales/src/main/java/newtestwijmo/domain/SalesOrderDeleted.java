package newtestwijmo.domain;

import java.util.*;
import lombok.*;
import newtestwijmo.domain.*;
import newtestwijmo.infra.AbstractEvent;

@Data
@ToString
public class SalesOrderDeleted extends AbstractEvent {

    private String salesOrderNumber;

    public SalesOrderDeleted(SalesOrder aggregate) {
        super(aggregate);
    }

    public SalesOrderDeleted() {
        super();
    }
}
