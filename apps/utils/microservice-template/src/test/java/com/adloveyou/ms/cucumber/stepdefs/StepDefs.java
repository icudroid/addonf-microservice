package com.adloveyou.ms.cucumber.stepdefs;

import com.adloveyou.ms.MicroserviceTemplateApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = MicroserviceTemplateApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
