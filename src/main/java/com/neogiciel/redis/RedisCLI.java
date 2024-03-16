package com.neogiciel.redis;

import io.quarkus.redis.datasource.RedisDataSource;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.neogiciel.util.Trace;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public interface RedisCLI{

    /*
     * Constructeur
     */
    //public RedisCLI(RedisDataSource redisDS);

    /*
     * Key Value
     */
    public void setData(String key, String value); 
    public void delData(String key);
    public String getData(String key);
    public boolean isDataAvailable(String key);

    /*
     * Liste
     */
    public void lPush(String liste, String value);
    public void rPush(String liste, String value);
    public String  lRange(String liste, int debut, int fin) ;
    public String  lIndex(String liste, int index);
    public void    lRem(String liste, int index) ;
    public String  lLen(String liste); 

    /*
     * Set
     */
    public void  hSet(String set, int index, String key, String value); 
    public String  hGetAll(String set, int index); 
    public String  hGet(String set, int index, String key) ;
    public void  hDel(String set,int index); 

 
}

