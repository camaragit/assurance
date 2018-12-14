package com.ajit.assuranceprojet.dao;

import com.ajit.assuranceprojet.model.Souscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SouscriptionRepo extends JpaRepository<Souscription,Long> {
}
