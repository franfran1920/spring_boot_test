package com.idealista.infrastructure.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;

@RestController
public class AdsController {

	InMemoryPersistence persistence = new InMemoryPersistence();
	
	List<QualityAd> qualityAd;
	
    @RequestMapping("/qualityList")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        //TODO rellena el cuerpo del método
    	List<AdVO> persistenceAds = new ArrayList();
    	persistenceAds = persistence.getAds();
    	qualityAd = new ArrayList<QualityAd>();
    	
    	
    	for(int i=0; i<persistenceAds.size(); i++) {
    		QualityAd newAd = new QualityAd();
    		newAd.setId(i);
    		newAd.setTypology(persistenceAds.get(i).getTypology());
    		newAd.setDescription(persistenceAds.get(i).getDescription());
    		
    		List<String> urls = new ArrayList();
    		for(int j=0; j<persistenceAds.get(i).getPictures().size(); j++) {
    			urls.add(persistence.getPictures().get(persistenceAds.get(i).getPictures().get(j)-1).getUrl());
    		}
    		newAd.setPictureUrls(urls);
    		newAd.setHouseSize(persistenceAds.get(i).getHouseSize());
    		newAd.setGardenSize(persistenceAds.get(i).getGardenSize());
    		newAd.setScore(persistenceAds.get(i).getScore());
    		newAd.setIrrelevantSince(persistenceAds.get(i).getIrrelevantSince());
    		
    		qualityAd.add(newAd);
    	}
        
        return new ResponseEntity<List<QualityAd>>(qualityAd, HttpStatus.OK);
    }
    

    @RequestMapping("/publicList")
    public ResponseEntity<List<PublicAd>> publicListing() {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build();
    }

    @RequestMapping("/score")
    public ResponseEntity<List<String>> calculateScore() {
        //TODO rellena el cuerpo del método
        
    	List<Integer> scores = new ArrayList<Integer>();
    	List<String> resultados = new ArrayList<String>();
    	
    	for(int i=0; i<persistence.getAds().size(); i++) {
    		int score = 0;
    		
    		AdVO ad = persistence.getAd(i);
    		PictureVO picture = persistence.getPicture(i);
    		
    		//Si el anuncio tiene fotos
    		if(ad.getPictures().size() > 0) {
    			List<Integer> pictureInteger = ad.getPictures();
    			for(int j = 0; j<pictureInteger.size(); j++) {
    				Integer pictureIndex = pictureInteger.get(j);
    				
    				if(persistence.getPictures().get(pictureIndex-1).getQuality() == "HD") {
    					score += 20;
    				}else {
    					score += 10;
    				}
    			}
    		}else {
    			score -= 10;
    		}
    		
    		//si el anuncio tiene texto descriptivo
    		String descripcion = ad.getDescription().toLowerCase();
    		List<String> words_descripcion = Arrays.asList(descripcion.split("\\s+"));
    		
    		if(words_descripcion.size() > 0) {
    			score += 5;
    			
    			//caso para añadir puntos segun la longitud de la descripcion
    			if(ad.getTypology() == "FLAT") {
    				if(words_descripcion.size() >= 50) {
    					score += 30;
    				}else {
    					if(words_descripcion.size() > 20){
    						score += 10;
    					}
    				}
    			}else {
    				if(ad.getTypology() == "CHALET" && words_descripcion.size() > 50) {
    					score += 20;
    				}
    			}
    			
    			//coincidencia de palabras en la descripcion
				List<String> keyWords = persistence.getKeyWords();
				for(int j=0; j<keyWords.size(); j++) {
					if(words_descripcion.contains(keyWords.get(j).toLowerCase())) {
						score += 5;
					}
				}
    		}
    		
    		
    		//comprobar si un anuncio es completo
    		if(ad.getPictures().size() > 0) {
    			if(ad.getDescription().length() > 0) {
        			//caso de pisos
        			if(ad.getTypology() == "FLAT" && ad.getHouseSize() != null) {
        				score += 40;
        			}
        			
        			//caso de pisos
        			if(ad.getTypology() == "CHALET" && ad.getHouseSize() != null && ad.getGardenSize() != null) {
        				score += 40;
        			}
    			}else {
    				//caso de los garajes
    				if(ad.getTypology() == "GARAGE") {
    					score += 40;
    				}
    			}
    		}
    		
    		
    		
    		if(score < 0) score = 0;
    		if(score > 100) score = 100;
    		
    		persistence.setScore(i, score);
    		
    		scores.add(score);
    		resultados.add("Anuncio "+ i + ", puntuacion: " + score);
    		System.out.print("Anuncio "+ i + ", puntuacion: " + score + "\n");
    	}
    	
    	
    	return new ResponseEntity<List<String>>(resultados, HttpStatus.OK);
    }
}
