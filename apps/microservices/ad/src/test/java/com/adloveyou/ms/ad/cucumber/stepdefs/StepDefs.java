package com.adloveyou.ms.ad.cucumber.stepdefs;

import com.adloveyou.ms.ad.AdApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AdApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
