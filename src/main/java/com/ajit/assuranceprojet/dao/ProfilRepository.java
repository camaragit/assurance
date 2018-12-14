package com.ajit.assuranceprojet.dao;

import com.ajit.assuranceprojet.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilRepository extends JpaRepository<Profil,Long> {

}
