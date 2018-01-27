package com.adloveyou.ms.goosegame.web.rest;

import com.adloveyou.ms.goosegame.GooseGameApp;

import com.adloveyou.ms.goosegame.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.goosegame.domain.GooseWin;
import com.adloveyou.ms.goosegame.repository.GooseWinRepository;
import com.adloveyou.ms.goosegame.service.GooseWinService;
import com.adloveyou.ms.goosegame.repository.search.GooseWinSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseWinDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseWinMapper;
import com.adloveyou.ms.web.rest.TestUtil;
import com.adloveyou.ms.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.adloveyou.ms.web.rest.TestUtil.sameInstant;
import static com.adloveyou.ms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adloveyou.ms.goosegame.domain.enumeration.WinStatus;
/**
 * Test class for the GooseWinResource REST controller.
 *
 * @see GooseWinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GooseGameApp.class, SecurityBeanOverrideConfiguration.class})
public class GooseWinResourceIntTest {

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    private static final WinStatus DEFAULT_STATUS = WinStatus.NOT_TRANFERED;
    private static final WinStatus UPDATED_STATUS = WinStatus.TRANSERFERIN;

    private static final ZonedDateTime DEFAULT_WON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_PLAYER_ID = 1L;
    private static final Long UPDATED_PLAYER_ID = 2L;

    @Autowired
    private GooseWinRepository gooseWinRepository;

    @Autowired
    private GooseWinMapper gooseWinMapper;

    @Autowired
    private GooseWinService gooseWinService;

    @Autowired
    private GooseWinSearchRepository gooseWinSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGooseWinMockMvc;

    private GooseWin gooseWin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GooseWinResource gooseWinResource = new GooseWinResource(gooseWinService);
        this.restGooseWinMockMvc = MockMvcBuilders.standaloneSetup(gooseWinResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GooseWin createEntity(EntityManager em) {
        GooseWin gooseWin = new GooseWin()
            .value(DEFAULT_VALUE)
            .status(DEFAULT_STATUS)
            .won(DEFAULT_WON)
            .playerId(DEFAULT_PLAYER_ID);
        return gooseWin;
    }

    @Before
    public void initTest() {
        gooseWinSearchRepository.deleteAll();
        gooseWin = createEntity(em);
    }

    @Test
    @Transactional
    public void createGooseWin() throws Exception {
        int databaseSizeBeforeCreate = gooseWinRepository.findAll().size();

        // Create the GooseWin
        GooseWinDTO gooseWinDTO = gooseWinMapper.toDto(gooseWin);
        restGooseWinMockMvc.perform(post("/api/goose-wins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseWinDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseWin in the database
        List<GooseWin> gooseWinList = gooseWinRepository.findAll();
        assertThat(gooseWinList).hasSize(databaseSizeBeforeCreate + 1);
        GooseWin testGooseWin = gooseWinList.get(gooseWinList.size() - 1);
        assertThat(testGooseWin.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testGooseWin.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testGooseWin.getWon()).isEqualTo(DEFAULT_WON);
        assertThat(testGooseWin.getPlayerId()).isEqualTo(DEFAULT_PLAYER_ID);

        // Validate the GooseWin in Elasticsearch
        GooseWin gooseWinEs = gooseWinSearchRepository.findOne(testGooseWin.getId());
        assertThat(testGooseWin.getWon()).isEqualTo(testGooseWin.getWon());
        assertThat(gooseWinEs).isEqualToIgnoringGivenFields(testGooseWin, "won");
    }

    @Test
    @Transactional
    public void createGooseWinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gooseWinRepository.findAll().size();

        // Create the GooseWin with an existing ID
        gooseWin.setId(1L);
        GooseWinDTO gooseWinDTO = gooseWinMapper.toDto(gooseWin);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGooseWinMockMvc.perform(post("/api/goose-wins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseWinDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GooseWin in the database
        List<GooseWin> gooseWinList = gooseWinRepository.findAll();
        assertThat(gooseWinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGooseWins() throws Exception {
        // Initialize the database
        gooseWinRepository.saveAndFlush(gooseWin);

        // Get all the gooseWinList
        restGooseWinMockMvc.perform(get("/api/goose-wins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseWin.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].won").value(hasItem(sameInstant(DEFAULT_WON))))
            .andExpect(jsonPath("$.[*].playerId").value(hasItem(DEFAULT_PLAYER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getGooseWin() throws Exception {
        // Initialize the database
        gooseWinRepository.saveAndFlush(gooseWin);

        // Get the gooseWin
        restGooseWinMockMvc.perform(get("/api/goose-wins/{id}", gooseWin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gooseWin.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.won").value(sameInstant(DEFAULT_WON)))
            .andExpect(jsonPath("$.playerId").value(DEFAULT_PLAYER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGooseWin() throws Exception {
        // Get the gooseWin
        restGooseWinMockMvc.perform(get("/api/goose-wins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGooseWin() throws Exception {
        // Initialize the database
        gooseWinRepository.saveAndFlush(gooseWin);
        gooseWinSearchRepository.save(gooseWin);
        int databaseSizeBeforeUpdate = gooseWinRepository.findAll().size();

        // Update the gooseWin
        GooseWin updatedGooseWin = gooseWinRepository.findOne(gooseWin.getId());
        // Disconnect from session so that the updates on updatedGooseWin are not directly saved in db
        em.detach(updatedGooseWin);
        updatedGooseWin
            .value(UPDATED_VALUE)
            .status(UPDATED_STATUS)
            .won(UPDATED_WON)
            .playerId(UPDATED_PLAYER_ID);
        GooseWinDTO gooseWinDTO = gooseWinMapper.toDto(updatedGooseWin);

        restGooseWinMockMvc.perform(put("/api/goose-wins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseWinDTO)))
            .andExpect(status().isOk());

        // Validate the GooseWin in the database
        List<GooseWin> gooseWinList = gooseWinRepository.findAll();
        assertThat(gooseWinList).hasSize(databaseSizeBeforeUpdate);
        GooseWin testGooseWin = gooseWinList.get(gooseWinList.size() - 1);
        assertThat(testGooseWin.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testGooseWin.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testGooseWin.getWon()).isEqualTo(UPDATED_WON);
        assertThat(testGooseWin.getPlayerId()).isEqualTo(UPDATED_PLAYER_ID);

        // Validate the GooseWin in Elasticsearch
        GooseWin gooseWinEs = gooseWinSearchRepository.findOne(testGooseWin.getId());
        assertThat(testGooseWin.getWon()).isEqualTo(testGooseWin.getWon());
        assertThat(gooseWinEs).isEqualToIgnoringGivenFields(testGooseWin, "won");
    }

    @Test
    @Transactional
    public void updateNonExistingGooseWin() throws Exception {
        int databaseSizeBeforeUpdate = gooseWinRepository.findAll().size();

        // Create the GooseWin
        GooseWinDTO gooseWinDTO = gooseWinMapper.toDto(gooseWin);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGooseWinMockMvc.perform(put("/api/goose-wins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseWinDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseWin in the database
        List<GooseWin> gooseWinList = gooseWinRepository.findAll();
        assertThat(gooseWinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGooseWin() throws Exception {
        // Initialize the database
        gooseWinRepository.saveAndFlush(gooseWin);
        gooseWinSearchRepository.save(gooseWin);
        int databaseSizeBeforeDelete = gooseWinRepository.findAll().size();

        // Get the gooseWin
        restGooseWinMockMvc.perform(delete("/api/goose-wins/{id}", gooseWin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gooseWinExistsInEs = gooseWinSearchRepository.exists(gooseWin.getId());
        assertThat(gooseWinExistsInEs).isFalse();

        // Validate the database is empty
        List<GooseWin> gooseWinList = gooseWinRepository.findAll();
        assertThat(gooseWinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGooseWin() throws Exception {
        // Initialize the database
        gooseWinRepository.saveAndFlush(gooseWin);
        gooseWinSearchRepository.save(gooseWin);

        // Search the gooseWin
        restGooseWinMockMvc.perform(get("/api/_search/goose-wins?query=id:" + gooseWin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseWin.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].won").value(hasItem(sameInstant(DEFAULT_WON))))
            .andExpect(jsonPath("$.[*].playerId").value(hasItem(DEFAULT_PLAYER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseWin.class);
        GooseWin gooseWin1 = new GooseWin();
        gooseWin1.setId(1L);
        GooseWin gooseWin2 = new GooseWin();
        gooseWin2.setId(gooseWin1.getId());
        assertThat(gooseWin1).isEqualTo(gooseWin2);
        gooseWin2.setId(2L);
        assertThat(gooseWin1).isNotEqualTo(gooseWin2);
        gooseWin1.setId(null);
        assertThat(gooseWin1).isNotEqualTo(gooseWin2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseWinDTO.class);
        GooseWinDTO gooseWinDTO1 = new GooseWinDTO();
        gooseWinDTO1.setId(1L);
        GooseWinDTO gooseWinDTO2 = new GooseWinDTO();
        assertThat(gooseWinDTO1).isNotEqualTo(gooseWinDTO2);
        gooseWinDTO2.setId(gooseWinDTO1.getId());
        assertThat(gooseWinDTO1).isEqualTo(gooseWinDTO2);
        gooseWinDTO2.setId(2L);
        assertThat(gooseWinDTO1).isNotEqualTo(gooseWinDTO2);
        gooseWinDTO1.setId(null);
        assertThat(gooseWinDTO1).isNotEqualTo(gooseWinDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gooseWinMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gooseWinMapper.fromId(null)).isNull();
    }
}
