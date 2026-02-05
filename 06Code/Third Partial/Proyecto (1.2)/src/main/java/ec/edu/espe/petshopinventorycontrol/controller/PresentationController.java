package ec.edu.espe.petshopinventorycontrol.controller;

import javax.swing.SwingWorker;

public final class PresentationController {

    private final PresentationView view;

    public PresentationController(PresentationView view) {
        this.view = view;
    }

    public void onInit() {
        view.setProgressRange(0, 100);
        view.setProgressValue(0);
        updateStatusForProgress(0);
    }

    public void onStart() {
        view.setStartEnabled(false);
        view.setProgressValue(0);
        updateStatusForProgress(0);

        new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30);
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                view.setProgressValue(value);
                updateStatusForProgress(value);
            }

            @Override
            protected void done() {
                updateStatusForProgress(100);
                view.openLoginAndClose();
            }
        }.execute();
    }

    private void updateStatusForProgress(int value) {
        String status;
        if (value < 10) {
            status = "Initializing systems...";
        } else if (value < 25) {
            status = "Loading core modules...";
        } else if (value < 45) {
            status = "Connecting to database...";
        } else if (value < 60) {
            status = "Database connection established...";
        } else if (value < 75) {
            status = "Loading user profiles...";
        } else if (value < 90) {
            status = "Finalizing startup...";
        } else {
            status = "Launching login...";
        }
        view.setStatusText(status);
    }
}
