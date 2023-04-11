/**
 * 
 */

/**
 * @author Tyler Schultz
 * 3/15/23
 * An example of the philosophers problem implemented within Java
 */

public class TestPhilosopher {

    public static void main(String[] args) throws Exception {

        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];					//5 forks which means each fork points to another resource

        for (int i = 0; i < forks.length; i++) 
        {
            forks[i] = new Object();										//make sure we initialize all of the forks
        }

        for (int i = 0; i < philosophers.length; i++) 
        {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            philosophers[i] = new Philosopher(leftFork, rightFork);			//make a new philosopher object based on the left and right fork parameter
            
            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }
}
class Philosopher implements Runnable {

	private Object leftFork;
    private Object rightFork;
    
    public Philosopher(Object leftFork, Object rightFork) {					//constructor
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }
    
    @Override
	public void run() {
		try 
		{
			while (true) 
			{
				doAction(": Thinking");
				/*We use synchronization so only one thread may access the section below (CS) one at a time*/
				synchronized (leftFork) {
					doAction(": Picked up left fork");
					synchronized (rightFork) {
						doAction(": Picked up right fork. Now eating"); 			//Will start eating after the right fork since the left fork is already obtained
						doAction(": Put down right fork");
	                    }
	                    
					doAction(": Put down left fork. Back to thinking");
					}
				}
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            return;
	        }
	    }
	
	 private void doAction(String action) throws InterruptedException {			//All of our output can be sourced here
	        System.out.println(Thread.currentThread().getName() + " " + action);
	        Thread.sleep(10000);													//sleep timer set to 5 seconds. please edit if you want a faster execution
	    }
	}

/*Note that Deadlock can happen here. Solution to 8 will fix that*/
