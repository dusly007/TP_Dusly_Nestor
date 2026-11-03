package ca.cegep.biblio.service;

import ca.cegep.biblio.exception.LivreIntrouvableException;
import ca.cegep.biblio.model.Livre;
import ca.cegep.biblio.repository.LivreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Logique métier de la bibliothèque. */
@Service
public class LivreService {

    private final LivreRepository repository;

    public LivreService(LivreRepository repository) {
        this.repository = repository;
    }

    public List<Livre> lister() {
        return repository.findAll();
    }

    public Livre trouver(Long id) {
        return repository.findById(id).orElseThrow(() -> new LivreIntrouvableException(id));
    }

    public Livre creer(String titre, String auteur, String isbn, int anneePublication, String categorie) {
        validerLivre(titre, auteur, anneePublication);
        Livre livre = new Livre(null, titre, auteur, isbn, anneePublication, categorie);
        return repository.save(livre);
    }

    public Livre modifier(Long id, String titre, String auteur, String isbn, int anneePublication, String categorie) {
        Livre livre = trouver(id);
        validerLivre(titre, auteur, anneePublication);
        
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setIsbn(isbn);
        livre.setAnneePublication(anneePublication);
        livre.setCategorie(categorie);
        return repository.save(livre);
    }

    public void supprimer(Long id) {
        trouver(id);
        repository.deleteById(id);
    }

    public Livre emprunter(Long id) {
        Livre livre = trouver(id);
        if (!livre.isDisponible()) {
            throw new IllegalStateException("Ce livre est déjà emprunté");
        }
        livre.setDisponible(false);
        return repository.save(livre);
    }

    public Livre retourner(Long id) {
        Livre livre = trouver(id);
        if (livre.isDisponible()) {
            throw new IllegalStateException("Ce livre n'est pas emprunté");
        }
        livre.setDisponible(true);
        return repository.save(livre);
    }

    public double calculerFraisRetard(String categorie, int joursRetard) {
        if (joursRetard <= 0) {
            return 0.0;
        }

        double taux = 0.25;
        double plafond = 10.0;

        if ("JEUNESSE".equals(categorie)) {
            taux = 0.10;
            plafond = 5.0;
        } else if ("SCIENCE".equals(categorie)) {
            taux = 0.50;
            plafond = 20.0;
        }

        return Math.min(joursRetard * taux, plafond);
    }

    private void validerLivre(String titre, String auteur, int anneePublication) {
        if (titre == null || titre.isBlank()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }
        if (auteur == null || auteur.isBlank()) {
            throw new IllegalArgumentException("L'auteur est obligatoire");
        }
        if (anneePublication < 1450 || anneePublication > 2100) {
            throw new IllegalArgumentException("L'année de publication est invalide");
        }
    }
}