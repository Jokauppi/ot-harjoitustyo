package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    @Test
    public void lataaRahaaToimii(){
        kortti.lataaRahaa(10);
        assertEquals(20, kortti.saldo());
    }

    @Test
    public void otaRahaaToimii(){
        kortti.otaRahaa(5);
        assertEquals(5, kortti.saldo());
    }

    @Test
    public void otaRahaaEiVeloitaYliSaldon(){
        kortti.otaRahaa(50);
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void toStringToimii(){
        assertEquals("saldo: 0.10", kortti.toString());
    }
}
