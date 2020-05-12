/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iteratiorpatten1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */


// TV Kanal Sınıfı
 class TVKanali {
 public String kanalAdi;

    TVKanali(String kanalAdi) {
        this.kanalAdi = kanalAdi;
    }

    

    public String getKanalAdi() {
        return kanalAdi;
    }

    public void setKanalAdi(String kanalAdi) {
        this.kanalAdi = kanalAdi;
    }
    
}

//IKanalIterator Interface

interface IKanalIterator{
TVKanali Next();
TVKanali gecerliKanal();
boolean bittiMi();
        
}

// KanalAggragate Interface

 interface IKanalAggregate{
IKanalIterator getIterator();
}

class KanalConcerteAggragete implements   IKanalAggregate 
 {
private List<TVKanali> kanalListesi = new ArrayList<TVKanali>();

public int kanalSayisi ;

    public int getKanalSayisi() {
        
        return kanalListesi.size();
    }

public void Add (TVKanali t){
kanalListesi.add(t);
} 
  
public TVKanali GetItem(int index){
return kanalListesi.get(index);

}
    @Override
    public IKanalIterator getIterator() {
        return new KanalConcereteIterator (this);
}
    

}        

class KanalConcereteIterator implements IKanalIterator{
private  int  index = 0;
private KanalConcerteAggragete kanallar ;
public KanalConcereteIterator(KanalConcerteAggragete kanal){
   this.kanallar = kanal;
}
    @Override
        public boolean bittiMi() {
            return index <kanallar.getKanalSayisi();

        }
@Override
    public TVKanali gecerliKanal() {
        return kanallar.GetItem(index);

    }

    @Override
    public TVKanali Next() {
        index++;
        if(bittiMi()){
        return kanallar.GetItem(index);
        }
        else {
        return null ;
        }
    }

    
    
    

    


}

public class IteratiorPatten1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
         System.out.println("kanallar  " );

       KanalConcerteAggragete kca = new  KanalConcerteAggragete();
       kca.Add(new TVKanali ("Kanal D"));
       kca.Add(new TVKanali ("Star"));
       kca.Add(new TVKanali ("TRT 1"));
       kca.Add(new TVKanali ("CNN TÜRK"));
        
       
        IKanalIterator  kanal = kca.getIterator();
        
        while (kanal.bittiMi()){
        
            System.out.println("kanal adı  " + kanal.gecerliKanal().kanalAdi);
            kanal.Next();
           
        }
        
    }
    
}
