package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Captor
    private ArgumentCaptor<Etudiant> etudiantCaptor;

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
    void testRetrieveAllEtudiants_ShouldReturnListOfEtudiants() {
        // Given
        List<Etudiant> expectedEtudiants = Arrays.asList(etudiant1, etudiant2);
        when(etudiantRepository.findAll()).thenReturn(expectedEtudiants);

        // When
        List<Etudiant> actualEtudiants = etudiantService.retrieveAllEtudiants();

        // Then
        assertEquals(2, actualEtudiants.size());
        assertEquals(expectedEtudiants, actualEtudiants);
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEtudiant_ShouldReturnEtudiant_WhenIdExists() {
        // Given
        Long etudiantId = 1L;
        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant1));

        // When
        Etudiant actualEtudiant = etudiantService.retrieveEtudiant(etudiantId);

        // Then
        assertNotNull(actualEtudiant);
        assertEquals(etudiant1.getIdEtudiant(), actualEtudiant.getIdEtudiant());
        assertEquals(etudiant1.getNomEtudiant(), actualEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }

    @Test
    void testAddEtudiant_ShouldReturnSavedEtudiant() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        // When
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant1);

        // Then
        assertNotNull(savedEtudiant);
        assertEquals(etudiant1.getNomEtudiant(), savedEtudiant.getNomEtudiant());
        assertEquals(etudiant1.getPrenomEtudiant(), savedEtudiant.getPrenomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant1);
    }

    @Test
    void testModifyEtudiant_ShouldReturnUpdatedEtudiant() {
        // Given
        etudiant1.setNomEtudiant("UpdatedName");
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        // When
        Etudiant updatedEtudiant = etudiantService.modifyEtudiant(etudiant1);

        // Then
        assertNotNull(updatedEtudiant);
        assertEquals("UpdatedName", updatedEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant1);
    }

    @Test
    void testRemoveEtudiant_ShouldCallDeleteById() {
        // Given
        Long etudiantId = 1L;
        doNothing().when(etudiantRepository).deleteById(etudiantId);

        // When
        etudiantService.removeEtudiant(etudiantId);

        // Then
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }

    @Test
    void testRecupererEtudiantParCin_ShouldReturnEtudiant_WhenCinExists() {
        // Given
        long cin = 12345678L;
        when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etudiant1);

        // When
        Etudiant actualEtudiant = etudiantService.recupererEtudiantParCin(cin);

        // Then
        assertNotNull(actualEtudiant);
        assertEquals(cin, actualEtudiant.getCinEtudiant());
        assertEquals(etudiant1.getNomEtudiant(), actualEtudiant.getNomEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
    }

    @Test
    void testRecupererEtudiantParCin_ShouldReturnNull_WhenCinDoesNotExist() {
        // Given
        long cin = 99999999L;
        when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(null);

        // When
        Etudiant actualEtudiant = etudiantService.recupererEtudiantParCin(cin);

        // Then
        assertNull(actualEtudiant);
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
    }

    @Test
    void testAddEtudiant_WithArgumentCaptor_ShouldCaptureCorrectArgument() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant1);

        // When
        etudiantService.addEtudiant(etudiant1);

        // Then
        verify(etudiantRepository).save(etudiantCaptor.capture());
        Etudiant capturedEtudiant = etudiantCaptor.getValue();
        
        assertEquals("Doe", capturedEtudiant.getNomEtudiant());
        assertEquals("John", capturedEtudiant.getPrenomEtudiant());
        assertEquals(12345678L, capturedEtudiant.getCinEtudiant());
    }

    @Test
    void testAddEtudiant_WithArgumentMatchers_ShouldUseCustomMatchers() {
        // Given
        when(etudiantRepository.save(argThat(etudiant -> 
            etudiant.getNomEtudiant().equals("Doe") && 
            etudiant.getCinEtudiant() == 12345678L
        ))).thenReturn(etudiant1);

        // When
        Etudiant result = etudiantService.addEtudiant(etudiant1);

        // Then
        assertNotNull(result);
        assertEquals("Doe", result.getNomEtudiant());
        
        verify(etudiantRepository).save(argThat(etudiant -> 
            etudiant.getNomEtudiant().equals("Doe")
        ));
    }
}
