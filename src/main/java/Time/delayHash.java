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


    // ���ü��Ĺ���ʱ��
    public void setKeyExpiration(String key, long delayInSeconds) {
        long currentTimestamp = System.currentTimeMillis();
        long expirationTimestamp = currentTimestamp + (delayInSeconds * 1000);
        keyExpirationMap.put(key, expirationTimestamp);
        scheduler.schedule(() -> handleExpiredKey(key), delayInSeconds, TimeUnit.SECONDS);
//        System.out.println(keyExpirationMap);
    }

    // �鿴����ʣ�����ʱ��
    public long getKeyTtl(String key) {
//        System.out.println(keyExpirationMap);
        if (keyExpirationMap.containsKey(key)) {
            long currentTimestamp = System.currentTimeMillis();
            long expirationTimestamp = keyExpirationMap.get(key);
            long remainingTime = expirationTimestamp - currentTimestamp;
            if (remainingTime > 0) {
                return remainingTime / 1000; // ת��Ϊ��
            }
        }
        return -1; // ����������ڻ��ѹ��ڣ��򷵻�-1��ʾ����ʱ����ѹ���
    }
    private void handleExpiredKey(String key) {
        keyExpirationMap.remove(key);

        System.out.println("Key�ѹ��ڣ�" + key);
        // �������д��ȥ����Ӧ��key���������һ�����ݽṹ��ͬ�������һ����key�����Ի����ͻ�Ŀ�����
        HashMap<String, String> hm = SSHashMap.getSSHashMap();
        HashMap<String, LinkedList<String>> hml = SLHashMap.getSLHashMap();
        HashMap<String, HashMap<String, String>> hmh = SHHashMap.getSHHashMap();
//      ��ʾ��ǰ�����һ����ϣû�����key
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
