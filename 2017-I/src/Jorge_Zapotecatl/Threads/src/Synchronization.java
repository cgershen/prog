
//Thread Interference and Memory Consistency Errors///////////////////////////////////////////////////////////
//Simple class 
class Counter {
    private int c = 0;
 
    public void increment() {
        c++;
    }
 
    public void decrement() {
        c--;
    }
    public int value() {
        return c;
    }
}
 
class Thread1 extends Thread{  
  Counter t;  
  Thread1(Counter t){  
     this.t = t;  
  }  
  public void run(){
    for(int i = 0; i < 1000000; i++)
       t.increment();  
  }  
}  

class Thread2 extends Thread{  
  Counter t;  
  Thread2(Counter t){  
     this.t = t;  
  }  
  public void run(){ 
    for(int i = 0; i < 1000000; i++)
       t.decrement();  
  }  
}

//Synchronized Methods//////////////////////////////////////////////////////////
class SynchronizationCounter {
    private int c = 0;
 
    public synchronized void increment() {
        c++;
    }
    public synchronized void decrement() {
        c--;
    }
    public int value() {
        return c;
    }
}

class ThreadSyn1 extends Thread{  
  SynchronizationCounter t;  
  ThreadSyn1(SynchronizationCounter t){  
     this.t = t;  
  }  
  public void run(){
    for(int i = 0; i < 1000000; i++)
       t.increment();  
  }  
}  

class ThreadSyn2 extends Thread{  
  SynchronizationCounter t;  
  ThreadSyn2(SynchronizationCounter t){  
     this.t = t;  
  }  
  public void run(){ 
    for(int i = 0; i < 1000000; i++)
       t.decrement();  
  }  
}

//Synchronized Statements///////////////////////////////////////////////////////
class MsLunch {
    private long c1 = 0;
    private long c2 = 0;
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
        synchronized(lock1) {
            c1++;
        }
    }
    public void inc2() {
        synchronized(lock2) {
            c2++;
        }
    }
    public long getValueC1() {
        return c1;
    }
    public long getValueC2() {
        return c2;
    }
}


class ThreadSta1 extends Thread{  
  MsLunch t;  
  ThreadSta1(MsLunch t){  
     this.t = t;  
  }  
  public void run(){
    for(int i = 0; i < 1000000; i++){
       t.inc1();
       t.inc2();
    }
  }  
}  

class ThreadSta2 extends Thread{  
  MsLunch t;  
  ThreadSta2(MsLunch t){  
     this.t = t;  
  }  
  public void run(){ 
    for(int i = 0; i < 1000000; i++){
       t.inc1();
       t.inc2();
    }
  }  
}


class Synchronization {

public static void main(String args[])  throws InterruptedException {

//Thread Interference and Memory Consistency Errors
Counter obj = new Counter();//only one object  
Thread1 t1 = new Thread1(obj);  
Thread2 t2 = new Thread2(obj);  
 
  t1.start();  
  t2.start(); 

  t1.join();
  t2.join();
  
  //Memory Consistency Errors
  System.out.format("Valor de c: %d (el resutado debe ser cero)%n", obj.value());


/*//Synchronized Methods
SynchronizationCounter objs = new SynchronizationCounter();//only one object  
ThreadSyn1 ts1 = new ThreadSyn1(objs);  
ThreadSyn2 ts2 = new ThreadSyn2(objs);  
 
  ts1.start();  
  ts2.start(); 

  ts1.join();
  ts2.join();
  
  //Memory Consistency Errors
  System.out.format("Valor de c: %d (el resutado es cero)%n", objs.value());
*/

/*
//Synchronized Statements
MsLunch lunch = new MsLunch();//only one object  
ThreadSta1 sta1 = new ThreadSta1(lunch);  
ThreadSta2 sta2 = new ThreadSta2(lunch);  
 
  sta1.start();  
  sta2.start(); 

  sta1.join();
  sta2.join();
  
  //Memory Consistency Errors
  System.out.format("Valor de c1 y c2: %d  %d%n", lunch.getValueC1(), lunch.getValueC2());
*/

}  
}


