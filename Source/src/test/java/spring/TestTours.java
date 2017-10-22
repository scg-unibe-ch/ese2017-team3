package spring;

import org.junit.Test;
import spring.entity.Tour;

import java.util.ArrayList;
import java.util.List;

public class TestTours {

    @Test
    public void createTestTours() {
        ArrayList touren = new ArrayList<Tour>() ;
        touren.add(new Tour("Kuh, "));
        touren.add(new Tour("Schafe"));
        touren.add(new Tour("Panda"));
    }
}
