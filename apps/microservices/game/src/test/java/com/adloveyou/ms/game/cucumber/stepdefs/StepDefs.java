package com.adloveyou.ms.game.cucumber.stepdefs;

import com.adloveyou.ms.game.GameApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = GameApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
