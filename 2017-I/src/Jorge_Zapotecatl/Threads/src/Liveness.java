
class Deadlock {
    static class Friend {
        private final String name;
        public Friend(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        
        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s" + " has bowed to me!%n", this.name, bower.getName());
            bower.bowBack(this);
            //this.selfBow(bower);
        }
        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
        }
       
        public void selfBow(Friend bower) {
            synchronized(this) {//No se bloque si ya esta bloqueado, es diferente de C...
            System.out.format("Again block, " + "%s: %s%n", this.name, bower.getName());
            }
        }
    }
    
    public static void main(String[] args) {
        
        final Deadlock.Friend alphonse = new Deadlock.Friend("Alphonse");
        final Deadlock.Friend gaston = new Deadlock.Friend("Gaston");
        
        new Thread(new Runnable() {
            public void run() { alphonse.bow(gaston); }
        }).start();
        
        new Thread(new Runnable() {
            public void run() { gaston.bow(alphonse); }
        }).start();
    }
}


//public class Liveness {
    
    
//}
