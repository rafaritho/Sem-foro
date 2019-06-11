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
              
                // Obtém a lock 
                sem.acquire(); 
              
                System.out.println(threadName + " recebe permissão"); 
          
                // Acessa a classe compartilhado 
                // outra thread espera até 
                // a liberação do lock  
                for(int i=0; i < 5; i++) 
                { 
                    Shared.count++; 
                    System.out.println(threadName + ": " + Shared.count);           
                } 
            } catch (InterruptedException exc) { 
                    System.out.println(exc); 
                } 
          
                // Thread A libera a permissão 
                System.out.println(threadName + " libera a permissão"); 
                sem.release(); 
        } 
          
        // Roda a thread B 
        else
        { 
            System.out.println("Iniciando thread " + threadName); 
            try 
            { 
                // Recebe permissão 
                System.out.println(threadName + " está aguardando permissão"); 
              
                // Obtém a lock 
                sem.acquire(); 
              
                System.out.println(threadName + " recebe permissão"); 
          
                // Acessa a classe compartilhado 
                // outra thread espera até 
                // a liberação do lock 
                for(int i=0; i < 5; i++) 
                { 
                    Shared.count--; 
                    System.out.println(threadName + ": " + Shared.count);           
                    
                } 
            } catch (InterruptedException exc) { 
                    System.out.println(exc); 
                } 
                // Thread B libera a permissão  
                System.out.println(threadName + " libera a permissão"); 
                sem.release(); 
        } 
    } 
} 
public class Main  
{ 
    public static void main(String args[]) throws InterruptedException  
    { 
        // criando o objeto Semaphore 
        // com o número permissões 1  
        Semaphore sem = new Semaphore(1); 
          
        // criando duas threads com nome A e B 
        // Thread A vai incrementar o contador
        // Thread B vai decrementar o contador
        MyThread mt1 = new MyThread(sem, "A"); 
        MyThread mt2 = new MyThread(sem, "B"); 
          
        // iniciando threads A e B 
        mt1.start(); 
        mt2.start(); 
          
        // esperando pelas threads A e B  
        mt1.join(); 
        mt2.join(); 
          
       
        // Ambas as threads completam sua execução
        System.out.println("count: " + Shared.count); 
    } 
} 
