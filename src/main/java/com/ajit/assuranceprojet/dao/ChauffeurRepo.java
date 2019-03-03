package com.ajit.assuranceprojet.dao;

import com.ajit.assuranceprojet.model.Chauffeur;
import com.ajit.assuranceprojet.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChauffeurRepo extends JpaRepository<Chauffeur,Long> {
    public List<Chauffeur> findByNfcid(String nfcId);
    public Chauffeur getChauffeurByNfcid(String nfcId);

}
