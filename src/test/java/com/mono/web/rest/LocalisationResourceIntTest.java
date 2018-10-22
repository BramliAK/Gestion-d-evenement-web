package com.mono.web.rest;

import com.mono.BackendApp;

import com.mono.domain.Localisation;
import com.mono.repository.LocalisationRepository;
import com.mono.service.LocalisationService;
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
import java.util.List;


import static com.mono.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mono.domain.enumeration.Emplacement;
/**
 * Test class for the LocalisationResource REST controller.
 *
 * @see LocalisationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApp.class)
public class LocalisationResourceIntTest {

    private static final String DEFAULT_NOMEMPLACEMENT = "AAAAAAAAAA";
    private static final String UPDATED_NOMEMPLACEMENT = "BBBBBBBBBB";

    private static final Emplacement DEFAULT_EMPLACEMENT = Emplacement.Ariana;
    private static final Emplacement UPDATED_EMPLACEMENT = Emplacement.Beja;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    @Autowired
    private LocalisationRepository localisationRepository;
    
    @Autowired
    private LocalisationService localisationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalisationMockMvc;

    private Localisation localisation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalisationResource localisationResource = new LocalisationResource(localisationService);
        this.restLocalisationMockMvc = MockMvcBuilders.standaloneSetup(localisationResource)
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
    public static Localisation createEntity(EntityManager em) {
        Localisation localisation = new Localisation()
            .nomemplacement(DEFAULT_NOMEMPLACEMENT)
            .emplacement(DEFAULT_EMPLACEMENT)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE);
        return localisation;
    }

    @Before
    public void initTest() {
        localisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalisation() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // Create the Localisation
        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isCreated());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate + 1);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getNomemplacement()).isEqualTo(DEFAULT_NOMEMPLACEMENT);
        assertThat(testLocalisation.getEmplacement()).isEqualTo(DEFAULT_EMPLACEMENT);
        assertThat(testLocalisation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocalisation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    public void createLocalisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // Create the Localisation with an existing ID
        localisation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomemplacementIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setNomemplacement(null);

        // Create the Localisation, which fails.

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmplacementIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setEmplacement(null);

        // Create the Localisation, which fails.

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLongitude(null);

        // Create the Localisation, which fails.

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLatitude(null);

        // Create the Localisation, which fails.

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalisations() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get all the localisationList
        restLocalisationMockMvc.perform(get("/api/localisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomemplacement").value(hasItem(DEFAULT_NOMEMPLACEMENT.toString())))
            .andExpect(jsonPath("$.[*].emplacement").value(hasItem(DEFAULT_EMPLACEMENT.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localisation.getId().intValue()))
            .andExpect(jsonPath("$.nomemplacement").value(DEFAULT_NOMEMPLACEMENT.toString()))
            .andExpect(jsonPath("$.emplacement").value(DEFAULT_EMPLACEMENT.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalisation() throws Exception {
        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalisation() throws Exception {
        // Initialize the database
        localisationService.save(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findById(localisation.getId()).get();
        // Disconnect from session so that the updates on updatedLocalisation are not directly saved in db
        em.detach(updatedLocalisation);
        updatedLocalisation
            .nomemplacement(UPDATED_NOMEMPLACEMENT)
            .emplacement(UPDATED_EMPLACEMENT)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);

        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalisation)))
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getNomemplacement()).isEqualTo(UPDATED_NOMEMPLACEMENT);
        assertThat(testLocalisation.getEmplacement()).isEqualTo(UPDATED_EMPLACEMENT);
        assertThat(testLocalisation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocalisation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Create the Localisation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalisation() throws Exception {
        // Initialize the database
        localisationService.save(localisation);

        int databaseSizeBeforeDelete = localisationRepository.findAll().size();

        // Get the localisation
        restLocalisationMockMvc.perform(delete("/api/localisations/{id}", localisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localisation.class);
        Localisation localisation1 = new Localisation();
        localisation1.setId(1L);
        Localisation localisation2 = new Localisation();
        localisation2.setId(localisation1.getId());
        assertThat(localisation1).isEqualTo(localisation2);
        localisation2.setId(2L);
        assertThat(localisation1).isNotEqualTo(localisation2);
        localisation1.setId(null);
        assertThat(localisation1).isNotEqualTo(localisation2);
    }
}
