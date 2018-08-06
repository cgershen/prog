/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Defining and Starting a Thread////////////////////////////////////////////////
//Provide a Runnable object
class HelloRunnable implements Runnable {

    public void run() {
        System.out.println("Hello from a thread (implements Runnable)!");
    }
}

//Subclass Thread
class HelloThread extends Thread {
    public void run() {
        System.out.println("Hello from a thread (extends Thread)!");
    }
}

//Pausing Execution with Sleep and Interrupts///////////////////////////////////
class SleepMessages implements Runnable {
    public void run() {
        String importantInfo[] = {
            "Hola",
            "Hello",
            "Olá",
            "Bonjour"
        };

        for (int i = 0; i < importantInfo.length; i++) {
            //Pause for 4 seconds
            try {
            Thread.sleep(4000);
            }
            catch(InterruptedException e) {
                // We've been interrupted: no more messages.
            }
            //Print a message
            System.out.println(importantInfo[i]);
        }
    }
}

//The Interrupt Status Flag///////////////////////////////////
class ClassInterrupt implements Runnable {
    public void run() {
        while (true) {
            if (Thread.interrupted()) {
                System.out.println("Hilo interrumpido");
                break;
            }
        }
    }
}

class SimpleThreads implements Runnable  {

    // Display a message, preceded by
    // the name of the current thread
    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }

    public static class MessageLoop
        implements Runnable {
        public void run() {
            String importantInfo[] = {
                "Hola",
                "Hello",
                "Olá",
                "Bonjour"
            };
            try {
                for (int i = 0; i < importantInfo.length; i++) {
                    // Pause for 4 seconds
                    Thread.sleep(4000);
                    // Print a message
                    threadMessage(importantInfo[i]);
                }
            } catch (InterruptedException e) {
                threadMessage("I wasn't done!");
            }
        }
    }

    public void run() {
   
        // Delay, in milliseconds before
        // we interrupt MessageLoop
        // thread (default one hour).
        long patience = 1000 * 60 * 60;

        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();
        Thread t = new Thread(new MessageLoop());
        t.start();

        threadMessage("Waiting for MessageLoop thread to finish");
        // loop until MessageLoop
        // thread exits
        while (t.isAlive()) {
            threadMessage("Still waiting...");
            // Wait maximum of 1 second
            // for MessageLoop thread
            // to finish.
            
            try {
              t.join(1000);
            }
            catch (InterruptedException e) {
              //...
            }
            
            if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive()) {
                threadMessage("Tired of waiting!");
                t.interrupt();
                // Shouldn't be long now
                // -- wait indefinitely
                try {
                  t.join();
                }
                catch (InterruptedException e) {
                  //...
                }
            }
        }
        threadMessage("Finally!");
            
    }
    
}


public class ThreadObjects {
    public static void main(String args[]) throws InterruptedException {
        
        //(new Thread(new HelloRunnable())).start(); //Provide a Runnable object
        
        //(new HelloThread()).start(); //Subclass Thread
        
        //(new Thread(new SleepMessages())).start();//Pausing Execution with Sleep
        
        /*//The Interrupt Status Flag
        Thread loop = new Thread(new ClassInterrupt());
        loop.start();
        loop.interrupt();
        */
        
        
        (new Thread(new SimpleThreads())).start(); //Provide a Runnable object
        
        
    }
}
