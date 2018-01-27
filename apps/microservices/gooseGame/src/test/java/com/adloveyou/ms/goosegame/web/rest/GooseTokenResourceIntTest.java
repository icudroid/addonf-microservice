package com.adloveyou.ms.goosegame.web.rest;

import com.adloveyou.ms.goosegame.GooseGameApp;

import com.adloveyou.ms.goosegame.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.goosegame.domain.GooseToken;
import com.adloveyou.ms.goosegame.repository.GooseTokenRepository;
import com.adloveyou.ms.goosegame.service.GooseTokenService;
import com.adloveyou.ms.goosegame.repository.search.GooseTokenSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseTokenDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseTokenMapper;
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
import java.util.List;

import static com.adloveyou.ms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GooseTokenResource REST controller.
 *
 * @see GooseTokenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GooseGameApp.class, SecurityBeanOverrideConfiguration.class})
public class GooseTokenResourceIntTest {

    private static final Long DEFAULT_PLAYER_ID = 1L;
    private static final Long UPDATED_PLAYER_ID = 2L;

    @Autowired
    private GooseTokenRepository gooseTokenRepository;

    @Autowired
    private GooseTokenMapper gooseTokenMapper;

    @Autowired
    private GooseTokenService gooseTokenService;

    @Autowired
    private GooseTokenSearchRepository gooseTokenSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGooseTokenMockMvc;

    private GooseToken gooseToken;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GooseTokenResource gooseTokenResource = new GooseTokenResource(gooseTokenService);
        this.restGooseTokenMockMvc = MockMvcBuilders.standaloneSetup(gooseTokenResource)
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
    public static GooseToken createEntity(EntityManager em) {
        GooseToken gooseToken = new GooseToken()
            .playerId(DEFAULT_PLAYER_ID);
        return gooseToken;
    }

    @Before
    public void initTest() {
        gooseTokenSearchRepository.deleteAll();
        gooseToken = createEntity(em);
    }

    @Test
    @Transactional
    public void createGooseToken() throws Exception {
        int databaseSizeBeforeCreate = gooseTokenRepository.findAll().size();

        // Create the GooseToken
        GooseTokenDTO gooseTokenDTO = gooseTokenMapper.toDto(gooseToken);
        restGooseTokenMockMvc.perform(post("/api/goose-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseTokenDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseToken in the database
        List<GooseToken> gooseTokenList = gooseTokenRepository.findAll();
        assertThat(gooseTokenList).hasSize(databaseSizeBeforeCreate + 1);
        GooseToken testGooseToken = gooseTokenList.get(gooseTokenList.size() - 1);
        assertThat(testGooseToken.getPlayerId()).isEqualTo(DEFAULT_PLAYER_ID);

        // Validate the GooseToken in Elasticsearch
        GooseToken gooseTokenEs = gooseTokenSearchRepository.findOne(testGooseToken.getId());
        assertThat(gooseTokenEs).isEqualToIgnoringGivenFields(testGooseToken);
    }

    @Test
    @Transactional
    public void createGooseTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gooseTokenRepository.findAll().size();

        // Create the GooseToken with an existing ID
        gooseToken.setId(1L);
        GooseTokenDTO gooseTokenDTO = gooseTokenMapper.toDto(gooseToken);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGooseTokenMockMvc.perform(post("/api/goose-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseTokenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GooseToken in the database
        List<GooseToken> gooseTokenList = gooseTokenRepository.findAll();
        assertThat(gooseTokenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGooseTokens() throws Exception {
        // Initialize the database
        gooseTokenRepository.saveAndFlush(gooseToken);

        // Get all the gooseTokenList
        restGooseTokenMockMvc.perform(get("/api/goose-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].playerId").value(hasItem(DEFAULT_PLAYER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getGooseToken() throws Exception {
        // Initialize the database
        gooseTokenRepository.saveAndFlush(gooseToken);

        // Get the gooseToken
        restGooseTokenMockMvc.perform(get("/api/goose-tokens/{id}", gooseToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gooseToken.getId().intValue()))
            .andExpect(jsonPath("$.playerId").value(DEFAULT_PLAYER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGooseToken() throws Exception {
        // Get the gooseToken
        restGooseTokenMockMvc.perform(get("/api/goose-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGooseToken() throws Exception {
        // Initialize the database
        gooseTokenRepository.saveAndFlush(gooseToken);
        gooseTokenSearchRepository.save(gooseToken);
        int databaseSizeBeforeUpdate = gooseTokenRepository.findAll().size();

        // Update the gooseToken
        GooseToken updatedGooseToken = gooseTokenRepository.findOne(gooseToken.getId());
        // Disconnect from session so that the updates on updatedGooseToken are not directly saved in db
        em.detach(updatedGooseToken);
        updatedGooseToken
            .playerId(UPDATED_PLAYER_ID);
        GooseTokenDTO gooseTokenDTO = gooseTokenMapper.toDto(updatedGooseToken);

        restGooseTokenMockMvc.perform(put("/api/goose-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseTokenDTO)))
            .andExpect(status().isOk());

        // Validate the GooseToken in the database
        List<GooseToken> gooseTokenList = gooseTokenRepository.findAll();
        assertThat(gooseTokenList).hasSize(databaseSizeBeforeUpdate);
        GooseToken testGooseToken = gooseTokenList.get(gooseTokenList.size() - 1);
        assertThat(testGooseToken.getPlayerId()).isEqualTo(UPDATED_PLAYER_ID);

        // Validate the GooseToken in Elasticsearch
        GooseToken gooseTokenEs = gooseTokenSearchRepository.findOne(testGooseToken.getId());
        assertThat(gooseTokenEs).isEqualToIgnoringGivenFields(testGooseToken);
    }

    @Test
    @Transactional
    public void updateNonExistingGooseToken() throws Exception {
        int databaseSizeBeforeUpdate = gooseTokenRepository.findAll().size();

        // Create the GooseToken
        GooseTokenDTO gooseTokenDTO = gooseTokenMapper.toDto(gooseToken);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGooseTokenMockMvc.perform(put("/api/goose-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseTokenDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseToken in the database
        List<GooseToken> gooseTokenList = gooseTokenRepository.findAll();
        assertThat(gooseTokenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGooseToken() throws Exception {
        // Initialize the database
        gooseTokenRepository.saveAndFlush(gooseToken);
        gooseTokenSearchRepository.save(gooseToken);
        int databaseSizeBeforeDelete = gooseTokenRepository.findAll().size();

        // Get the gooseToken
        restGooseTokenMockMvc.perform(delete("/api/goose-tokens/{id}", gooseToken.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gooseTokenExistsInEs = gooseTokenSearchRepository.exists(gooseToken.getId());
        assertThat(gooseTokenExistsInEs).isFalse();

        // Validate the database is empty
        List<GooseToken> gooseTokenList = gooseTokenRepository.findAll();
        assertThat(gooseTokenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGooseToken() throws Exception {
        // Initialize the database
        gooseTokenRepository.saveAndFlush(gooseToken);
        gooseTokenSearchRepository.save(gooseToken);

        // Search the gooseToken
        restGooseTokenMockMvc.perform(get("/api/_search/goose-tokens?query=id:" + gooseToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].playerId").value(hasItem(DEFAULT_PLAYER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseToken.class);
        GooseToken gooseToken1 = new GooseToken();
        gooseToken1.setId(1L);
        GooseToken gooseToken2 = new GooseToken();
        gooseToken2.setId(gooseToken1.getId());
        assertThat(gooseToken1).isEqualTo(gooseToken2);
        gooseToken2.setId(2L);
        assertThat(gooseToken1).isNotEqualTo(gooseToken2);
        gooseToken1.setId(null);
        assertThat(gooseToken1).isNotEqualTo(gooseToken2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseTokenDTO.class);
        GooseTokenDTO gooseTokenDTO1 = new GooseTokenDTO();
        gooseTokenDTO1.setId(1L);
        GooseTokenDTO gooseTokenDTO2 = new GooseTokenDTO();
        assertThat(gooseTokenDTO1).isNotEqualTo(gooseTokenDTO2);
        gooseTokenDTO2.setId(gooseTokenDTO1.getId());
        assertThat(gooseTokenDTO1).isEqualTo(gooseTokenDTO2);
        gooseTokenDTO2.setId(2L);
        assertThat(gooseTokenDTO1).isNotEqualTo(gooseTokenDTO2);
        gooseTokenDTO1.setId(null);
        assertThat(gooseTokenDTO1).isNotEqualTo(gooseTokenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gooseTokenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gooseTokenMapper.fromId(null)).isNull();
    }
}
