package newtestwijmo.domain;

import java.util.*;
import lombok.*;
import newtestwijmo.domain.*;
import newtestwijmo.infra.AbstractEvent;

@Data
@ToString
public class CompanyDeleted extends AbstractEvent {

    private UUID companyId;

    public CompanyDeleted(Company aggregate) {
        super(aggregate);
    }

    public CompanyDeleted() {
        super();
    }
}
