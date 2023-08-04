package newtestwijmo.common;

import io.cucumber.spring.CucumberContextConfiguration;
import newtestwijmo.SalesApplication;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = { SalesApplication.class })
public class CucumberSpingConfiguration {}
