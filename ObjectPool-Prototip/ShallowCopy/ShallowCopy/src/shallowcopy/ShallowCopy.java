/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shallowcopy;

/**
 *
 * @author Lenovo
 */


 class Personel_Departmanı {
    String personelID;
 
    String meslek;
 
 
    public Personel_Departmanı(String personelID, String meslek) {
        this.personelID = personelID;
 
        this.meslek = meslek;
 
    }
}
 
class Calısan implements Cloneable {
    int id;
 
    String name;
 
    Personel_Departmanı dept;
 
    public Calısan(int id, String name, Personel_Departmanı dept) {
        this.id = id;
 
        this.name = name;
 
        this.dept = dept;
    }
 
 
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


public class ShallowCopy {

    /**
     * @param args the command line arguments
     */
    
   

    public static void main(String[] args) {
        // TODO code application logic here
        
        Personel_Departmanı dept1 = new Personel_Departmanı ("1", "Doktor");
 
        Calısan c1 = new Calısan (111, "Ayşe", dept1);
 
        Calısan c2 = null;
 
        try {
 
            c2 = (Calısan) c1.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
 
        
        System.out.println(c1.dept.meslek);
 
        c2.dept.meslek = "Hemşire";
 
        System.out.println(c1.dept.meslek); 
        System.out.println(c2.dept.meslek); 

        
        System.out.println(c2.name); 

        
    }
    
}
