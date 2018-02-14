package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class GameOnTest {
    String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json ";
    Layout siebelMap;
    static URLget work = new URLget ( );

    @Before
    public void setUp() throws Exception {
        Gson fileReader = new Gson ( );
        siebelMap = fileReader.fromJson (work.getFileContentsAsString ("a.json"), Layout.class);
    }

    @Test
    public void startingRoom() {
        assertEquals ("MatthewsStreet", siebelMap.getStartingRoom ( ));
    }

    @Test
    public void endingRoom() {
        assertEquals ("Siebel1314", siebelMap.getEndingRoom ( ));
    }

    @Test
    public void rooms() {
        assertEquals ("SiebelEntry", siebelMap.getRooms ( )[1].getName ( ));
    }

    @Test
    public void roomDescription() {
        assertEquals ("You are in Siebel 1112.  There is space for two code reviews in this room.", siebelMap.getRooms ( )[4].getDescription ( ));
    }

    @Test
    public void roomItems() {
        assertEquals (null, siebelMap.getRooms ( )[3].getItems ( ));
    }

    @Test
    public void roomItems1() {
        assertEquals ("bagel", siebelMap.getRooms ( )[5].getItems ( ).get (0).getName ( ));
    }

    @Test
    public void roomDirections() {
        assertEquals ("West", siebelMap.getRooms ( )[5].getDirections ( )[0].getDirectionName ( ));
    }

    @Test
    public void roomInDirection() {
        assertEquals ("SiebelNorthHallway", siebelMap.getRooms ( )[4].getDirections ( )[0].getRoom ( ));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void invalidRoom() {
        assertEquals (18, siebelMap.getRooms ( )[121212]);
    }

    @Test
    public void monsterName() {
        assertEquals (2, siebelMap.getRooms ( )[0].getMonstersInRoom ( ).size ( ));
    }

    @Test
    public void duelTest() {

        assertEquals (1, siebelMap.getRooms ()[0].getMonstersInRoom ().get (0).getDefense (), 0.01);


    }
    @Test
    public void duelHealth() {

        assertEquals (1,siebelMap.getPlayer ()[0].getHealth ());


    }
}

