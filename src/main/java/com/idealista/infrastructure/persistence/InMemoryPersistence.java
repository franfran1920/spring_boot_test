package com.idealista.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class InMemoryPersistence {

    private List<AdVO> ads;
    private List<PictureVO> pictures;
	List<String> listKeyWords;


    public InMemoryPersistence() {
    	
        ads = new ArrayList<AdVO>();
        ads.add(new AdVO(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Arrays.asList(4), 300, null, null, null));
        ads.add(new AdVO(3, "CHALET", "", Arrays.asList(2), 300, null, null, null));
        ads.add(new AdVO(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", Arrays.asList(5), 300, null, null, null));
        ads.add(new AdVO(5, "FLAT", "Pisazo,", Arrays.asList(3, 8), 300, null, null, null));
        ads.add(new AdVO(6, "GARAGE", "", Arrays.asList(6), 300, null, null, null));
        ads.add(new AdVO(7, "	", "Garaje en el centro de Albacete", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList(1, 7), 300, null, null, null));

        pictures = new ArrayList<PictureVO>();
        pictures.add(new PictureVO(1, "http://www.idealista.com/pictures/1", "SD"));
        pictures.add(new PictureVO(2, "http://www.idealista.com/pictures/2", "HD"));
        pictures.add(new PictureVO(3, "http://www.idealista.com/pictures/3", "SD"));
        pictures.add(new PictureVO(4, "http://www.idealista.com/pictures/4", "HD"));
        pictures.add(new PictureVO(5, "http://www.idealista.com/pictures/5", "SD"));
        pictures.add(new PictureVO(6, "http://www.idealista.com/pictures/6", "SD"));
        pictures.add(new PictureVO(7, "http://www.idealista.com/pictures/7", "SD"));
        pictures.add(new PictureVO(8, "http://www.idealista.com/pictures/8", "HD"));
        
        listKeyWords = new ArrayList<String>();
    	listKeyWords.add("Luminoso");
    	listKeyWords.add("Nuevo");
    	listKeyWords.add("Céntrico");
    	listKeyWords.add("Reformado");
    	listKeyWords.add("Ático");
    }

    //TODO crea los métodos que necesites
    
    public List<AdVO> getAds(){
    	return ads;
    }
    
    public List<PictureVO> getPictures(){
    	return pictures;
    }
    
    public List<String> getKeyWords(){
    	return listKeyWords;
    }
    
    public AdVO getAd(int index) {
    	return ads.get(index);
    }
    
    public PictureVO getPicture(int index) {
    	return pictures.get(index);
    }
    
    public void setScore(int index, int score) {
    	ads.get(index).setScore(score);
    }
}
