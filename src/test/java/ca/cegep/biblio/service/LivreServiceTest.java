package ca.cegep.biblio.service;

import ca.cegep.biblio.model.Livre;
import ca.cegep.biblio.repository.LivreRepository;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * TP partie C : à vous d'implémenter les 3 tests ci-dessous.
 * Retirez @Disabled de chaque test une fois son implémentation écrite.
 * Respectez le modèle Arrange / Act / Assert.
 */
@ExtendWith(MockitoExtension.class)
class LivreServiceTest {

    @Mock
    private LivreRepository repository;

    @InjectMocks
    private LivreService service;

    // ---------- 1 test simple (sans mock) ----------

    @Test
    /*@Disabled("À implémenter")*/
    void calculerFraisRetard_longRetard_plafonneLeMontant() {
        // À tester : pour une catégorie donnée, un très grand nombre de jours de retard
        // ne dépasse jamais le plafond de cette catégorie.
        // Indice : la méthode ne dépend pas du repository.
        int joursDeRetard = 100;
        String categorie = "SCIENCE";
        
        double frais = service.calculerFraisRetard(categorie, joursDeRetard);

        assertEquals(20.0, frais);
        
    }

    // ---------- 1 test sur une exception (assertThrows) ----------

    @Test
    /*@Disabled("À implémenter")*/
    void emprunter_livreDejaEmprunte_lanceIllegalStateException() {
        // À tester : emprunter un livre dont disponible == false lance une IllegalStateException.
        // Indice : le repository (mock) doit retourner ce livre pour son id.
        Long livreId = 1L;
        Livre livreEmprunte = new Livre(livreId, "Dune", "Frank Herbert", "9782266320481", 1965, "SCIENCE");
        livreEmprunte.setDisponible(false);

        when(repository.findById(livreId)).thenReturn(Optional.of(livreEmprunte));

        assertThrows(IllegalStateException.class, () -> {
            service.emprunter(livreId);
        });
        

    }

    // ---------- 1 test avec mock (when / thenReturn / verify) ----------

    @Test
    /*@Disabled("À implémenter")*/
    void emprunter_livreDisponible_marqueIndisponibleEtSauvegarde() {
        // À tester : emprunter un livre disponible le rend indisponible
        // et appelle repository.save(...) exactement une fois.
        // Définissez le comportement du mock avec when(...).thenReturn(...).

        // Arrange 
        Long livreId = 2L;
        Livre livreDisponible = new Livre(livreId, "Foundation", "Isaac Asimov", "9782070360533", 1951, "SCIENCE");
        livreDisponible.setDisponible(true);

        when(repository.findById(livreId)).thenReturn(Optional.of(livreDisponible));
        when(repository.save(any(Livre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act 
        service.emprunter(livreId);

        // Assert 
        assertFalse(livreDisponible.isDisponible());
        verify(repository, times(1)).save(livreDisponible);
    }
}


