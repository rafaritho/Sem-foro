import java.util.concurrent.*; 
  
//Classe compartilhada 
class Shared  
{ 
    static int count = 0; 
} 
  
class MyThread extends Thread 
{ 
    Semaphore sem; 
    String threadName; 
    public MyThread(Semaphore sem, String threadName)  
    { 
        super(threadName); 
        this.sem = sem; 
        this.threadName = threadName; 
    } 
  
    @Override
    public void run() { 
          
        // Inicia thread A 
        if(this.getName().equals("A")) 
        { 
            System.out.println("Iniciando thread " + threadName); 
            try 
            { 
                //Pede a permissão primeiro  
                System.out.println(threadName + " está aguardando permissão"); 
              
                // Obtem a lock 
                sem.acquire(); 
              
                System.out.println(threadName + " recebe permissão"); 
          
                // Acessa o a classe compartilhada
                // a thread B aguarda até que essa 
                // libere a lock 
                for(int i=0; i < 5; i++) 
                { 
                    Shared.count++; 
                    System.out.println(threadName + ": " + Shared.count);           
                } 
            } catch (InterruptedException exc) { 
                    System.out.println(exc); 
                } 
          
                // Thread A larga a permissão 
                System.out.println(threadName + " libera a permissão"); 
                sem.release(); 
        } 
          
        // run by thread B 
        else
        { 
            System.out.println("Iniciando thread " + threadName); 
            try 
            { 
                // First, get a permit. 
                System.out.println(threadName + " está aguardando permissão"); 
              
                // acquiring the lock 
                sem.acquire(); 
              
                System.out.println(threadName + " recebe permissão"); 
          
                // Now, accessing the shared resource. 
                // other waiting threads will wait, until this  
                // thread release the lock 
                for(int i=0; i < 5; i++) 
                { 
                    Shared.count--; 
                    System.out.println(threadName + ": " + Shared.count);           
                    
                } 
            } catch (InterruptedException exc) { 
                    System.out.println(exc); 
                } 
                // Release the permit. 
                System.out.println(threadName + " libera a permissão"); 
                sem.release(); 
        } 
    } 
} 
public class Main  
{ 
    public static void main(String args[]) throws InterruptedException  
    { 
        // creating a Semaphore object 
        // with number of permits 1 
        Semaphore sem = new Semaphore(1); 
          
        // criando duas threads com nome A e B 
        // Note that thread A will increment the count 
        // and thread B will decrement the count 
        MyThread mt1 = new MyThread(sem, "A"); 
        MyThread mt2 = new MyThread(sem, "B"); 
          
        // iniciando threads A e B 
        mt1.start(); 
        mt2.start(); 
          
        // esperando pelas threads A e B  
        mt1.join(); 
        mt2.join(); 
          
       
        // both threads will complete their execution 
        System.out.println("count: " + Shared.count); 
    } 
} 
