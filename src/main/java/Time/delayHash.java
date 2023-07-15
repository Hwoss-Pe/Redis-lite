package Time;

import HashMapControl.SHHashMap;
import HashMapControl.SLHashMap;
import HashMapControl.SSHashMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class delayHash {
    static HashMap<String, Long> keyExpirationMap =  new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    // 设置键的过期时间
    public void setKeyExpiration(String key, long delayInSeconds) {
        long currentTimestamp = System.currentTimeMillis();
        long expirationTimestamp = currentTimestamp + (delayInSeconds * 1000);
        keyExpirationMap.put(key, expirationTimestamp);
        scheduler.schedule(() -> handleExpiredKey(key), delayInSeconds, TimeUnit.SECONDS);
//        System.out.println(keyExpirationMap);
    }

    // 查看键的剩余过期时间
    public long getKeyTtl(String key) {
//        System.out.println(keyExpirationMap);
        if (keyExpirationMap.containsKey(key)) {
            long currentTimestamp = System.currentTimeMillis();
            long expirationTimestamp = keyExpirationMap.get(key);
            long remainingTime = expirationTimestamp - currentTimestamp;
            if (remainingTime > 0) {
                return remainingTime / 1000; // 转换为秒
            }
        }
        return -1; // 如果键不存在或已过期，则返回-1表示无限时间或已过期
    }
    private void handleExpiredKey(String key) {
        keyExpirationMap.remove(key);

        System.out.println("Key已过期：" + key);
        // 在这里就写把去掉对应的key，由于命令不一样数据结构不同不会存在一样的key，忽略缓存冲突的可能性
        HashMap<String, String> hm = SSHashMap.getSSHashMap();
        HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
        HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
//      表示当前里面第一个哈希没有这个key
        if(removeKeyHm(hm,key)==0){
            if(removeKeyHml(hml,key)==0){
                removeKeyHmh(hmh,key);
            }
        }
    }



    public int removeKeyHm(HashMap<String ,String > hm , String key){
        if(hm.containsKey(key)){
            hm.remove(key);
            return 1;
        }
        return 0;
    }
    public int removeKeyHml(HashMap<String , LinkedList<String>> hml , String key){
        if(hml.containsKey(key)){
            hml.remove(key);
            return 1;
        }
        return 0;
    }
    public int removeKeyHmh(HashMap<String ,HashMap<String,String> > hmh , String key){
        if(hmh.containsKey(key)){
            hmh.remove(key);
            return 1;
        }
        return 0;
    }
}
