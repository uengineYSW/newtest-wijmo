package newtestwijmo.domain;

import java.util.*;
import lombok.*;
import newtestwijmo.domain.*;
import newtestwijmo.infra.AbstractEvent;

@Data
@ToString
public class SalesOrderCreated extends AbstractEvent {

    private String salesOrderNumber;

    public SalesOrderCreated(SalesOrder aggregate) {
        super(aggregate);
    }

    public SalesOrderCreated() {
        super();
    }
}
