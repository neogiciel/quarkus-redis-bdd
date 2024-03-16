package com.neogiciel.redis;

import io.quarkus.arc.lookup.LookupIfProperty.List;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.string.StringCommands;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.neogiciel.util.Trace;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/*
 * https://github.com/quarkusio/quarkus/issues/4453
 * 
 */

@Singleton
public class RedisCLIImpl implements RedisCLI{

    
    @Inject
    private RedisDataSource redisDS;
    
     private StringCommands<String, Integer> cmd;
           
    /*
     * Constructeur
     */
    public RedisCLIImpl(RedisDataSource redisDS) {
        this.redisDS = redisDS;
        cmd = redisDS.string(Integer.class);
    }

    /*
     * setData(String key, String value)
     */
    public void setData(String key, String value) {
        Trace.info("SetData ("+key+","+value+")");
        redisDS.execute("SET",key,value).toString();
        //redisDS.execute("EXPIRE",key,expireSecond).toString();
    }

    /*
     * detData(String key) 
     */
    public void delData(String key) {
        redisDS.execute("DEL",key);
    }        
     
    /*
     * getData(String key) 
     */
    public String getData(String key) {
        return  redisDS.execute("GET",key).toString();
    }

    /*
     * isDataAvailable(String key) 
     */
    public boolean isDataAvailable(String key){
        if(getData(key)!=null)
            return true;
        return false;
    }

     /*
     * rPush(String liste, String value)
     */
    public void lPush(String liste, String value) {
        Trace.info("lPush ("+liste+","+value+")");
        redisDS.execute("LPUSH",liste,value).toString();
    }


    /*
     * rPush(String liste, String value)
     */
    public void rPush(String liste, String value) {
        Trace.info("rPush ("+liste+","+value+")");
        redisDS.execute("RPUSH",liste,value).toString();
    }

    /*
     * lRange(String liste, int debut, int fin)
     */
    public String  lRange(String liste, int debut, int fin) {
        Trace.info("lRange ("+liste+","+debut+","+fin+")");
        String value = Integer.toString(debut)+" "+Integer.toString(fin);
        return redisDS.execute("LRANGE",liste,value).toString();

    }

    /*
     * lIndex(String liste, int index) 
     */
    public String  lIndex(String liste, int index) {
        return redisDS.execute("LINDEX",liste,Integer.toString(index)).toString();
    }

    /*
     * lRange(String liste, int debut, int fin)
     */
    public void lRem(String liste, int index) {
        String valueIndex = redisDS.execute("LINDEX",liste,Integer.toString(index)).toString();
        redisDS.execute("LREM",liste,Integer.toString(index),valueIndex);
     }


   /*
     * lLen(String liste)
     */
    public String  lLen(String liste) {
        Trace.info("lLen ("+liste+")");
        return redisDS.execute("LLEN",liste).toString();

    }

    /*
     * hSet(String liste, int index, String key, String value) 
     * HSET user:1 username "John"
     */
    public void  hSet(String set, int index, String key, String value) {
        Trace.info("hSet ("+set+","+index+","+key+","+value+")");
        redisDS.execute("HSET",set+":"+Integer.toString(index),key,value);
        //redisDS.execute("HSET","uesermap:0",key,value);
    }

    /*
     * hGetAll(String set, int index) {
     * HGETALL user:1
     */
    public String  hGetAll(String set, int index) {
        return redisDS.execute("HGETALL",set+":"+Integer.toString(index)).toString();
    }

    /*
     * hGet(String set, int index) {
     * HGET user:1 username # "John"
     */
    public String  hGet(String set, int index, String key) {
        return redisDS.execute("HGET",set+":"+Integer.toString(index),key).toString();
    }

    /*
     * hDel(String set) {
     * 
     */
    public void  hDel(String set,int index) {
        redisDS.execute("HDEL",set,Integer.toString(index));
    }



}

