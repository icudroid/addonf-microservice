package com.adloveyou.webapps.cucumber.stepdefs;

import com.adloveyou.webapps.GatewayTemplateApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = GatewayTemplateApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
