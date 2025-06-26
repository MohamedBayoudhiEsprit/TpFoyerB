package tn.esprit.tpfoyer.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EtudiantRestController.class)
class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantService;

    @Autowired
    private ObjectMapper objectMapper;

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEtudiant("Doe");
        etudiant1.setPrenomEtudiant("John");
        etudiant1.setCinEtudiant(12345678L);
        etudiant1.setDateNaissance(new Date());

        etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2L);
        etudiant2.setNomEtudiant("Smith");
        etudiant2.setPrenomEtudiant("Jane");
        etudiant2.setCinEtudiant(87654321L);
        etudiant2.setDateNaissance(new Date());
    }

    @Test
    void testGetEtudiants_ShouldReturnListOfEtudiants() throws Exception {
        // Given
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);
        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiants);

        // When & Then
        mockMvc.perform(get("/etudiant/retrieve-all-etudiants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$[0].prenomEtudiant").value("John"))
                .andExpect(jsonPath("$[1].nomEtudiant").value("Smith"))
                .andExpect(jsonPath("$[1].prenomEtudiant").value("Jane"));
    }

    @Test
    void testRetrieveEtudiant_ShouldReturnEtudiant_WhenIdExists() throws Exception {
        // Given
        Long etudiantId = 1L;
        when(etudiantService.retrieveEtudiant(etudiantId)).thenReturn(etudiant1);

        // When & Then
        mockMvc.perform(get("/etudiant/retrieve-etudiant/{etudiant-id}", etudiantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1))
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.prenomEtudiant").value("John"))
                .andExpect(jsonPath("$.cinEtudiant").value(12345678));
    }

    @Test
    void testRetrieveEtudiantParCin_ShouldReturnEtudiant_WhenCinExists() throws Exception {
        // Given
        Long cin = 12345678L;
        when(etudiantService.recupererEtudiantParCin(cin)).thenReturn(etudiant1);

        // When & Then
        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/{cin}", cin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinEtudiant").value(12345678))
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.prenomEtudiant").value("John"));
    }

    @Test
    void testAddEtudiant_ShouldReturnCreatedEtudiant() throws Exception {
        // Given
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant1);

        // When & Then
        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.prenomEtudiant").value("John"))
                .andExpect(jsonPath("$.cinEtudiant").value(12345678));
    }

    @Test
    void testModifyEtudiant_ShouldReturnUpdatedEtudiant() throws Exception {
        // Given
        etudiant1.setNomEtudiant("UpdatedName");
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant1);

        // When & Then
        mockMvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("UpdatedName"))
                .andExpect(jsonPath("$.prenomEtudiant").value("John"))
                .andExpect(jsonPath("$.cinEtudiant").value(12345678));
    }

    @Test
    void testRemoveEtudiant_ShouldReturnOk() throws Exception {
        // Given
        Long etudiantId = 1L;

        // When & Then
        mockMvc.perform(delete("/etudiant/remove-etudiant/{etudiant-id}", etudiantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
