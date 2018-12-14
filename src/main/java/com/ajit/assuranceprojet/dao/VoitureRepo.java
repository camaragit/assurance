package com.ajit.assuranceprojet.dao;

import com.ajit.assuranceprojet.model.Chauffeur;
import com.ajit.assuranceprojet.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepo extends JpaRepository<Voiture,Long> {
    public List<Voiture> findByChauffeur(Chauffeur chauffeur);
}
