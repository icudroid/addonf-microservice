package com.adloveyou.ms.goosegame.web.rest;

import com.adloveyou.ms.goosegame.GooseGameApp;

import com.adloveyou.ms.goosegame.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.goosegame.domain.GooseGame;
import com.adloveyou.ms.goosegame.repository.GooseGameRepository;
import com.adloveyou.ms.goosegame.service.GooseGameService;
import com.adloveyou.ms.goosegame.repository.search.GooseGameSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseGameDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseGameMapper;
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

import com.adloveyou.ms.goosegame.domain.enumeration.GooseGameStatus;
/**
 * Test class for the GooseGameResource REST controller.
 *
 * @see GooseGameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GooseGameApp.class, SecurityBeanOverrideConfiguration.class})
public class GooseGameResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final GooseGameStatus DEFAULT_STATUS = GooseGameStatus.ENABLED;
    private static final GooseGameStatus UPDATED_STATUS = GooseGameStatus.DISABLED;

    @Autowired
    private GooseGameRepository gooseGameRepository;

    @Autowired
    private GooseGameMapper gooseGameMapper;

    @Autowired
    private GooseGameService gooseGameService;

    @Autowired
    private GooseGameSearchRepository gooseGameSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGooseGameMockMvc;

    private GooseGame gooseGame;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GooseGameResource gooseGameResource = new GooseGameResource(gooseGameService);
        this.restGooseGameMockMvc = MockMvcBuilders.standaloneSetup(gooseGameResource)
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
    public static GooseGame createEntity(EntityManager em) {
        GooseGame gooseGame = new GooseGame()
            .name(DEFAULT_NAME)
            .created(DEFAULT_CREATED)
            .status(DEFAULT_STATUS);
        return gooseGame;
    }

    @Before
    public void initTest() {
        gooseGameSearchRepository.deleteAll();
        gooseGame = createEntity(em);
    }

    @Test
    @Transactional
    public void createGooseGame() throws Exception {
        int databaseSizeBeforeCreate = gooseGameRepository.findAll().size();

        // Create the GooseGame
        GooseGameDTO gooseGameDTO = gooseGameMapper.toDto(gooseGame);
        restGooseGameMockMvc.perform(post("/api/goose-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseGameDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseGame in the database
        List<GooseGame> gooseGameList = gooseGameRepository.findAll();
        assertThat(gooseGameList).hasSize(databaseSizeBeforeCreate + 1);
        GooseGame testGooseGame = gooseGameList.get(gooseGameList.size() - 1);
        assertThat(testGooseGame.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGooseGame.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testGooseGame.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the GooseGame in Elasticsearch
        GooseGame gooseGameEs = gooseGameSearchRepository.findOne(testGooseGame.getId());
        assertThat(testGooseGame.getCreated()).isEqualTo(testGooseGame.getCreated());
        assertThat(gooseGameEs).isEqualToIgnoringGivenFields(testGooseGame, "created");
    }

    @Test
    @Transactional
    public void createGooseGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gooseGameRepository.findAll().size();

        // Create the GooseGame with an existing ID
        gooseGame.setId(1L);
        GooseGameDTO gooseGameDTO = gooseGameMapper.toDto(gooseGame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGooseGameMockMvc.perform(post("/api/goose-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseGameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GooseGame in the database
        List<GooseGame> gooseGameList = gooseGameRepository.findAll();
        assertThat(gooseGameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGooseGames() throws Exception {
        // Initialize the database
        gooseGameRepository.saveAndFlush(gooseGame);

        // Get all the gooseGameList
        restGooseGameMockMvc.perform(get("/api/goose-games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseGame.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getGooseGame() throws Exception {
        // Initialize the database
        gooseGameRepository.saveAndFlush(gooseGame);

        // Get the gooseGame
        restGooseGameMockMvc.perform(get("/api/goose-games/{id}", gooseGame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gooseGame.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGooseGame() throws Exception {
        // Get the gooseGame
        restGooseGameMockMvc.perform(get("/api/goose-games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGooseGame() throws Exception {
        // Initialize the database
        gooseGameRepository.saveAndFlush(gooseGame);
        gooseGameSearchRepository.save(gooseGame);
        int databaseSizeBeforeUpdate = gooseGameRepository.findAll().size();

        // Update the gooseGame
        GooseGame updatedGooseGame = gooseGameRepository.findOne(gooseGame.getId());
        // Disconnect from session so that the updates on updatedGooseGame are not directly saved in db
        em.detach(updatedGooseGame);
        updatedGooseGame
            .name(UPDATED_NAME)
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS);
        GooseGameDTO gooseGameDTO = gooseGameMapper.toDto(updatedGooseGame);

        restGooseGameMockMvc.perform(put("/api/goose-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseGameDTO)))
            .andExpect(status().isOk());

        // Validate the GooseGame in the database
        List<GooseGame> gooseGameList = gooseGameRepository.findAll();
        assertThat(gooseGameList).hasSize(databaseSizeBeforeUpdate);
        GooseGame testGooseGame = gooseGameList.get(gooseGameList.size() - 1);
        assertThat(testGooseGame.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGooseGame.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testGooseGame.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the GooseGame in Elasticsearch
        GooseGame gooseGameEs = gooseGameSearchRepository.findOne(testGooseGame.getId());
        assertThat(testGooseGame.getCreated()).isEqualTo(testGooseGame.getCreated());
        assertThat(gooseGameEs).isEqualToIgnoringGivenFields(testGooseGame, "created");
    }

    @Test
    @Transactional
    public void updateNonExistingGooseGame() throws Exception {
        int databaseSizeBeforeUpdate = gooseGameRepository.findAll().size();

        // Create the GooseGame
        GooseGameDTO gooseGameDTO = gooseGameMapper.toDto(gooseGame);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGooseGameMockMvc.perform(put("/api/goose-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseGameDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseGame in the database
        List<GooseGame> gooseGameList = gooseGameRepository.findAll();
        assertThat(gooseGameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGooseGame() throws Exception {
        // Initialize the database
        gooseGameRepository.saveAndFlush(gooseGame);
        gooseGameSearchRepository.save(gooseGame);
        int databaseSizeBeforeDelete = gooseGameRepository.findAll().size();

        // Get the gooseGame
        restGooseGameMockMvc.perform(delete("/api/goose-games/{id}", gooseGame.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gooseGameExistsInEs = gooseGameSearchRepository.exists(gooseGame.getId());
        assertThat(gooseGameExistsInEs).isFalse();

        // Validate the database is empty
        List<GooseGame> gooseGameList = gooseGameRepository.findAll();
        assertThat(gooseGameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGooseGame() throws Exception {
        // Initialize the database
        gooseGameRepository.saveAndFlush(gooseGame);
        gooseGameSearchRepository.save(gooseGame);

        // Search the gooseGame
        restGooseGameMockMvc.perform(get("/api/_search/goose-games?query=id:" + gooseGame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseGame.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseGame.class);
        GooseGame gooseGame1 = new GooseGame();
        gooseGame1.setId(1L);
        GooseGame gooseGame2 = new GooseGame();
        gooseGame2.setId(gooseGame1.getId());
        assertThat(gooseGame1).isEqualTo(gooseGame2);
        gooseGame2.setId(2L);
        assertThat(gooseGame1).isNotEqualTo(gooseGame2);
        gooseGame1.setId(null);
        assertThat(gooseGame1).isNotEqualTo(gooseGame2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseGameDTO.class);
        GooseGameDTO gooseGameDTO1 = new GooseGameDTO();
        gooseGameDTO1.setId(1L);
        GooseGameDTO gooseGameDTO2 = new GooseGameDTO();
        assertThat(gooseGameDTO1).isNotEqualTo(gooseGameDTO2);
        gooseGameDTO2.setId(gooseGameDTO1.getId());
        assertThat(gooseGameDTO1).isEqualTo(gooseGameDTO2);
        gooseGameDTO2.setId(2L);
        assertThat(gooseGameDTO1).isNotEqualTo(gooseGameDTO2);
        gooseGameDTO1.setId(null);
        assertThat(gooseGameDTO1).isNotEqualTo(gooseGameDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gooseGameMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gooseGameMapper.fromId(null)).isNull();
    }
}
