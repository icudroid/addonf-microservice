package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.CustomerTarget;
import com.adloveyou.ms.repository.CustomerTargetRepository;
import com.adloveyou.ms.service.CustomerTargetService;
import com.adloveyou.ms.repository.search.CustomerTargetSearchRepository;
import com.adloveyou.ms.service.dto.CustomerTargetDTO;
import com.adloveyou.ms.service.mapper.CustomerTargetMapper;
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

import com.adloveyou.ms.domain.enumeration.Sex;
import com.adloveyou.ms.domain.enumeration.AgeGroup;
/**
 * Test class for the CustomerTargetResource REST controller.
 *
 * @see CustomerTargetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class CustomerTargetResourceIntTest {

    private static final Sex DEFAULT_SEX = Sex.MR;
    private static final Sex UPDATED_SEX = Sex.MME;

    private static final AgeGroup DEFAULT_AGE = AgeGroup.FROM_0_TO_9;
    private static final AgeGroup UPDATED_AGE = AgeGroup.FROM_10_TO_14;

    @Autowired
    private CustomerTargetRepository customerTargetRepository;

    @Autowired
    private CustomerTargetMapper customerTargetMapper;

    @Autowired
    private CustomerTargetService customerTargetService;

    @Autowired
    private CustomerTargetSearchRepository customerTargetSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerTargetMockMvc;

    private CustomerTarget customerTarget;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerTargetResource customerTargetResource = new CustomerTargetResource(customerTargetService);
        this.restCustomerTargetMockMvc = MockMvcBuilders.standaloneSetup(customerTargetResource)
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
    public static CustomerTarget createEntity(EntityManager em) {
        CustomerTarget customerTarget = new CustomerTarget()
            .sex(DEFAULT_SEX)
            .age(DEFAULT_AGE);
        return customerTarget;
    }

    @Before
    public void initTest() {
        customerTargetSearchRepository.deleteAll();
        customerTarget = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerTarget() throws Exception {
        int databaseSizeBeforeCreate = customerTargetRepository.findAll().size();

        // Create the CustomerTarget
        CustomerTargetDTO customerTargetDTO = customerTargetMapper.toDto(customerTarget);
        restCustomerTargetMockMvc.perform(post("/api/customer-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTargetDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerTarget in the database
        List<CustomerTarget> customerTargetList = customerTargetRepository.findAll();
        assertThat(customerTargetList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerTarget testCustomerTarget = customerTargetList.get(customerTargetList.size() - 1);
        assertThat(testCustomerTarget.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testCustomerTarget.getAge()).isEqualTo(DEFAULT_AGE);

        // Validate the CustomerTarget in Elasticsearch
        CustomerTarget customerTargetEs = customerTargetSearchRepository.findOne(testCustomerTarget.getId());
        assertThat(customerTargetEs).isEqualToIgnoringGivenFields(testCustomerTarget);
    }

    @Test
    @Transactional
    public void createCustomerTargetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerTargetRepository.findAll().size();

        // Create the CustomerTarget with an existing ID
        customerTarget.setId(1L);
        CustomerTargetDTO customerTargetDTO = customerTargetMapper.toDto(customerTarget);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerTargetMockMvc.perform(post("/api/customer-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTargetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerTarget in the database
        List<CustomerTarget> customerTargetList = customerTargetRepository.findAll();
        assertThat(customerTargetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerTargets() throws Exception {
        // Initialize the database
        customerTargetRepository.saveAndFlush(customerTarget);

        // Get all the customerTargetList
        restCustomerTargetMockMvc.perform(get("/api/customer-targets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.toString())));
    }

    @Test
    @Transactional
    public void getCustomerTarget() throws Exception {
        // Initialize the database
        customerTargetRepository.saveAndFlush(customerTarget);

        // Get the customerTarget
        restCustomerTargetMockMvc.perform(get("/api/customer-targets/{id}", customerTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerTarget.getId().intValue()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerTarget() throws Exception {
        // Get the customerTarget
        restCustomerTargetMockMvc.perform(get("/api/customer-targets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerTarget() throws Exception {
        // Initialize the database
        customerTargetRepository.saveAndFlush(customerTarget);
        customerTargetSearchRepository.save(customerTarget);
        int databaseSizeBeforeUpdate = customerTargetRepository.findAll().size();

        // Update the customerTarget
        CustomerTarget updatedCustomerTarget = customerTargetRepository.findOne(customerTarget.getId());
        // Disconnect from session so that the updates on updatedCustomerTarget are not directly saved in db
        em.detach(updatedCustomerTarget);
        updatedCustomerTarget
            .sex(UPDATED_SEX)
            .age(UPDATED_AGE);
        CustomerTargetDTO customerTargetDTO = customerTargetMapper.toDto(updatedCustomerTarget);

        restCustomerTargetMockMvc.perform(put("/api/customer-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTargetDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerTarget in the database
        List<CustomerTarget> customerTargetList = customerTargetRepository.findAll();
        assertThat(customerTargetList).hasSize(databaseSizeBeforeUpdate);
        CustomerTarget testCustomerTarget = customerTargetList.get(customerTargetList.size() - 1);
        assertThat(testCustomerTarget.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testCustomerTarget.getAge()).isEqualTo(UPDATED_AGE);

        // Validate the CustomerTarget in Elasticsearch
        CustomerTarget customerTargetEs = customerTargetSearchRepository.findOne(testCustomerTarget.getId());
        assertThat(customerTargetEs).isEqualToIgnoringGivenFields(testCustomerTarget);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerTarget() throws Exception {
        int databaseSizeBeforeUpdate = customerTargetRepository.findAll().size();

        // Create the CustomerTarget
        CustomerTargetDTO customerTargetDTO = customerTargetMapper.toDto(customerTarget);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerTargetMockMvc.perform(put("/api/customer-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTargetDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerTarget in the database
        List<CustomerTarget> customerTargetList = customerTargetRepository.findAll();
        assertThat(customerTargetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerTarget() throws Exception {
        // Initialize the database
        customerTargetRepository.saveAndFlush(customerTarget);
        customerTargetSearchRepository.save(customerTarget);
        int databaseSizeBeforeDelete = customerTargetRepository.findAll().size();

        // Get the customerTarget
        restCustomerTargetMockMvc.perform(delete("/api/customer-targets/{id}", customerTarget.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerTargetExistsInEs = customerTargetSearchRepository.exists(customerTarget.getId());
        assertThat(customerTargetExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerTarget> customerTargetList = customerTargetRepository.findAll();
        assertThat(customerTargetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerTarget() throws Exception {
        // Initialize the database
        customerTargetRepository.saveAndFlush(customerTarget);
        customerTargetSearchRepository.save(customerTarget);

        // Search the customerTarget
        restCustomerTargetMockMvc.perform(get("/api/_search/customer-targets?query=id:" + customerTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTarget.class);
        CustomerTarget customerTarget1 = new CustomerTarget();
        customerTarget1.setId(1L);
        CustomerTarget customerTarget2 = new CustomerTarget();
        customerTarget2.setId(customerTarget1.getId());
        assertThat(customerTarget1).isEqualTo(customerTarget2);
        customerTarget2.setId(2L);
        assertThat(customerTarget1).isNotEqualTo(customerTarget2);
        customerTarget1.setId(null);
        assertThat(customerTarget1).isNotEqualTo(customerTarget2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTargetDTO.class);
        CustomerTargetDTO customerTargetDTO1 = new CustomerTargetDTO();
        customerTargetDTO1.setId(1L);
        CustomerTargetDTO customerTargetDTO2 = new CustomerTargetDTO();
        assertThat(customerTargetDTO1).isNotEqualTo(customerTargetDTO2);
        customerTargetDTO2.setId(customerTargetDTO1.getId());
        assertThat(customerTargetDTO1).isEqualTo(customerTargetDTO2);
        customerTargetDTO2.setId(2L);
        assertThat(customerTargetDTO1).isNotEqualTo(customerTargetDTO2);
        customerTargetDTO1.setId(null);
        assertThat(customerTargetDTO1).isNotEqualTo(customerTargetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerTargetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerTargetMapper.fromId(null)).isNull();
    }
}
