package com.ajit.assuranceprojet.Dto;

import com.ajit.assuranceprojet.model.Voiture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SouscriptionVoiture implements Serializable{
    private Voiture voiture;

}
