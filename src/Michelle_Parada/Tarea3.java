public class CampoDeTiro {
    public static void main(String[] args) {
        Pistola arma = new Pistola();
        Cargar c = new Cargar(arma, 1);
        Descargar d = new Descargar(arma, 1);
        c.start();
        d.start();
    }
 }
 
 public class Pistola {
    private int cartucho;
    private boolean enposicion = true;

    public synchronized void disparar(int cartucho) {
        while (enposicion == false) {
            try {
                // Esperar a apuntar
                wait();
            } catch (InterruptedException e) { }
        }
        enposicion = false;
        notifyAll();
    }
 
    public synchronized void apuntar() {
        while (enposicion == true) {
            try {
                // Esperar a disparar
                wait();
            } catch (InterruptedException e) { }
        }
        enposicion = true;
        notifyAll();
    }

}

public class Cargar extends Thread {
    private Pistola arma;
    private int cartucho;

    public Cargar(Pistola arma, int cartucho) {
        this.arma = arma;
        this.cartucho = cartucho;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            arma.apuntar();
            System.out.println("Apuntar #" + this.cartucho
                               + " bala: " + i);
        }
    }
}

public class Descargar extends Thread {
    private Pistola arma;
    private int cartucho;

    public Descargar(Pistola arma, int cartucho) {
        this.arma = arma;
        this.cartucho = cartucho;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            arma.disparar(i);
            System.out.println("Descargar #" + this.cartucho
                               + " bala: " + i);
        }
    }
}
