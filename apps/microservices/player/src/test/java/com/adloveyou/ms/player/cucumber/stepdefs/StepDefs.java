package com.adloveyou.ms.player.cucumber.stepdefs;

import com.adloveyou.ms.player.PlayerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = PlayerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
