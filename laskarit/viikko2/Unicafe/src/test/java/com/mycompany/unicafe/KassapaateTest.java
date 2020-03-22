package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kassapaate;

    @Before
    public void setUp() throws Exception {
        kassapaate = new Kassapaate();
    }

    @Test
    public void konstruktoriToimii(){
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty()+kassapaate.maukkaitaLounaitaMyyty());
    }
}