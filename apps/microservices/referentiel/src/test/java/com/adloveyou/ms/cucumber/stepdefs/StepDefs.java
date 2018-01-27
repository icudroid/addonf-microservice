package com.adloveyou.ms.cucumber.stepdefs;

import com.adloveyou.ms.ReferentielApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ReferentielApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
