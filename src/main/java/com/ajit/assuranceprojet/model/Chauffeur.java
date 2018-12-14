package com.ajit.assuranceprojet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Chauffeur implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long IdChauffeur;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String nfcid;
    private String telephone;
    private Boolean proprietaire;
    @JsonIgnore
    @OneToMany(mappedBy = "chauffeur")
    private Collection<Voiture> voitures;
}
