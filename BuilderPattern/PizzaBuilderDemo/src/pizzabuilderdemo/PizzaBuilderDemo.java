/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzabuilderdemo;

/**
 *
 * @author medine
 */


/* "Product" */
class Pizza {
    private String hamur;
    private String sos = "";
    private String malzeme = "";
    private String ektraSos="";

    public String getEktraSos() {
        return ektraSos;
    }

    public void setEktraSos(String ektraSos) {
        this.ektraSos = ektraSos;
    }

    public String getHamur() {
        return hamur;
    }

    public void setHamur(String hamur) {
        this.hamur = hamur;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public String getMalzeme() {
        return malzeme;
    }

    public void setMalzeme(String susleme) {
        this.malzeme = susleme;
    }

   
    
}

/* "Abstract Builder" */
abstract class PizzaBuilder {
    protected Pizza pizza;

    public Pizza getPizza() {
        return pizza;
    }

    public void createNewPizzaProduct() {
        pizza = new Pizza();
    }

    public abstract void buildHamur();
    public abstract void buildSos();
    public abstract void buildMalzeme();
    public abstract void buildExtraSos();
    
}

/* "ConcreteBuilder" */
class HawaiianPizzaBuilder extends PizzaBuilder {
    @Override
    public void buildHamur() {
        pizza.setHamur("cross");
    }

    public void buildSos() {
        pizza.setSos("mild");
    }

    public void buildMalzeme() {
        pizza.setMalzeme("ham + pineapple");
    }

   
    public void buildExtraSos() {
        
    }
}

/* "ConcreteBuilder" */
class SpicyPizzaBuilder extends PizzaBuilder {
    public void buildHamur() {
        pizza.setHamur("pan baked");
    }

    public void buildSos() {
        pizza.setSos("hot");
    }

    public void buildMalzeme() {
        pizza.setMalzeme("pepperoni + salami");
    }

    public void buildExtraSos() {
        pizza.setEktraSos("acı sos ");
    }
    
//     public String toString() {
//        return  "pizzanızın hamuru "+" "+pizza.getHamur()  +"  pizzanızın sosu  "+ pizza.getSos() +"  pizzanızın malzemeleri  "+ pizza  + "pizza ektra sos  " + ektraSos;
//    }
}

/* "Director" */
class Waiter {
    private PizzaBuilder pizzaBuilder;

    public void setPizzaBuilder(PizzaBuilder pb) {
        pizzaBuilder = pb;
    }

    public Pizza getPizza() {
        return pizzaBuilder.getPizza();
    }

    public void constructPizza() {
        pizzaBuilder.createNewPizzaProduct();
        pizzaBuilder.buildHamur();
        pizzaBuilder.buildSos();
        pizzaBuilder.buildMalzeme();
        pizzaBuilder.buildExtraSos();
    }
}

public class PizzaBuilderDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
         Waiter waiter = new Waiter();
        PizzaBuilder hawaiianPizzabuilder = new HawaiianPizzaBuilder();
        PizzaBuilder spicyPizzaBuilder = new SpicyPizzaBuilder();

        waiter.setPizzaBuilder( hawaiianPizzabuilder );
        waiter.constructPizza();

        Pizza pizza = waiter.getPizza();
        
         System.out.println(pizza.getHamur());
         System.out.println(pizza.getMalzeme());
         System.out.println(pizza.getSos());

         

    }
    
}
