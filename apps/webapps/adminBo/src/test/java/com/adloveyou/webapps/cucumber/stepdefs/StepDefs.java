package com.adloveyou.webapps.cucumber.stepdefs;

import com.adloveyou.webapps.AdminBoApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AdminBoApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
