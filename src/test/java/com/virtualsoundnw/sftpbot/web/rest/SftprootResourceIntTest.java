package com.virtualsoundnw.sftpbot.web.rest;

import com.virtualsoundnw.sftpbot.SftpbotApp;

import com.virtualsoundnw.sftpbot.domain.Sftproot;
import com.virtualsoundnw.sftpbot.repository.SftprootRepository;
import com.virtualsoundnw.sftpbot.service.SftprootService;
import com.virtualsoundnw.sftpbot.service.dto.SftprootDTO;
import com.virtualsoundnw.sftpbot.service.mapper.SftprootMapper;
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
 * Test class for the SftprootResource REST controller.
 *
 * @see SftprootResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SftpbotApp.class)
public class SftprootResourceIntTest {

    private static final String DEFAULT_INCOMING_DIRECTORY = "AAAAAAAAAA";
    private static final String UPDATED_INCOMING_DIRECTORY = "BBBBBBBBBB";

    private static final String DEFAULT_OUTGOING_DIRECTORY = "AAAAAAAAAA";
    private static final String UPDATED_OUTGOING_DIRECTORY = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_DIRECTORY = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_DIRECTORY = "BBBBBBBBBB";

    @Autowired
    private SftprootRepository sftprootRepository;

    @Autowired
    private SftprootMapper sftprootMapper;

    @Autowired
    private SftprootService sftprootService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSftprootMockMvc;

    private Sftproot sftproot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SftprootResource sftprootResource = new SftprootResource(sftprootService);
        this.restSftprootMockMvc = MockMvcBuilders.standaloneSetup(sftprootResource)
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
    public static Sftproot createEntity(EntityManager em) {
        Sftproot sftproot = new Sftproot()
            .incomingDirectory(DEFAULT_INCOMING_DIRECTORY)
            .outgoingDirectory(DEFAULT_OUTGOING_DIRECTORY)
            .errorDirectory(DEFAULT_ERROR_DIRECTORY);
        return sftproot;
    }

    @Before
    public void initTest() {
        sftproot = createEntity(em);
    }

    @Test
    @Transactional
    public void createSftproot() throws Exception {
        int databaseSizeBeforeCreate = sftprootRepository.findAll().size();

        // Create the Sftproot
        SftprootDTO sftprootDTO = sftprootMapper.toDto(sftproot);
        restSftprootMockMvc.perform(post("/api/sftproots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftprootDTO)))
            .andExpect(status().isCreated());

        // Validate the Sftproot in the database
        List<Sftproot> sftprootList = sftprootRepository.findAll();
        assertThat(sftprootList).hasSize(databaseSizeBeforeCreate + 1);
        Sftproot testSftproot = sftprootList.get(sftprootList.size() - 1);
        assertThat(testSftproot.getIncomingDirectory()).isEqualTo(DEFAULT_INCOMING_DIRECTORY);
        assertThat(testSftproot.getOutgoingDirectory()).isEqualTo(DEFAULT_OUTGOING_DIRECTORY);
        assertThat(testSftproot.getErrorDirectory()).isEqualTo(DEFAULT_ERROR_DIRECTORY);
    }

    @Test
    @Transactional
    public void createSftprootWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sftprootRepository.findAll().size();

        // Create the Sftproot with an existing ID
        sftproot.setId(1L);
        SftprootDTO sftprootDTO = sftprootMapper.toDto(sftproot);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSftprootMockMvc.perform(post("/api/sftproots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftprootDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sftproot in the database
        List<Sftproot> sftprootList = sftprootRepository.findAll();
        assertThat(sftprootList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSftproots() throws Exception {
        // Initialize the database
        sftprootRepository.saveAndFlush(sftproot);

        // Get all the sftprootList
        restSftprootMockMvc.perform(get("/api/sftproots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sftproot.getId().intValue())))
            .andExpect(jsonPath("$.[*].incomingDirectory").value(hasItem(DEFAULT_INCOMING_DIRECTORY.toString())))
            .andExpect(jsonPath("$.[*].outgoingDirectory").value(hasItem(DEFAULT_OUTGOING_DIRECTORY.toString())))
            .andExpect(jsonPath("$.[*].errorDirectory").value(hasItem(DEFAULT_ERROR_DIRECTORY.toString())));
    }

    @Test
    @Transactional
    public void getSftproot() throws Exception {
        // Initialize the database
        sftprootRepository.saveAndFlush(sftproot);

        // Get the sftproot
        restSftprootMockMvc.perform(get("/api/sftproots/{id}", sftproot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sftproot.getId().intValue()))
            .andExpect(jsonPath("$.incomingDirectory").value(DEFAULT_INCOMING_DIRECTORY.toString()))
            .andExpect(jsonPath("$.outgoingDirectory").value(DEFAULT_OUTGOING_DIRECTORY.toString()))
            .andExpect(jsonPath("$.errorDirectory").value(DEFAULT_ERROR_DIRECTORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSftproot() throws Exception {
        // Get the sftproot
        restSftprootMockMvc.perform(get("/api/sftproots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSftproot() throws Exception {
        // Initialize the database
        sftprootRepository.saveAndFlush(sftproot);
        int databaseSizeBeforeUpdate = sftprootRepository.findAll().size();

        // Update the sftproot
        Sftproot updatedSftproot = sftprootRepository.findOne(sftproot.getId());
        // Disconnect from session so that the updates on updatedSftproot are not directly saved in db
        em.detach(updatedSftproot);
        updatedSftproot
            .incomingDirectory(UPDATED_INCOMING_DIRECTORY)
            .outgoingDirectory(UPDATED_OUTGOING_DIRECTORY)
            .errorDirectory(UPDATED_ERROR_DIRECTORY);
        SftprootDTO sftprootDTO = sftprootMapper.toDto(updatedSftproot);

        restSftprootMockMvc.perform(put("/api/sftproots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftprootDTO)))
            .andExpect(status().isOk());

        // Validate the Sftproot in the database
        List<Sftproot> sftprootList = sftprootRepository.findAll();
        assertThat(sftprootList).hasSize(databaseSizeBeforeUpdate);
        Sftproot testSftproot = sftprootList.get(sftprootList.size() - 1);
        assertThat(testSftproot.getIncomingDirectory()).isEqualTo(UPDATED_INCOMING_DIRECTORY);
        assertThat(testSftproot.getOutgoingDirectory()).isEqualTo(UPDATED_OUTGOING_DIRECTORY);
        assertThat(testSftproot.getErrorDirectory()).isEqualTo(UPDATED_ERROR_DIRECTORY);
    }

    @Test
    @Transactional
    public void updateNonExistingSftproot() throws Exception {
        int databaseSizeBeforeUpdate = sftprootRepository.findAll().size();

        // Create the Sftproot
        SftprootDTO sftprootDTO = sftprootMapper.toDto(sftproot);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSftprootMockMvc.perform(put("/api/sftproots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sftprootDTO)))
            .andExpect(status().isCreated());

        // Validate the Sftproot in the database
        List<Sftproot> sftprootList = sftprootRepository.findAll();
        assertThat(sftprootList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSftproot() throws Exception {
        // Initialize the database
        sftprootRepository.saveAndFlush(sftproot);
        int databaseSizeBeforeDelete = sftprootRepository.findAll().size();

        // Get the sftproot
        restSftprootMockMvc.perform(delete("/api/sftproots/{id}", sftproot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sftproot> sftprootList = sftprootRepository.findAll();
        assertThat(sftprootList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sftproot.class);
        Sftproot sftproot1 = new Sftproot();
        sftproot1.setId(1L);
        Sftproot sftproot2 = new Sftproot();
        sftproot2.setId(sftproot1.getId());
        assertThat(sftproot1).isEqualTo(sftproot2);
        sftproot2.setId(2L);
        assertThat(sftproot1).isNotEqualTo(sftproot2);
        sftproot1.setId(null);
        assertThat(sftproot1).isNotEqualTo(sftproot2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SftprootDTO.class);
        SftprootDTO sftprootDTO1 = new SftprootDTO();
        sftprootDTO1.setId(1L);
        SftprootDTO sftprootDTO2 = new SftprootDTO();
        assertThat(sftprootDTO1).isNotEqualTo(sftprootDTO2);
        sftprootDTO2.setId(sftprootDTO1.getId());
        assertThat(sftprootDTO1).isEqualTo(sftprootDTO2);
        sftprootDTO2.setId(2L);
        assertThat(sftprootDTO1).isNotEqualTo(sftprootDTO2);
        sftprootDTO1.setId(null);
        assertThat(sftprootDTO1).isNotEqualTo(sftprootDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sftprootMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sftprootMapper.fromId(null)).isNull();
    }
}
