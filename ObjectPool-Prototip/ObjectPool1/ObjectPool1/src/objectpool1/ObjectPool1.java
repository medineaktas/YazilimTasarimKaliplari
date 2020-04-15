/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectpool1;

/**
 *
 * @author Lenovo
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Kitap {

    int ISBN;
    String KitapAdi;
}
class Musteri  implements Runnable {

    String  musteriAd;
    String Fatura;

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
class YayınEvi {

    private static int PoolMaxSize = 3;

    private static  YayınEvi instance = new YayınEvi();
    List<Kitap> pool = new ArrayList<Kitap>();

    private YayınEvi() {
        init();
    }

    private void init() {

        for (int i = 0; i < 3; i++) {
            pool.add(new Kitap());
        }
    }

   synchronized static Kitap getKitap() {

        if (instance.pool.size() == 0) {

        }
        
        Kitap uret = instance.pool.get(0);
        instance.pool.remove(0);
        System.out.println(uret);
        return uret;
    }
    


    
    
    static void release(Kitap uret) {
            instance.pool.add(uret);
        
    }
}

public class ObjectPool1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Musteri t = new Musteri(); 
        
        Thread t1 = new Thread(t); 
        
        
        
        


        Kitap k1 = YayınEvi.getKitap();
        Kitap k2 = YayınEvi.getKitap();
        Kitap k3 = YayınEvi.getKitap();

        YayınEvi.release(k3);

        Kitap k4 = YayınEvi.getKitap();
    }

}
