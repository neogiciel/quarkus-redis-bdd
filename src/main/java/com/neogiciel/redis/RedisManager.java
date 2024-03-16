package com.neogiciel.redis;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.neogiciel.util.Trace;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class RedisManager{

    
    @Inject
    private RedisCLI redisCLI;
    
          
    /*
     * Constructeur
     */
    public RedisManager(RedisCLI redisCLI) {
        this.redisCLI = redisCLI;
        //cmd = redisDS.string(String.class);
    }

           
    /*
     * String getData(String key)
     */
    public String getData(String key) {
        return redisCLI.getData(key);
    }

    /*
     * void setData(String key,String value)
     */
    public void setData(String key,String value) {
        redisCLI.setData(key,value);
    }

    /*
     * void setListe(String nom,Liste liste)
     */
    public void setListe(String nom,List liste){
        int nb = Integer.parseInt(redisCLI.lLen(nom));
        Trace.info("[RedisManager] nb = "+nb);        
        if(nb==0){
            for (int i=0; i<liste.size(); ++i){
             redisCLI.rPush(nom,(String)liste.get(i));
            }
        }
    }
    /*
     * void getSizeListe(String nom)
     */
    public int getSizeListe(String nom){
        return Integer.parseInt(redisCLI.lLen(nom));
    }

    /*
     * void delListe(String nom)
     */
    public void delListe(String nom){
        redisCLI.delData(nom);
    }

    /*
     * delElementFromListe(String nom,int index)
     */
    public void delElementFromListe(String nom,int index){
        redisCLI.lRem(nom, index);
    }


    /*
     * void getElementListe(String nom)
     */
    public String getElementListe(String nom, int index){
        return redisCLI.lIndex(nom, index);
    }


    /*
     * void setListe(String nom,Liste liste)
     */
    /*public void createMap(String map){
        //redisCLI.hSet(nom, "0" , "nb", "0"); 
        redisCLI.hSet(map, 0 , "nb", "0"); 
    }*/

    /*
     * void addElementMap(String map, int index, String key, String value){
     */
    public void delElementMap(String map,int index){
        //int nb = Integer.parseInt(redisCLI.hGet(map,0, "nb");
        redisCLI.hDel(map,index) ;
    }


    /*
     * void addElementMap(String map, int index, String key, String value){
     */
    public void addElementMap(String map, int index, String key, String value){
        redisCLI.hSet(map, index, key, value); 
    }

    /*
     * void modifyElementMap(String map,int index, String key, String value){
     */
    public void modifyElementMap(String map,int index, String key, String value){
        redisCLI.hSet(map, index, key, value); 
    }



}

