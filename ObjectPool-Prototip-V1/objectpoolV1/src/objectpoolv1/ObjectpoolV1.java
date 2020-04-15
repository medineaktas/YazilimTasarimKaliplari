/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectpoolv1;

import java.util.ArrayList;

/**
 *
 * @author Medine Aktas
 */
class Ziyaretci {

    private boolean inUse = false;
    int ID ;
    int yas;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yas) {
        this.yas = yas;
    }
    

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

}

class Mutex {

    static private java.util.concurrent.locks.Lock reentrantLock
            = new java.util.concurrent.locks.ReentrantLock();

    public void acquire() {
        reentrantLock.lock();
    }

    public void release() {
        reentrantLock.unlock();
    }

}


/*

Mutex’ler uygulamanın yazıldığı dil ve Runtime tarafından sağlanan basit veri yapılarıdır.
Farklı Thread’ler tarafından paylaşılan her bir kaynak için kaynağa olan erişimi düzenlemek üzere bir Mutex yaratılır.
Paylaşılan kaynağa erişim yapılan kod bölgesi Critical Section olarak adlandırılır.
Kaynakla işi olan Thread, Mutex'in sahipliğini almaya (Acquire) çalışır.
Mutex o anda başka bir Thread tarafından tutulmuyorsa Thread Mutex'i alır, Critical Section'a girerek ilgili kaynağı kullanır.
Diğer durumda, yani Mutex o anda başka bir Thread tarafından kullanılıyor ise, ikinci Thread işlemci tarafından beklemeye alınır.
Mutex'i tutan Thread Critical Section'ı bitirip Mutex'i bırakırken, halihazırda Mutex'in bırakılmasını bekleyen Thread uyandırılır ve 
Mutex'in sahipliğini alarak Critical Section'a girer ve paylaşılan kaynağa erişim sağlar.

 */
class Lock {

    private static Mutex mutex = new Mutex();

    public Lock() {
        mutex.acquire();
    }

    public void release() {
        mutex.release();
    }

}

class Havuz {
    
    //havuz boyutu belirlenir

    public static final int havuzBoyutu = 2;
    // Array list oluşturdum ziyaretci tipinde
    private ArrayList< Ziyaretci> liste;
    private int maxPoolSize = havuzBoyutu;
    // Singleton pateni için private static nesne extends  edilice private 
    protected static Havuz instance = null;

    protected Havuz() {
        liste = new ArrayList< Ziyaretci>();
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Ziyaretci acquireZiyaretci() {
        // Thread senkronizasyon 
        Lock criticalSection = new Lock();
        for (Ziyaretci ziyaretci : liste) {
            if (!ziyaretci.isInUse()) {
                ziyaretci.setInUse(true);
                criticalSection.release();
                return ziyaretci;
            }
        }
        if (liste.size() >= getMaxPoolSize()) {
            criticalSection.release();
            return null;
        }
        Ziyaretci ziyaretci = new Ziyaretci();
        ziyaretci.setInUse(true);
        liste.add(ziyaretci);

        criticalSection.release();
        return ziyaretci;
    }

    public void releaseZiyaretci(Ziyaretci subject) {
        Lock criticalSection = new Lock();
        int idx = liste.indexOf(subject);
        if (idx == -1) {
            criticalSection.release();
            return;
        }
        Ziyaretci ziyaretci = liste.get(idx);
        ziyaretci.setInUse(false);
        criticalSection.release();
    }

    public static Havuz getInstance() {
        Lock criticalSection = new Lock();

        if (instance == null) {
            instance = new Havuz();
        }

        criticalSection.release();

        return instance;
    }

}

public class ObjectpoolV1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Havuz pool = Havuz.getInstance();


        Ziyaretci ziyaretci1 = pool.acquireZiyaretci();

        System.out.println("1 ziyaretci kullanıyor   " + ziyaretci1.hashCode());

        Ziyaretci ziyaretci2 = pool.acquireZiyaretci();

        System.out.println("2 ziyaretci kullanıyor   " + ziyaretci2.hashCode());

        try {
            Ziyaretci ziyaretci3 = pool.acquireZiyaretci();

            System.out.println("3 ziyaretci kullanıyor   " + ziyaretci3.hashCode());
        } catch (NullPointerException e) {
            System.out.println("Liste Dolu ");
        }

        pool.releaseZiyaretci(ziyaretci1);

        try {
            Ziyaretci ziyaretci3 = pool.acquireZiyaretci();

            System.out.println("3 ziyaretci kullanıyor   " + ziyaretci3.hashCode());
        } catch (NullPointerException e) {
            System.out.print("Liste Dolu ");
        }

    }

}
