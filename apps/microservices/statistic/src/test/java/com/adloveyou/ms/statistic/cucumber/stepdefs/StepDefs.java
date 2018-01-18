package com.adloveyou.ms.statistic.cucumber.stepdefs;

import com.adloveyou.ms.statistic.StatisticApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = StatisticApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
