package com.ajit.assuranceprojet.controllers;

import com.ajit.assuranceprojet.dao.ChauffeurRepo;
import com.ajit.assuranceprojet.dao.SouscriptionRepo;
import com.ajit.assuranceprojet.dao.VoitureRepo;
import com.ajit.assuranceprojet.model.*;
import com.ajit.assuranceprojet.reponse.ApiSucessReponse;
import com.ajit.assuranceprojet.reponse.CustomException;
import com.ajit.assuranceprojet.service.UserService;
import com.ajit.assuranceprojet.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ChauffeurController {
    @Autowired
    private ChauffeurRepo chauffeurRepo;
    @Autowired
    private VoitureRepo voitureRepo;
    @Autowired
    private SouscriptionRepo souscriptionRepo;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/savevoiture")
    public ResponseEntity<ApiSucessReponse> saveChauffeur(@RequestBody Voiture value) throws CustomException {
      /*  try {*/

/*            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(value);
            System.out.println(value);
            JsonNode jsonVoiture = actualObj.get("voiture");
            Voiture voiture = new Voiture();
            Long puissance = jsonVoiture.get("puissance").asLong();
            Long nombreplace = jsonVoiture.get("nombrePlaces").asLong();
            if(nombreplace==0 || puissance==0)
                throw new CustomException("Revoir la valeur de la puissance ou celle du nombre de place !");

            voiture.setMarque(jsonVoiture.get("usage").asText());
            voiture.setMatricule(jsonVoiture.get("matricule").asText());
            voiture.setModel(jsonVoiture.get("model").asText());
            voiture.setNombrePlaces(nombreplace);
            voiture.setPuissance(puissance);
            voiture.setTypeCarburant(jsonVoiture.get("typeCarburant").asText());
            voiture.setUsage(Usage.valueOf(jsonVoiture.get("usage").asText()));
            JsonNode jsonChauffeur = actualObj.get("chauffeur");
            Chauffeur chauffeur = new Chauffeur();
            chauffeur.setNfcid(jsonChauffeur.get("nfcid").asText());
            chauffeur.setNom(jsonChauffeur.get("nom").asText());
            chauffeur.setPrenom(jsonChauffeur.get("prenom").asText());
            chauffeur.setTelephone(jsonChauffeur.get("telephone").asText());
            chauffeur.setProprietaire(jsonChauffeur.get("proprietaire").asBoolean());
            if(chauffeurRepo.save(chauffeur)==null) throw new CustomException("Impossible de sauvegarder les infos du chauffeur !");
            voiture.setChauffeur(chauffeur);
            if(voitureRepo.save(voiture)==null) throw new CustomException("Impossible de sauvegarder les infos de la voiture !");*/
            if(value.getPuissance()==0 || value.getNombrePlaces()==0)
                throw new CustomException("Revoir la valeur de la puissance ou celle du nombre de place !");
            Map<String,Object> map = new HashMap<>();
            chauffeurRepo.save(value.getChauffeur());
            Voiture voiture = voitureRepo.save(value);
            map.put("voiture",voiture);
            ApiSucessReponse reponse = new ApiSucessReponse("Informations sauvegardées avec succès",map);
            return new ResponseEntity<ApiSucessReponse>(reponse, HttpStatus.OK);
            }
    @GetMapping(value = "voitures")
    public ResponseEntity<ApiSucessReponse> getvoitures() throws CustomException {
        Map<String,Object> map = new HashMap<>();
        List<Voiture> voitures = voitureRepo.findAll();
        if(voitures.size()<=0)
            throw new CustomException("Aucune voiture n'a été enrollée !");

        map.put("Voiture",voitures);
        ApiSucessReponse reponse = new ApiSucessReponse("Liste des voitures",map);

        return new ResponseEntity<ApiSucessReponse>(reponse,HttpStatus.OK);
    }
    @GetMapping(value = "voiture/{nfcid}")
    public ResponseEntity<ApiSucessReponse> getVoitureByNFCID(@PathVariable("nfcid") String nfcid) throws CustomException {
        System.out.println("NFC ID ======>"+nfcid);
        List<Chauffeur> chauffeurs = chauffeurRepo.findByNfcid(nfcid);

        if(chauffeurs.size()<=0) throw new CustomException("Désolé cette carte n'est enrolllée sous aucune voiture");

        List<Voiture> voitures = voitureRepo.findByChauffeur(chauffeurs.get(0));

        if(voitures.size()<=0) throw new CustomException("Désolé cette carte n'est enrolllée sous aucune voiture");
        Map<String,Object> map = new HashMap<>();
        map.put("Voiture",voitures.get(0));
        ApiSucessReponse reponse = new ApiSucessReponse("Voiture par nfcid",map);
        return new ResponseEntity<ApiSucessReponse>(reponse,HttpStatus.OK);

    }
    @PostMapping(value = "souscription")
    public ResponseEntity<ApiSucessReponse> savesouscription(@RequestBody String requete) throws CustomException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode actualObj = mapper.readTree(requete);
            String nfcid = actualObj.get("nfcid").asText();
            System.out.println("NFC ID ======>"+nfcid);
            List<Chauffeur> chauffeurs = chauffeurRepo.findByNfcid(nfcid);

            if(chauffeurs.size()<=0) throw new CustomException("Désolé cette carte n'est enrolllée sous aucune voiture");

            List<Voiture> voitures = voitureRepo.findByChauffeur(chauffeurs.get(0));

            if(voitures.size()<=0) throw new CustomException("Désolé cette carte n'est enrolllée sous aucune voiture");

            Voiture voiture = voitures.get(0);

            Souscription souscription = new Souscription();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            try {
                souscription.setDateDebutValidite(dateFormat.parse(actualObj.get("datedebut").asText()));
            } catch (ParseException e) {
                throw new CustomException("Le format de la date de debut est incorrect mettre au format (jj-mm-aaaa)");
            }
            try {
                souscription.setDateFinValidite(dateFormat.parse(actualObj.get("datefin").asText()));
            } catch (ParseException e) {
                throw new CustomException("Le format de la date de fin est incorrect mettre au format (jj-mm-aaaa)");
            }
            if(souscription.getDateFinValidite().before(souscription.getDateDebutValidite()) || souscription.getDateFinValidite().compareTo(souscription.getDateDebutValidite())==0)
                throw new CustomException("La date de debut doit être supérieure à la date de fin");

            souscription.setPrixSouscription(actualObj.get("prix").asLong());
            souscription.setVoiture(voiture);
            User user = userService.getUserByEmail(JwtTokenUtil.EMAIL);
            souscription.setUser(user);
            Souscription souscrpt = souscriptionRepo.save(souscription);
            if(souscrpt==null) throw new CustomException("Impsossible d'enregistrée la souscription");
            Map<String,Object> map = new HashMap<>();
            map.put("Souscription",souscription);

            ApiSucessReponse reponse = new ApiSucessReponse("Souscription effectuée avec succès",map);
            return new ResponseEntity<ApiSucessReponse>(reponse,HttpStatus.OK);
        } catch (IOException e) {

            throw new CustomException("Revoir les données du parametre");
        }
    }
}
