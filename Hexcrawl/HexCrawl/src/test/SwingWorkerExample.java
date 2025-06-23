package test;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

@SuppressWarnings("serial")
public class SwingWorkerExample extends JFrame {

    private final JLabel statusLabel;
    private final JButton startButton;
    private final JProgressBar progressBar;

    public SwingWorkerExample() {
        setTitle("SwingWorker Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new FlowLayout());

        statusLabel = new JLabel("Ready");
        startButton = new JButton("Start Task");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        startButton.addActionListener(e -> startLongTask());

        add(statusLabel);
        add(startButton);
        add(progressBar);

        setVisible(true);
    }

    private void startLongTask() {
        startButton.setEnabled(false);
        statusLabel.setText("Task Started");
        progressBar.setValue(0);

        LongTaskWorker worker = new LongTaskWorker();
        worker.execute();
    }

    private class LongTaskWorker extends SwingWorker<Void, Integer> {

        @Override
        protected Void doInBackground() throws Exception {
            Random random = new Random();
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(random.nextInt(50)); // Simulate work
                publish(i);
            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            int progress = chunks.get(chunks.size() - 1);
            progressBar.setValue(progress);
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
            statusLabel.setText("Task Completed");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingWorkerExample::new);
    }
}