package com.mono.web.rest;

import com.mono.BackendApp;

import com.mono.domain.Evenement;
import com.mono.repository.EvenementRepository;
import com.mono.service.EvenementService;
import com.mono.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.mono.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mono.domain.enumeration.Typeevent;
/**
 * Test class for the EvenementResource REST controller.
 *
 * @see EvenementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApp.class)
public class EvenementResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Typeevent DEFAULT_TYPEEVNET = Typeevent.Film;
    private static final Typeevent UPDATED_TYPEEVNET = Typeevent.Theatre;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;

    @Autowired
    private EvenementRepository evenementRepository;

    @Mock
    private EvenementRepository evenementRepositoryMock;
    

    @Mock
    private EvenementService evenementServiceMock;

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEvenementMockMvc;

    private Evenement evenement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvenementResource evenementResource = new EvenementResource(evenementService);
        this.restEvenementMockMvc = MockMvcBuilders.standaloneSetup(evenementResource)
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
    public static Evenement createEntity(EntityManager em) {
        Evenement evenement = new Evenement()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .typeevnet(DEFAULT_TYPEEVNET)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .prix(DEFAULT_PRIX);
        return evenement;
    }

    @Before
    public void initTest() {
        evenement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvenement() throws Exception {
        int databaseSizeBeforeCreate = evenementRepository.findAll().size();

        // Create the Evenement
        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenement)))
            .andExpect(status().isCreated());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeCreate + 1);
        Evenement testEvenement = evenementList.get(evenementList.size() - 1);
        assertThat(testEvenement.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEvenement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvenement.getTypeevnet()).isEqualTo(DEFAULT_TYPEEVNET);
        assertThat(testEvenement.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testEvenement.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testEvenement.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createEvenementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evenementRepository.findAll().size();

        // Create the Evenement with an existing ID
        evenement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenement)))
            .andExpect(status().isBadRequest());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = evenementRepository.findAll().size();
        // set the field null
        evenement.setNom(null);

        // Create the Evenement, which fails.

        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenement)))
            .andExpect(status().isBadRequest());

        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeevnetIsRequired() throws Exception {
        int databaseSizeBeforeTest = evenementRepository.findAll().size();
        // set the field null
        evenement.setTypeevnet(null);

        // Create the Evenement, which fails.

        restEvenementMockMvc.perform(post("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenement)))
            .andExpect(status().isBadRequest());

        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvenements() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get all the evenementList
        restEvenementMockMvc.perform(get("/api/evenements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evenement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].typeevnet").value(hasItem(DEFAULT_TYPEEVNET.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }
    
    public void getAllEvenementsWithEagerRelationshipsIsEnabled() throws Exception {
        EvenementResource evenementResource = new EvenementResource(evenementServiceMock);
        when(evenementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEvenementMockMvc = MockMvcBuilders.standaloneSetup(evenementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEvenementMockMvc.perform(get("/api/evenements?eagerload=true"))
        .andExpect(status().isOk());

        verify(evenementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllEvenementsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EvenementResource evenementResource = new EvenementResource(evenementServiceMock);
            when(evenementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEvenementMockMvc = MockMvcBuilders.standaloneSetup(evenementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEvenementMockMvc.perform(get("/api/evenements?eagerload=true"))
        .andExpect(status().isOk());

            verify(evenementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEvenement() throws Exception {
        // Initialize the database
        evenementRepository.saveAndFlush(evenement);

        // Get the evenement
        restEvenementMockMvc.perform(get("/api/evenements/{id}", evenement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evenement.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.typeevnet").value(DEFAULT_TYPEEVNET.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEvenement() throws Exception {
        // Get the evenement
        restEvenementMockMvc.perform(get("/api/evenements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvenement() throws Exception {
        // Initialize the database
        evenementService.save(evenement);

        int databaseSizeBeforeUpdate = evenementRepository.findAll().size();

        // Update the evenement
        Evenement updatedEvenement = evenementRepository.findById(evenement.getId()).get();
        // Disconnect from session so that the updates on updatedEvenement are not directly saved in db
        em.detach(updatedEvenement);
        updatedEvenement
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .typeevnet(UPDATED_TYPEEVNET)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .prix(UPDATED_PRIX);

        restEvenementMockMvc.perform(put("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvenement)))
            .andExpect(status().isOk());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeUpdate);
        Evenement testEvenement = evenementList.get(evenementList.size() - 1);
        assertThat(testEvenement.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEvenement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvenement.getTypeevnet()).isEqualTo(UPDATED_TYPEEVNET);
        assertThat(testEvenement.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testEvenement.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testEvenement.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingEvenement() throws Exception {
        int databaseSizeBeforeUpdate = evenementRepository.findAll().size();

        // Create the Evenement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvenementMockMvc.perform(put("/api/evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evenement)))
            .andExpect(status().isBadRequest());

        // Validate the Evenement in the database
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvenement() throws Exception {
        // Initialize the database
        evenementService.save(evenement);

        int databaseSizeBeforeDelete = evenementRepository.findAll().size();

        // Get the evenement
        restEvenementMockMvc.perform(delete("/api/evenements/{id}", evenement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Evenement> evenementList = evenementRepository.findAll();
        assertThat(evenementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evenement.class);
        Evenement evenement1 = new Evenement();
        evenement1.setId(1L);
        Evenement evenement2 = new Evenement();
        evenement2.setId(evenement1.getId());
        assertThat(evenement1).isEqualTo(evenement2);
        evenement2.setId(2L);
        assertThat(evenement1).isNotEqualTo(evenement2);
        evenement1.setId(null);
        assertThat(evenement1).isNotEqualTo(evenement2);
    }
}
