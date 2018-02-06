package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameOnTest {
    String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
    Layout siebelMap;

    @Before
    public void setUp() throws Exception {
        siebelMap = URLget.makeApiRequest(url);
    }

    @Test
    public void startingRoom() {
        assertEquals("MatthewsStreet", siebelMap.getStartingRoom());
    }

    @Test
    public void endingRoom() {
        assertEquals("Siebel1314", siebelMap.getEndingRoom());
    }

    @Test
    public void rooms() {
        assertEquals("SiebelEntry", siebelMap.getRooms()[1].getName());
    }
}