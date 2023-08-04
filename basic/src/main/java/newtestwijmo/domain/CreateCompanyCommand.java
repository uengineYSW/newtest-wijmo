package newtestwijmo.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
public class CreateCompanyCommand {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private String name;

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private String industry;

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private LocalDate foundedDate;
}
