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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MinimalEtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("TestName");
        etudiant.setPrenomEtudiant("TestPrenom");
        etudiant.setCinEtudiant(12345678L);
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Given
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant));

        // When
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(etudiantRepository).findAll();
    }

    @Test
    void testAddEtudiant() {
        // Given
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // When
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Then
        assertNotNull(result);
        assertEquals("TestName", result.getNomEtudiant());
        verify(etudiantRepository).save(etudiant);
    }

    @Test
    void testRemoveEtudiant() {
        // Given
        Long id = 1L;

        // When
        etudiantService.removeEtudiant(id);

        // Then
        verify(etudiantRepository).deleteById(id);
    }
}
