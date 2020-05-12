/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observepattern;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */

// Haber Kaynağı  interface
 interface Subject {


// gözlemcileri kaydetme ve kayıtlarını kaldırma 
        public void register(Observer obj);
	public void unregister(Observer obj);
	
// gözlemcileri değişime bildirme 
        public void notifyObservers();
	
        
// güclleme metodu
	public Object getUpdate(Observer obj);
	
}

// Gözlemci İnterface
interface Observer {
	
// gözlemciyi güncelleme
	public void update();
	
	public void setSubject(Subject sub);
}

//  Bilgi Kaynağı
 class MyTopic implements Subject {

	private List<Observer> observers;
	private String message;
	private boolean changed;
	private final Object MUTEX= new Object();
	
	public MyTopic(){
		this.observers=new ArrayList<>();
	}
	@Override
	public void register(Observer obj) {
		if(obj == null) throw new NullPointerException("Null Observer");
		synchronized (MUTEX) {
		if(!observers.contains(obj)) observers.add(obj);
		}
	}

	@Override
	public void unregister(Observer obj) {
		synchronized (MUTEX) {
		observers.remove(obj);
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> observersLocal = null;
		synchronized (MUTEX) {
			if (!changed)
				return;
			observersLocal = new ArrayList<>(this.observers);
			this.changed=false;
		}
		for (Observer obj : observersLocal) {
			obj.update();
		}

	}

	@Override
	public Object getUpdate(Observer obj) {
		return this.message;
	}
	
	public void postMessage(String msg){
		System.out.println("Yeni Mesajlar:"+msg);
		this.message=msg;
		this.changed=true;
		notifyObservers();
	}

}

//  Subscriberlar 
 class MyTopicSubscriber implements Observer {
	
	private String name;
	private Subject topic;
	
	public MyTopicSubscriber(String nm){
		this.name=nm;
	}
	@Override
	public void update() {
		String msg = (String) topic.getUpdate(this);
		if(msg == null){
			System.out.println(name+":: Eski Mesaj ");
		}else
		System.out.println(name+":: Tüketici  message::"+msg);
	}

	@Override
	public void setSubject(Subject sub) {
		this.topic=sub;
	}

}





public class ObservePattern {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        MyTopic topic = new MyTopic();
		
		// observers
		Observer obj1 = new MyTopicSubscriber("Obj1");
		Observer obj2 = new MyTopicSubscriber("Obj2");
		Observer obj3 = new MyTopicSubscriber("Obj3");
		
		// observers kayıt etme
		topic.register(obj1);
		topic.register(obj2);
		topic.register(obj3);
		
		obj1.setSubject(topic);
		obj2.setSubject(topic);
		obj3.setSubject(topic);
		
		//güncelleme kontrolü
		obj1.update();
                obj2.update();
		
		//yeni mesajlar subject tarafından
		topic.postMessage(" hello ben 2. güncelleme ");
                topic.postMessage("hello ben 3 günceleme");
               
                
                
        
    }
    
}
