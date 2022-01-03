package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.IOException;
import java.util.Timer;

public class PaymentTimer {
    private Timer timer;

    public PaymentTimer() throws IOException {
        timer = new Timer();
        timer.schedule(new PaymentProcessTask(), 3600000);
    }
}