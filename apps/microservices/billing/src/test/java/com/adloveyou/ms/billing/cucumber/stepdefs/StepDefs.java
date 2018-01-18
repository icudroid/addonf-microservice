package com.adloveyou.ms.billing.cucumber.stepdefs;

import com.adloveyou.ms.billing.BillingApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = BillingApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
