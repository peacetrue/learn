package com.github.peacetrue.learn.event;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ListenAll.class)
public class ListenAllTest {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void handle() throws Exception {
        Assert.assertNull(ListenAll.event1);
        Event1 event1 = new Event1();
        applicationEventPublisher.publishEvent(event1);
        Assert.assertEquals(event1, ListenAll.event1);

        Assert.assertNull(ListenAll.event2);
        Event2 event2 = new Event2();
        applicationEventPublisher.publishEvent(event2);
        Assert.assertEquals(event2, ListenAll.event2);

        Assert.assertNull(ListenAll.event3);
        Event3 event3 = new Event3();
        applicationEventPublisher.publishEvent(event3);
        Assert.assertEquals(event3, ListenAll.event3);
        Assert.assertNotEquals(event2, ListenAll.event2);
    }
}