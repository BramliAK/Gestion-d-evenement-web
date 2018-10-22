package com.mono.web.rest;

import com.mono.BackendApp;

import com.mono.domain.Dateevent;
import com.mono.repository.DateeventRepository;
import com.mono.service.DateeventService;
import com.mono.web.rest.errors.ExceptionTranslator;

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
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.mono.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DateeventResource REST controller.
 *
 * @see DateeventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApp.class)
public class DateeventResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DateeventRepository dateeventRepository;
    
    @Autowired
    private DateeventService dateeventService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDateeventMockMvc;

    private Dateevent dateevent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DateeventResource dateeventResource = new DateeventResource(dateeventService);
        this.restDateeventMockMvc = MockMvcBuilders.standaloneSetup(dateeventResource)
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
    public static Dateevent createEntity(EntityManager em) {
        Dateevent dateevent = new Dateevent()
            .date(DEFAULT_DATE);
        return dateevent;
    }

    @Before
    public void initTest() {
        dateevent = createEntity(em);
    }

    @Test
    @Transactional
    public void createDateevent() throws Exception {
        int databaseSizeBeforeCreate = dateeventRepository.findAll().size();

        // Create the Dateevent
        restDateeventMockMvc.perform(post("/api/dateevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dateevent)))
            .andExpect(status().isCreated());

        // Validate the Dateevent in the database
        List<Dateevent> dateeventList = dateeventRepository.findAll();
        assertThat(dateeventList).hasSize(databaseSizeBeforeCreate + 1);
        Dateevent testDateevent = dateeventList.get(dateeventList.size() - 1);
        assertThat(testDateevent.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDateeventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dateeventRepository.findAll().size();

        // Create the Dateevent with an existing ID
        dateevent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDateeventMockMvc.perform(post("/api/dateevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dateevent)))
            .andExpect(status().isBadRequest());

        // Validate the Dateevent in the database
        List<Dateevent> dateeventList = dateeventRepository.findAll();
        assertThat(dateeventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dateeventRepository.findAll().size();
        // set the field null
        dateevent.setDate(null);

        // Create the Dateevent, which fails.

        restDateeventMockMvc.perform(post("/api/dateevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dateevent)))
            .andExpect(status().isBadRequest());

        List<Dateevent> dateeventList = dateeventRepository.findAll();
        assertThat(dateeventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDateevents() throws Exception {
        // Initialize the database
        dateeventRepository.saveAndFlush(dateevent);

        // Get all the dateeventList
        restDateeventMockMvc.perform(get("/api/dateevents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dateevent.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDateevent() throws Exception {
        // Initialize the database
        dateeventRepository.saveAndFlush(dateevent);

        // Get the dateevent
        restDateeventMockMvc.perform(get("/api/dateevents/{id}", dateevent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dateevent.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDateevent() throws Exception {
        // Get the dateevent
        restDateeventMockMvc.perform(get("/api/dateevents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDateevent() throws Exception {
        // Initialize the database
        dateeventService.save(dateevent);

        int databaseSizeBeforeUpdate = dateeventRepository.findAll().size();

        // Update the dateevent
        Dateevent updatedDateevent = dateeventRepository.findById(dateevent.getId()).get();
        // Disconnect from session so that the updates on updatedDateevent are not directly saved in db
        em.detach(updatedDateevent);
        updatedDateevent
            .date(UPDATED_DATE);

        restDateeventMockMvc.perform(put("/api/dateevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDateevent)))
            .andExpect(status().isOk());

        // Validate the Dateevent in the database
        List<Dateevent> dateeventList = dateeventRepository.findAll();
        assertThat(dateeventList).hasSize(databaseSizeBeforeUpdate);
        Dateevent testDateevent = dateeventList.get(dateeventList.size() - 1);
        assertThat(testDateevent.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDateevent() throws Exception {
        int databaseSizeBeforeUpdate = dateeventRepository.findAll().size();

        // Create the Dateevent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDateeventMockMvc.perform(put("/api/dateevents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dateevent)))
            .andExpect(status().isBadRequest());

        // Validate the Dateevent in the database
        List<Dateevent> dateeventList = dateeventRepository.findAll();
        assertThat(dateeventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDateevent() throws Exception {
        // Initialize the database
        dateeventService.save(dateevent);

        int databaseSizeBeforeDelete = dateeventRepository.findAll().size();

        // Get the dateevent
        restDateeventMockMvc.perform(delete("/api/dateevents/{id}", dateevent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dateevent> dateeventList = dateeventRepository.findAll();
        assertThat(dateeventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dateevent.class);
        Dateevent dateevent1 = new Dateevent();
        dateevent1.setId(1L);
        Dateevent dateevent2 = new Dateevent();
        dateevent2.setId(dateevent1.getId());
        assertThat(dateevent1).isEqualTo(dateevent2);
        dateevent2.setId(2L);
        assertThat(dateevent1).isNotEqualTo(dateevent2);
        dateevent1.setId(null);
        assertThat(dateevent1).isNotEqualTo(dateevent2);
    }
}
