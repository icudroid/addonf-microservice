package com.adloveyou.ms.goosegame.cucumber.stepdefs;

import com.adloveyou.ms.goosegame.GooseGameApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = GooseGameApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
