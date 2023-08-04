package newtestwijmo.common;

import io.cucumber.spring.CucumberContextConfiguration;
import newtestwijmo.BasicApplication;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = { BasicApplication.class })
public class CucumberSpingConfiguration {}
