package newtestwijmo.infra;

import newtestwijmo.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class SalesOrderHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<SalesOrder>> {

    @Override
    public EntityModel<SalesOrder> process(EntityModel<SalesOrder> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "//sales/update")
                .withRel("/sales/update")
        );

        return model;
    }
}
