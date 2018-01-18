package com.adloveyou.ms.transaction.cucumber.stepdefs;

import com.adloveyou.ms.transaction.TransactionApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = TransactionApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
