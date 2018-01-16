package com.virtualsoundnw.sftpbot.web.rest;

import com.virtualsoundnw.sftpbot.SftpbotApp;

import com.virtualsoundnw.sftpbot.domain.SftpTestCase;
import com.virtualsoundnw.sftpbot.domain.Sftproot;
import com.virtualsoundnw.sftpbot.repository.SftpTestCaseRepository;
import com.virtualsoundnw.sftpbot.service.SftpTestCaseService;
import com.virtualsoundnw.sftpbot.service.dto.SftpTestCaseDTO;
import com.virtualsoundnw.sftpbot.service.mapper.SftpTestCaseMapper;
import com.virtualsoundnw.sftpbot.web.rest.errors.ExceptionTranslator;

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

import static com.virtualsoundnw.sftpbot.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SftpTestCaseResource REST controller.
 *
 * @see SftpTestCaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SftpbotApp.class)
public class SftpTestCaseResourceIntTest {

    private static final String DEFAULT_INCOMING_FILE_NAME = "AAAAAAAAAAAAA";
    private static final String UPDATED_INCOMING_FILE_NAME = "BBBBBBBBBBBBB";

    private static final String DEFAULT_RESULT_FILE_NAME = "AAAAAAAAAAAAA";
    private static final String UPDATED_RESULT_FILE_NAME = "BBBBBBBBBBBBB";

    private static final String DEFAULT_ERROR_FILE_NAME = "AAAAAAAAAAAAA";
    private static final String UPDATED_ERROR_FILE_NAME = "BBBBBBBBBBBBB";

    private static final String DEFAULT_FILE_CONTENTS = "AAAAAAAAAA";
    private static final String UPDATED_FILE_CONTENTS = "BBBBBBBBBB";

    @Autowired
    private SftpTestCaseRepository sftpTestCaseRepository;

    @Autowired
    private SftpTestCaseMapper sftpTestCaseMapper;

    @Autowired
    private SftpTestCaseService sftpTestCaseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSftpTestCaseMockMvc;

    private SftpTestCase sftpTestCase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SftpTestCaseResource sftpTestCaseResource = new SftpTestCaseResource(sftpTestCaseService);
        this.restSftpTestCaseMockMvc = MockMvcBuilders.standaloneSetup(sftpTestCaseResource)
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
    public static SftpTestCase createEntity(EntityManager em) {
        SftpTestCase sftpTestCase = new SftpTestCase()
            .incomingFileName(DEFAULT_INCOMING_FILE_NAME)
            .resultFileName(DEFAULT_RESULT_FILE_NAME)
            .errorFileName(DEFAULT_ERROR_FILE_NAME)
            .fileContents(DEFAULT_FILE_CONTENTS);
        // Add required entity
        Sftproot sftproot = SftprootResourceIntTest.createEntity(em);
        em.persist(sftproot);
        em.flush();
        sftpTestCase.setSftproot(sftproot);
        return sftpTestCase;
    }

    @Before
    public void initTest() {
        sftpTestCase = createEntity(em);
    }

    @Test
    @Transactional
    public void createSftpTestCase() throws Exception {
        int databaseSizeBeforeCreate = sftpTestCaseRepository.findAll().size();

        // Create the SftpTestCase
        SftpTestCaseDTO sftpTestCaseDTO = sftpTestCaseMapper.toDto(sftpTestCase);
        restSftpTestCaseMockMvc.perform(post("/api/sftp-test-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftpTestCaseDTO)))
            .andExpect(status().isCreated());

        // Validate the SftpTestCase in the database
        List<SftpTestCase> sftpTestCaseList = sftpTestCaseRepository.findAll();
        assertThat(sftpTestCaseList).hasSize(databaseSizeBeforeCreate + 1);
        SftpTestCase testSftpTestCase = sftpTestCaseList.get(sftpTestCaseList.size() - 1);
        assertThat(testSftpTestCase.getIncomingFileName()).isEqualTo(DEFAULT_INCOMING_FILE_NAME);
        assertThat(testSftpTestCase.getResultFileName()).isEqualTo(DEFAULT_RESULT_FILE_NAME);
        assertThat(testSftpTestCase.getErrorFileName()).isEqualTo(DEFAULT_ERROR_FILE_NAME);
        assertThat(testSftpTestCase.getFileContents()).isEqualTo(DEFAULT_FILE_CONTENTS);
    }

    @Test
    @Transactional
    public void createSftpTestCaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sftpTestCaseRepository.findAll().size();

        // Create the SftpTestCase with an existing ID
        sftpTestCase.setId(1L);
        SftpTestCaseDTO sftpTestCaseDTO = sftpTestCaseMapper.toDto(sftpTestCase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSftpTestCaseMockMvc.perform(post("/api/sftp-test-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftpTestCaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SftpTestCase in the database
        List<SftpTestCase> sftpTestCaseList = sftpTestCaseRepository.findAll();
        assertThat(sftpTestCaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIncomingFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sftpTestCaseRepository.findAll().size();
        // set the field null
        sftpTestCase.setIncomingFileName(null);

        // Create the SftpTestCase, which fails.
        SftpTestCaseDTO sftpTestCaseDTO = sftpTestCaseMapper.toDto(sftpTestCase);

        restSftpTestCaseMockMvc.perform(post("/api/sftp-test-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftpTestCaseDTO)))
            .andExpect(status().isBadRequest());

        List<SftpTestCase> sftpTestCaseList = sftpTestCaseRepository.findAll();
        assertThat(sftpTestCaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileContentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = sftpTestCaseRepository.findAll().size();
        // set the field null
        sftpTestCase.setFileContents(null);

        // Create the SftpTestCase, which fails.
        SftpTestCaseDTO sftpTestCaseDTO = sftpTestCaseMapper.toDto(sftpTestCase);

        restSftpTestCaseMockMvc.perform(post("/api/sftp-test-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftpTestCaseDTO)))
            .andExpect(status().isBadRequest());

        List<SftpTestCase> sftpTestCaseList = sftpTestCaseRepository.findAll();
        assertThat(sftpTestCaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSftpTestCases() throws Exception {
        // Initialize the database
        sftpTestCaseRepository.saveAndFlush(sftpTestCase);

        // Get all the sftpTestCaseList
        restSftpTestCaseMockMvc.perform(get("/api/sftp-test-cases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sftpTestCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].incomingFileName").value(hasItem(DEFAULT_INCOMING_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].resultFileName").value(hasItem(DEFAULT_RESULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].errorFileName").value(hasItem(DEFAULT_ERROR_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileContents").value(hasItem(DEFAULT_FILE_CONTENTS.toString())));
    }

    @Test
    @Transactional
    public void getSftpTestCase() throws Exception {
        // Initialize the database
        sftpTestCaseRepository.saveAndFlush(sftpTestCase);

        // Get the sftpTestCase
        restSftpTestCaseMockMvc.perform(get("/api/sftp-test-cases/{id}", sftpTestCase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sftpTestCase.getId().intValue()))
            .andExpect(jsonPath("$.incomingFileName").value(DEFAULT_INCOMING_FILE_NAME.toString()))
            .andExpect(jsonPath("$.resultFileName").value(DEFAULT_RESULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.errorFileName").value(DEFAULT_ERROR_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileContents").value(DEFAULT_FILE_CONTENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSftpTestCase() throws Exception {
        // Get the sftpTestCase
        restSftpTestCaseMockMvc.perform(get("/api/sftp-test-cases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSftpTestCase() throws Exception {
        // Initialize the database
        sftpTestCaseRepository.saveAndFlush(sftpTestCase);
        int databaseSizeBeforeUpdate = sftpTestCaseRepository.findAll().size();

        // Update the sftpTestCase
        SftpTestCase updatedSftpTestCase = sftpTestCaseRepository.findOne(sftpTestCase.getId());
        // Disconnect from session so that the updates on updatedSftpTestCase are not directly saved in db
        em.detach(updatedSftpTestCase);
        updatedSftpTestCase
            .incomingFileName(UPDATED_INCOMING_FILE_NAME)
            .resultFileName(UPDATED_RESULT_FILE_NAME)
            .errorFileName(UPDATED_ERROR_FILE_NAME)
            .fileContents(UPDATED_FILE_CONTENTS);
        SftpTestCaseDTO sftpTestCaseDTO = sftpTestCaseMapper.toDto(updatedSftpTestCase);

        restSftpTestCaseMockMvc.perform(put("/api/sftp-test-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftpTestCaseDTO)))
            .andExpect(status().isOk());

        // Validate the SftpTestCase in the database
        List<SftpTestCase> sftpTestCaseList = sftpTestCaseRepository.findAll();
        assertThat(sftpTestCaseList).hasSize(databaseSizeBeforeUpdate);
        SftpTestCase testSftpTestCase = sftpTestCaseList.get(sftpTestCaseList.size() - 1);
        assertThat(testSftpTestCase.getIncomingFileName()).isEqualTo(UPDATED_INCOMING_FILE_NAME);
        assertThat(testSftpTestCase.getResultFileName()).isEqualTo(UPDATED_RESULT_FILE_NAME);
        assertThat(testSftpTestCase.getErrorFileName()).isEqualTo(UPDATED_ERROR_FILE_NAME);
        assertThat(testSftpTestCase.getFileContents()).isEqualTo(UPDATED_FILE_CONTENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingSftpTestCase() throws Exception {
        int databaseSizeBeforeUpdate = sftpTestCaseRepository.findAll().size();

        // Create the SftpTestCase
        SftpTestCaseDTO sftpTestCaseDTO = sftpTestCaseMapper.toDto(sftpTestCase);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSftpTestCaseMockMvc.perform(put("/api/sftp-test-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftpTestCaseDTO)))
            .andExpect(status().isCreated());

        // Validate the SftpTestCase in the database
        List<SftpTestCase> sftpTestCaseList = sftpTestCaseRepository.findAll();
        assertThat(sftpTestCaseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSftpTestCase() throws Exception {
        // Initialize the database
        sftpTestCaseRepository.saveAndFlush(sftpTestCase);
        int databaseSizeBeforeDelete = sftpTestCaseRepository.findAll().size();

        // Get the sftpTestCase
        restSftpTestCaseMockMvc.perform(delete("/api/sftp-test-cases/{id}", sftpTestCase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SftpTestCase> sftpTestCaseList = sftpTestCaseRepository.findAll();
        assertThat(sftpTestCaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SftpTestCase.class);
        SftpTestCase sftpTestCase1 = new SftpTestCase();
        sftpTestCase1.setId(1L);
        SftpTestCase sftpTestCase2 = new SftpTestCase();
        sftpTestCase2.setId(sftpTestCase1.getId());
        assertThat(sftpTestCase1).isEqualTo(sftpTestCase2);
        sftpTestCase2.setId(2L);
        assertThat(sftpTestCase1).isNotEqualTo(sftpTestCase2);
        sftpTestCase1.setId(null);
        assertThat(sftpTestCase1).isNotEqualTo(sftpTestCase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SftpTestCaseDTO.class);
        SftpTestCaseDTO sftpTestCaseDTO1 = new SftpTestCaseDTO();
        sftpTestCaseDTO1.setId(1L);
        SftpTestCaseDTO sftpTestCaseDTO2 = new SftpTestCaseDTO();
        assertThat(sftpTestCaseDTO1).isNotEqualTo(sftpTestCaseDTO2);
        sftpTestCaseDTO2.setId(sftpTestCaseDTO1.getId());
        assertThat(sftpTestCaseDTO1).isEqualTo(sftpTestCaseDTO2);
        sftpTestCaseDTO2.setId(2L);
        assertThat(sftpTestCaseDTO1).isNotEqualTo(sftpTestCaseDTO2);
        sftpTestCaseDTO1.setId(null);
        assertThat(sftpTestCaseDTO1).isNotEqualTo(sftpTestCaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sftpTestCaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sftpTestCaseMapper.fromId(null)).isNull();
    }
}
