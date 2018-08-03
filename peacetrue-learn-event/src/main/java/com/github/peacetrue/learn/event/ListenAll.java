package com.github.peacetrue.learn.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author xiayx
 */
@Component
public class ListenAll {

    public static Event1 event1;
    public static Event2 event2;
    public static Event3 event3;

    @EventListener
    public void handle(Event1 event1) {
        System.out.println(event1);
        ListenAll.event1 = event1;
    }

    @EventListener
    public void handle(Event2 event2) {
        System.out.println(event2);
        ListenAll.event2 = event2;
    }

    @EventListener
    public Event2 handle(Event3 event3) {
        System.out.println(event3);
        ListenAll.event3 = event3;
        return new Event2();
    }

}
