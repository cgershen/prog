
//Immutable Objects/////////////////////////////////////////////////////////////

//Synchronized Class Example
class SynchronizedRGB {

    // Values must be between 0 and 255.
    private int red;
    private int green;
    private int blue;
    private String name;

    private void check(int red, int green, int blue) {
        if (red < 0 || red > 255 || green < 0 || green > 255
                    || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    public SynchronizedRGB(int red, int green, int blue, String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }

    public void set(int red, int green, int blue, String name) {
        check(red, green, blue);
        synchronized (this) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }
    }

    public synchronized int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void invert() {
        red = 255 - red;
        green = 255 - green;
        blue = 255 - blue;
        name = "Inverse of " + name;
    }
}


class ThreadMutable1 extends Thread{  
  SynchronizedRGB color;  
  ThreadMutable1(SynchronizedRGB color){  
     this.color = color;  
  }  
  public void run(){
   
    int myColorInt = color.getRGB();
    String myColorName = color.getName();
    
    System.out.format("Color: %d Nombre: %s%n", myColorInt, myColorName);
    
  }
}  

class ThreadMutable2 extends Thread{  
  SynchronizedRGB color;  
  ThreadMutable2(SynchronizedRGB color){  
     this.color = color;  
  }  
  public void run(){ 
      color.set(10, 10, 10, "black");
  }  
}

//Defining Immutable Objects

final class ImmutableRGB {

    // Values must be between 0 and 255.
    final private int red;
    final private int green;
    final private int blue;
    final private String name;

    private void check(int red,
                       int green,
                       int blue) {
        if (red < 0 || red > 255
            || green < 0 || green > 255
            || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    public ImmutableRGB(int red,
                        int green,
                        int blue,
                        String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }


    public int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public String getName() {
        return name;
    }

    public ImmutableRGB invert() {
        return new ImmutableRGB(255 - red,
                       255 - green,
                       255 - blue,
                       "Inverse of " + name);
    }
}

class ThreadImmutable1 extends Thread{  
  ImmutableRGB color;  
  ThreadImmutable1(ImmutableRGB color){  
     this.color = color;  
  }  
  public void run(){
   
    int myColorInt = color.getRGB();
    String myColorName = color.getName();
    
    System.out.format("Color: %d Nombre: %s%n", myColorInt, myColorName);
    
  }
}  

class ThreadImmutable2 extends Thread{  
  ImmutableRGB color;  
  ThreadImmutable2(ImmutableRGB color){  
     this.color = color;  
  }  
  public void run(){ 
      color.invert();
  }  
}


public class ImmutableObjects {
    
     public static void main(String[] args) throws InterruptedException {
        
        
        SynchronizedRGB obj = new SynchronizedRGB(255,255,255,"white");//only one object  

        ThreadMutable1 t1 = new ThreadMutable1(obj);  
        ThreadMutable2 t2 = new ThreadMutable2(obj);  
 
        t1.start();  
        t2.start(); 

        t1.join();
        t2.join();
        
        
        /* 
        ImmutableRGB obji = new ImmutableRGB(255,255,255,"white");//only one object  

        ThreadImmutable1 ti1 = new ThreadImmutable1(obji);  
        ThreadImmutable2 ti2 = new ThreadImmutable2(obji);  
 
        ti1.start();  
        ti2.start(); 

        ti1.join();
        ti2.join();
        */
  
    }
}
