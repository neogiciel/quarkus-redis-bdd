## <h1>Application Quarkus Redis Base de données</h1>
***
<img src="https://upload.wikimedia.org/wikipedia/fr/thumb/6/6b/Redis_Logo.svg/701px-Redis_Logo.svg.png?20190421180155" alt="drawing" height="240px"/>

## Informations Générales
***
Mise en place d'un base de données redis avec sous Quarkus.

## Technologies
***
Technologies utilisées:
* Java 17 
* Maven 3.6
* Quarkus: 3.6.4
## Instalation
***
Deploiement de Redis docker-compose<br>
```
version: '3.3'

services:
  redis:
    image: redis:latest
    restart: always
    ports:
      - "6379:6379"
    volumes:
      - /path/to/local/dаta:/root/redis
      - /path/to/local/redis.conf:/usr/local/etc/redis/redis.conf
    environment:
      - REDIS_PASSWORD=test
      - REDIS_PORT=6379
      - REDIS_DATABASES=16
```
Lancement de Redis:
docker-compose up -d 

Lancement de l'application Quarkus<br>
```
$ mvn  clean
$ mvn quarkus:dev
```
Le service est accessible sur http://localhost:8080

## FAQs
***
**Manager d'appel à Redis**
La mise en place de requête Redis se fait via des Aplles CLI bas Niveau

---
@Singleton
public class RedisCLIImpl implements RedisCLI{
    
    @Inject
    private RedisDataSource redisDS;

    /*
     * Constructeur
     */
    public RedisCLIImpl(RedisDataSource redisDS) {
        this.redisDS = redisDS;
    }

    /*
     * setData(String key, String value)
     */
    public void setData(String key, String value) {
        Trace.info("SetData ("+key+","+value+")");
        redisDS.execute("SET",key,value).toString();
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
        redisDS.execute("LPUSH",liste,value).toString();
    }


    /*
     * rPush(String liste, String value)
     */
    public void rPush(String liste, String value) {
        redisDS.execute("RPUSH",liste,value).toString();
    }

    /*
     * lRange(String liste, int debut, int fin)
     */
    public String  lRange(String liste, int debut, int fin) {
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
        return redisDS.execute("LLEN",liste).toString();

   }
    /*
     * hSet(String liste, int index, String key, String value) 
     * HSET user:1 username "John"
     */
    public void  hSet(String set, int index, String key, String value) {
        redisDS.execute("HSET",set+":"+Integer.toString(index),key,value);
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

---
