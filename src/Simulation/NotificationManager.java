package Simulation;

import java.util.Vector;

public class NotificationManager {
    public NotificationManager(){
        notifications = new Vector<String>();
    }

    public void add(String notification){
        notifications.add(notification);
    }

    public void clear(){
        notifications.clear();
    }

    public String printOut(){
        StringBuilder out = new StringBuilder();
        for(String notification : notifications){
            out.append(notification).append("\n");
        }

        return out.toString();
    }

    private final Vector<String> notifications;
}
