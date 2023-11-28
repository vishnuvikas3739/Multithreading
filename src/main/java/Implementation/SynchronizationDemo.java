package Implementation;


class Counter {
    private int count = 0;

    // Synchronized method to increment the count
    public synchronized void increment() {
        count++;
        notify(); 
    }

    public synchronized int getCount() {
        return count;
    }

    // Synchronized method to wait until count is updated
    public synchronized void waitForUpdate() throws InterruptedException {
        while (count % 2 == 0) {
            wait(); 
        }
    }
}

class Synchronization implements Runnable {
    private Counter counter;

    public Synchronization(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            counter.increment();
            System.out.println(Thread.currentThread().getName() + " Count is: " + counter.getCount());

            try {
                Thread.sleep(1000); 

              
                if (counter.getCount() % 3 == 0) {
                    counter.waitForUpdate();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

 class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

     
        Synchronization s1 = new Synchronization(counter);
        Synchronization s2 = new Synchronization(counter);

       
        Thread t1 = new Thread(s1, "Here the Thread 1");
        Thread t2 = new Thread(s2, "Here the Thread 2");

      
        t1.start();
        t2.start();
    }
}
