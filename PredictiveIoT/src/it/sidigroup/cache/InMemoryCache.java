package it.sidigroup.cache;

import it.sidigroup.servlet.DataCollectorServlet;

import java.util.ArrayList;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * @author Crunchify.com
 */
 
public class InMemoryCache<K, T> {
 
    private long timeToLive;
    private LRUMap crunchifyCacheMap;
 
	private static Logger logger = LoggerFactory.getLogger(DataCollectorServlet.class);
    
    protected class CrunchifyCacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;
 
        protected CrunchifyCacheObject(T value) {
            this.value = value;
        }
    }
 
    public InMemoryCache(long crunchifyTimeToLive, final long crunchifyTimerInterval, int maxItems) {
    	logger.debug("In.mempry cache constructor call. Paramaters - ttl: "+crunchifyTimeToLive+" - time interval: "+crunchifyTimerInterval+" - max items: "+maxItems);
    	this.timeToLive = crunchifyTimeToLive * 1000;
 
        crunchifyCacheMap = new LRUMap(maxItems);
 
        if (timeToLive > 0 && crunchifyTimerInterval > 0) {
 
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(crunchifyTimerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });
 
            t.setDaemon(true);
            t.start();
        }
    }
 
    public void put(K key, T value) {
        synchronized (crunchifyCacheMap) {
            crunchifyCacheMap.put(key, new CrunchifyCacheObject(value));
        }
    }
 
    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (crunchifyCacheMap) {
            CrunchifyCacheObject c = (CrunchifyCacheObject) crunchifyCacheMap.get(key);
 
            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }
 
    public void remove(K key) {
        synchronized (crunchifyCacheMap) {
            crunchifyCacheMap.remove(key);
        }
    }
 
    public int size() {
        synchronized (crunchifyCacheMap) {
            return crunchifyCacheMap.size();
        }
    }
 
    @SuppressWarnings("unchecked")
    public void cleanup() {
 
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;
 
        synchronized (crunchifyCacheMap) {
            MapIterator itr = crunchifyCacheMap.mapIterator();
 
            deleteKey = new ArrayList<K>((crunchifyCacheMap.size() / 2) + 1);
            K key = null;
            CrunchifyCacheObject c = null;
 
            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (CrunchifyCacheObject) itr.getValue();
 
                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }
 
        for (K key : deleteKey) {
            synchronized (crunchifyCacheMap) {
                crunchifyCacheMap.remove(key);
            }
 
            Thread.yield();
        }
    }

	public static Logger getLogger() {
		return logger;
	}
}