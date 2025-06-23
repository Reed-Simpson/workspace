package test;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingTimerExample {

    public static void main(String[] args) throws InterruptedException {
        // Define the delay in milliseconds (e.g., 1000ms = 1 second)
        int delay = 1000; 

        // Create an ActionListener to define the task to be performed
        ActionListener taskPerformer = new ActionListener() {
            int count = 0; // Counter to demonstrate repeated execution

            public void actionPerformed(ActionEvent evt) {
                System.out.println("Timer fired! Count: " + ++count);
                // You can update Swing components here safely
                // For example: myLabel.setText("Time: " + System.currentTimeMillis());

                // Stop the timer after a certain number of firings (optional)
                if (count >= 5) {
                    ((Timer) evt.getSource()).stop(); // Stop the timer
                    System.out.println("Timer stopped.");
                }
            }
        };

        // Create the Timer object
        // The first argument is the delay, the second is the ActionListener
        Timer timer = new Timer(delay, taskPerformer);

        // Start the timer
        timer.start();

        System.out.println("Timer started. Waiting for events...");
        Thread.sleep(5000);
    }
}