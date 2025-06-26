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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Advanced Mockito Testing Examples
 * This class demonstrates various Mockito features and best practices
 */
@ExtendWith(MockitoExtension.class)
class AdvancedMockitoExamplesTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Captor
    private ArgumentCaptor<Etudiant> etudiantCaptor;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("TestStudent");
        etudiant.setPrenomEtudiant("Test");
        etudiant.setCinEtudiant(12345678L);
        etudiant.setDateNaissance(new Date());
    }

    @Test
    void testMockAnnotation_ShouldCreateMockObject() {
        // @Mock creates a mock object
        assertNotNull(etudiantRepository);
        
        // Mock behavior
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        
        // Test
        Optional<Etudiant> result = etudiantRepository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("TestStudent", result.get().getNomEtudiant());
    }

    @Test
    void testCaptorAnnotation_ShouldCaptureArguments() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // When
        etudiantService.addEtudiant(etudiant);

        // Then - @Captor captures method arguments
        verify(etudiantRepository).save(etudiantCaptor.capture());
        Etudiant captured = etudiantCaptor.getValue();
        
        assertEquals("TestStudent", captured.getNomEtudiant());
        assertEquals(12345678L, captured.getCinEtudiant());
    }

    @Test
    void testBasicStubbing_ShouldReturnConfiguredValue() {
        // Basic stubbing with when().thenReturn()
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Optional<Etudiant> result = etudiantRepository.findById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("TestStudent", result.get().getNomEtudiant());
    }

    @Test
    void testMultipleStubbing_ShouldHandleSequentialCalls() {
        // Multiple return values for sequential calls
        when(etudiantRepository.count())
            .thenReturn(1L)
            .thenReturn(2L)
            .thenReturn(3L);

        assertEquals(1L, etudiantRepository.count());
        assertEquals(2L, etudiantRepository.count());
        assertEquals(3L, etudiantRepository.count());
    }

    @Test
    void testExceptionStubbing_ShouldThrowException() {
        // Stubbing to throw exceptions
        when(etudiantRepository.findById(999L))
            .thenThrow(new RuntimeException("Student not found"));

        assertThrows(RuntimeException.class, () -> {
            etudiantRepository.findById(999L);
        });
    }

    @Test
    void testAnswerStubbing_ShouldExecuteCustomLogic() {
        // Using Answer for complex behavior
        when(etudiantRepository.save(any(Etudiant.class))).thenAnswer(invocation -> {
            Etudiant arg = invocation.getArgument(0);
            arg.setIdEtudiant(100L); // Simulate database ID assignment
            return arg;
        });

        Etudiant saved = etudiantRepository.save(etudiant);
        assertEquals(100L, saved.getIdEtudiant());
    }

    @Test
    void testArgumentMatchers_ShouldMatchDifferentTypes() {
        // any() matcher
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        
        // anyLong() matcher
        when(etudiantRepository.findById(anyLong())).thenReturn(Optional.of(etudiant));
        
        // eq() matcher for specific values
        when(etudiantRepository.findEtudiantByCinEtudiant(eq(12345678L))).thenReturn(etudiant);

        // Test all matchers
        assertNotNull(etudiantRepository.save(etudiant));
        assertTrue(etudiantRepository.findById(999L).isPresent());
        assertNotNull(etudiantRepository.findEtudiantByCinEtudiant(12345678L));
    }

    @Test
    void testCustomArgumentMatcher_ShouldUseCustomLogic() {
        // Custom argument matcher with argThat()
        when(etudiantRepository.save(argThat(student -> 
            student.getNomEtudiant().startsWith("Test") && 
            student.getCinEtudiant() > 10000000L
        ))).thenReturn(etudiant);

        Etudiant result = etudiantRepository.save(etudiant);
        assertNotNull(result);
        
        verify(etudiantRepository).save(argThat(student -> 
            student.getNomEtudiant().contains("Test")
        ));
    }

    @Test
    void testBasicVerification_ShouldVerifyMethodCalls() {
        // Given
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant));

        // When
        etudiantService.retrieveAllEtudiants();

        // Then - Basic verification
        verify(etudiantRepository).findAll();
        verify(etudiantRepository, times(1)).findAll();
        verify(etudiantRepository, atLeastOnce()).findAll();
        verify(etudiantRepository, never()).deleteAll();
    }

    @Test
    void testVerificationWithTimeout_ShouldVerifyWithinTimeLimit() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // When
        etudiantService.addEtudiant(etudiant);

        // Then - Verification with timeout
        verify(etudiantRepository, timeout(1000)).save(any(Etudiant.class));
        verify(etudiantRepository, timeout(500).times(1)).save(etudiant);
    }

    @Test
    void testVerifyNoInteractions_ShouldEnsureNoMethodsCalled() {
        // When - No service methods called

        // Then - Verify no interactions
        verifyNoInteractions(etudiantRepository);
    }

    @Test
    void testVerifyNoMoreInteractions_ShouldEnsureNoExtraCalls() {
        // Given
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // When
        etudiantRepository.findById(1L);

        // Then
        verify(etudiantRepository).findById(1L);
        verifyNoMoreInteractions(etudiantRepository);
    }

    @Test
    void testExceptionHandling_ShouldHandleRepositoryExceptions() {
        // Given
        when(etudiantRepository.save(any(Etudiant.class)))
            .thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            etudiantService.addEtudiant(etudiant);
        });

        verify(etudiantRepository).save(etudiant);
    }
}
