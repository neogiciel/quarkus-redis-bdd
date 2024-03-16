package com.neogiciel;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;

import com.neogiciel.redis.RedisCLI;
import com.neogiciel.redis.RedisManager;
//import com.neogiciel.service.BddManager;
import com.neogiciel.util.Trace;


@Path("/")
public class ApiController {


    //RedisManager
    @Inject
    RedisManager redisBdd; 
    

    /*
     * test
     */
    //@CacheResult(cacheName = "test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/api/test")
    public String test() {
        Trace.info("Applel REST : /api/test");        
    
        String nom = redisBdd.getData("NOM");

        return getJSON("test", nom).toString();

     }

     /*
     * liste
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/api/liste")
    public String liste() {
        Trace.info("Applel REST : /api/liste");        
        redisBdd.delListe("listetest");

        List<String> liste = new ArrayList();
        liste.add("test1");
        liste.add("test2");
        liste.add("test3");
        liste.add("test4");
        
        redisBdd.setListe("listetest", liste);

        int nb = redisBdd.getSizeListe("listetest");
        Trace.info("Taille de la liste ="+nb);        

        String element = redisBdd.getElementListe("listetest",0);
        Trace.info("Element ="+element);        

        redisBdd.delElementFromListe("listetest", 0);


        return getJSON("test","liste").toString();

     }

     /*
     * liste
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/api/map")
    public String map() {
        Trace.info("Applel REST : /api/map");        
                
        redisBdd.addElementMap("user",0,"nom","radin");
        redisBdd.addElementMap("user",0,"prenom","radin");
        redisBdd.addElementMap("user",0,"age","20");
        redisBdd.addElementMap("user",1,"nom","du bourg");
        redisBdd.addElementMap("user",1,"prenom","nathalie");
        redisBdd.addElementMap("user",1,"age","20");

        return getJSON("test","map").toString();

     }



     /*
      * getJSON
      */
     public JSONObject getJSON(String value,String key){
        JSONObject obj = new JSONObject();
        obj.put(value, key);
        return obj;
     }



}
