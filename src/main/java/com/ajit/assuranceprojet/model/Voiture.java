package com.ajit.assuranceprojet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Voiture implements Serializable {
    @Id
    private String matricule;
    private String marque;
    private String model;
    @Enumerated(EnumType.STRING)
    private Usage usage;
    private Long puissance;
    private String typeCarburant;
    Long nombrePlaces;
    @ManyToOne
    private Chauffeur chauffeur;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "voiture")
    private Collection<Souscription> souscriptions;

}
