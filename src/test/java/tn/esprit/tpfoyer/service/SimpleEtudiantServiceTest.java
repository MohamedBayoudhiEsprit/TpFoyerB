package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleEtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant1;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEtudiant("Doe");
        etudiant1.setPrenomEtudiant("John");
        etudiant1.setCinEtudiant(12345678L);
        etudiant1.setDateNaissance(new Date());
    }

    @Test
    void testRetrieveAllEtudiants_ShouldWork() {
        // Given
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant1));

        // When
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(etudiantRepository).findAll();
    }

    @Test
    void testAddEtudiant_ShouldWork() {
        // Given
        when(etudiantRepository.save(etudiant1)).thenReturn(etudiant1);

        // When
        Etudiant result = etudiantService.addEtudiant(etudiant1);

        // Then
        assertNotNull(result);
        assertEquals("Doe", result.getNomEtudiant());
        verify(etudiantRepository).save(etudiant1);
    }
}
