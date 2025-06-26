package tn.esprit.tpfoyer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tn.esprit.tpfoyer.entity.Etudiant;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EtudiantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EtudiantRepository etudiantRepository;

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        etudiant1 = new Etudiant();
        etudiant1.setNomEtudiant("Doe");
        etudiant1.setPrenomEtudiant("John");
        etudiant1.setCinEtudiant(12345678L);
        etudiant1.setDateNaissance(new Date());

        etudiant2 = new Etudiant();
        etudiant2.setNomEtudiant("Smith");
        etudiant2.setPrenomEtudiant("Jane");
        etudiant2.setCinEtudiant(87654321L);
        etudiant2.setDateNaissance(new Date());
    }

    @Test
    void testFindEtudiantByCinEtudiant_ShouldReturnEtudiant_WhenCinExists() {
        // Given
        entityManager.persistAndFlush(etudiant1);

        // When
        Etudiant foundEtudiant = etudiantRepository.findEtudiantByCinEtudiant(12345678L);

        // Then
        assertNotNull(foundEtudiant);
        assertEquals("Doe", foundEtudiant.getNomEtudiant());
        assertEquals("John", foundEtudiant.getPrenomEtudiant());
        assertEquals(12345678L, foundEtudiant.getCinEtudiant());
    }

    @Test
    void testFindEtudiantByCinEtudiant_ShouldReturnNull_WhenCinDoesNotExist() {
        // Given - no data persisted

        // When
        Etudiant foundEtudiant = etudiantRepository.findEtudiantByCinEtudiant(99999999L);

        // Then
        assertNull(foundEtudiant);
    }

    @Test
    void testSaveEtudiant_ShouldPersistEtudiant() {
        // When
        Etudiant savedEtudiant = etudiantRepository.save(etudiant1);

        // Then
        assertNotNull(savedEtudiant.getIdEtudiant());
        assertEquals("Doe", savedEtudiant.getNomEtudiant());
        assertEquals("John", savedEtudiant.getPrenomEtudiant());
        assertEquals(12345678L, savedEtudiant.getCinEtudiant());
    }

    @Test
    void testFindById_ShouldReturnEtudiant_WhenIdExists() {
        // Given
        Etudiant persistedEtudiant = entityManager.persistAndFlush(etudiant1);

        // When
        Optional<Etudiant> foundEtudiant = etudiantRepository.findById(persistedEtudiant.getIdEtudiant());

        // Then
        assertTrue(foundEtudiant.isPresent());
        assertEquals("Doe", foundEtudiant.get().getNomEtudiant());
        assertEquals("John", foundEtudiant.get().getPrenomEtudiant());
    }

    @Test
    void testFindAll_ShouldReturnAllEtudiants() {
        // Given
        entityManager.persistAndFlush(etudiant1);
        entityManager.persistAndFlush(etudiant2);

        // When
        List<Etudiant> allEtudiants = etudiantRepository.findAll();

        // Then
        assertEquals(2, allEtudiants.size());
        assertTrue(allEtudiants.stream().anyMatch(e -> e.getNomEtudiant().equals("Doe")));
        assertTrue(allEtudiants.stream().anyMatch(e -> e.getNomEtudiant().equals("Smith")));
    }

    @Test
    void testDeleteById_ShouldRemoveEtudiant() {
        // Given
        Etudiant persistedEtudiant = entityManager.persistAndFlush(etudiant1);
        Long etudiantId = persistedEtudiant.getIdEtudiant();

        // When
        etudiantRepository.deleteById(etudiantId);

        // Then
        Optional<Etudiant> deletedEtudiant = etudiantRepository.findById(etudiantId);
        assertFalse(deletedEtudiant.isPresent());
    }
}
