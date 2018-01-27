package com.adloveyou.ms.game.web.rest;

import com.adloveyou.ms.game.GameApp;

import com.adloveyou.ms.game.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.game.domain.AdGame;
import com.adloveyou.ms.game.repository.AdGameRepository;
import com.adloveyou.ms.game.service.AdGameService;
import com.adloveyou.ms.game.repository.search.AdGameSearchRepository;
import com.adloveyou.ms.game.service.dto.AdGameDTO;
import com.adloveyou.ms.game.service.mapper.AdGameMapper;
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

import com.adloveyou.ms.game.domain.enumeration.GameStatus;
/**
 * Test class for the AdGameResource REST controller.
 *
 * @see AdGameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameApp.class, SecurityBeanOverrideConfiguration.class})
public class AdGameResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final GameStatus DEFAULT_STATUS = GameStatus.WIN;
    private static final GameStatus UPDATED_STATUS = GameStatus.LOST;

    private static final Long DEFAULT_PLAYER_ID = 1L;
    private static final Long UPDATED_PLAYER_ID = 2L;

    @Autowired
    private AdGameRepository adGameRepository;

    @Autowired
    private AdGameMapper adGameMapper;

    @Autowired
    private AdGameService adGameService;

    @Autowired
    private AdGameSearchRepository adGameSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdGameMockMvc;

    private AdGame adGame;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdGameResource adGameResource = new AdGameResource(adGameService);
        this.restAdGameMockMvc = MockMvcBuilders.standaloneSetup(adGameResource)
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
    public static AdGame createEntity(EntityManager em) {
        AdGame adGame = new AdGame()
            .created(DEFAULT_CREATED)
            .status(DEFAULT_STATUS)
            .playerId(DEFAULT_PLAYER_ID);
        return adGame;
    }

    @Before
    public void initTest() {
        adGameSearchRepository.deleteAll();
        adGame = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdGame() throws Exception {
        int databaseSizeBeforeCreate = adGameRepository.findAll().size();

        // Create the AdGame
        AdGameDTO adGameDTO = adGameMapper.toDto(adGame);
        restAdGameMockMvc.perform(post("/api/ad-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adGameDTO)))
            .andExpect(status().isCreated());

        // Validate the AdGame in the database
        List<AdGame> adGameList = adGameRepository.findAll();
        assertThat(adGameList).hasSize(databaseSizeBeforeCreate + 1);
        AdGame testAdGame = adGameList.get(adGameList.size() - 1);
        assertThat(testAdGame.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testAdGame.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAdGame.getPlayerId()).isEqualTo(DEFAULT_PLAYER_ID);

        // Validate the AdGame in Elasticsearch
        AdGame adGameEs = adGameSearchRepository.findOne(testAdGame.getId());
        assertThat(testAdGame.getCreated()).isEqualTo(testAdGame.getCreated());
        assertThat(adGameEs).isEqualToIgnoringGivenFields(testAdGame, "created");
    }

    @Test
    @Transactional
    public void createAdGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adGameRepository.findAll().size();

        // Create the AdGame with an existing ID
        adGame.setId(1L);
        AdGameDTO adGameDTO = adGameMapper.toDto(adGame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdGameMockMvc.perform(post("/api/ad-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adGameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdGame in the database
        List<AdGame> adGameList = adGameRepository.findAll();
        assertThat(adGameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdGames() throws Exception {
        // Initialize the database
        adGameRepository.saveAndFlush(adGame);

        // Get all the adGameList
        restAdGameMockMvc.perform(get("/api/ad-games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adGame.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].playerId").value(hasItem(DEFAULT_PLAYER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAdGame() throws Exception {
        // Initialize the database
        adGameRepository.saveAndFlush(adGame);

        // Get the adGame
        restAdGameMockMvc.perform(get("/api/ad-games/{id}", adGame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adGame.getId().intValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.playerId").value(DEFAULT_PLAYER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdGame() throws Exception {
        // Get the adGame
        restAdGameMockMvc.perform(get("/api/ad-games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdGame() throws Exception {
        // Initialize the database
        adGameRepository.saveAndFlush(adGame);
        adGameSearchRepository.save(adGame);
        int databaseSizeBeforeUpdate = adGameRepository.findAll().size();

        // Update the adGame
        AdGame updatedAdGame = adGameRepository.findOne(adGame.getId());
        // Disconnect from session so that the updates on updatedAdGame are not directly saved in db
        em.detach(updatedAdGame);
        updatedAdGame
            .created(UPDATED_CREATED)
            .status(UPDATED_STATUS)
            .playerId(UPDATED_PLAYER_ID);
        AdGameDTO adGameDTO = adGameMapper.toDto(updatedAdGame);

        restAdGameMockMvc.perform(put("/api/ad-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adGameDTO)))
            .andExpect(status().isOk());

        // Validate the AdGame in the database
        List<AdGame> adGameList = adGameRepository.findAll();
        assertThat(adGameList).hasSize(databaseSizeBeforeUpdate);
        AdGame testAdGame = adGameList.get(adGameList.size() - 1);
        assertThat(testAdGame.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testAdGame.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAdGame.getPlayerId()).isEqualTo(UPDATED_PLAYER_ID);

        // Validate the AdGame in Elasticsearch
        AdGame adGameEs = adGameSearchRepository.findOne(testAdGame.getId());
        assertThat(testAdGame.getCreated()).isEqualTo(testAdGame.getCreated());
        assertThat(adGameEs).isEqualToIgnoringGivenFields(testAdGame, "created");
    }

    @Test
    @Transactional
    public void updateNonExistingAdGame() throws Exception {
        int databaseSizeBeforeUpdate = adGameRepository.findAll().size();

        // Create the AdGame
        AdGameDTO adGameDTO = adGameMapper.toDto(adGame);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdGameMockMvc.perform(put("/api/ad-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adGameDTO)))
            .andExpect(status().isCreated());

        // Validate the AdGame in the database
        List<AdGame> adGameList = adGameRepository.findAll();
        assertThat(adGameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdGame() throws Exception {
        // Initialize the database
        adGameRepository.saveAndFlush(adGame);
        adGameSearchRepository.save(adGame);
        int databaseSizeBeforeDelete = adGameRepository.findAll().size();

        // Get the adGame
        restAdGameMockMvc.perform(delete("/api/ad-games/{id}", adGame.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adGameExistsInEs = adGameSearchRepository.exists(adGame.getId());
        assertThat(adGameExistsInEs).isFalse();

        // Validate the database is empty
        List<AdGame> adGameList = adGameRepository.findAll();
        assertThat(adGameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdGame() throws Exception {
        // Initialize the database
        adGameRepository.saveAndFlush(adGame);
        adGameSearchRepository.save(adGame);

        // Search the adGame
        restAdGameMockMvc.perform(get("/api/_search/ad-games?query=id:" + adGame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adGame.getId().intValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].playerId").value(hasItem(DEFAULT_PLAYER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdGame.class);
        AdGame adGame1 = new AdGame();
        adGame1.setId(1L);
        AdGame adGame2 = new AdGame();
        adGame2.setId(adGame1.getId());
        assertThat(adGame1).isEqualTo(adGame2);
        adGame2.setId(2L);
        assertThat(adGame1).isNotEqualTo(adGame2);
        adGame1.setId(null);
        assertThat(adGame1).isNotEqualTo(adGame2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdGameDTO.class);
        AdGameDTO adGameDTO1 = new AdGameDTO();
        adGameDTO1.setId(1L);
        AdGameDTO adGameDTO2 = new AdGameDTO();
        assertThat(adGameDTO1).isNotEqualTo(adGameDTO2);
        adGameDTO2.setId(adGameDTO1.getId());
        assertThat(adGameDTO1).isEqualTo(adGameDTO2);
        adGameDTO2.setId(2L);
        assertThat(adGameDTO1).isNotEqualTo(adGameDTO2);
        adGameDTO1.setId(null);
        assertThat(adGameDTO1).isNotEqualTo(adGameDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adGameMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adGameMapper.fromId(null)).isNull();
    }
}
