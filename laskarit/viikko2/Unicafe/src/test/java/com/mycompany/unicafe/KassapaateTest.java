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

    @Test
    public void kateisostoToimiiEdullisesti(){
        assertEquals(10, kassapaate.syoEdullisesti(250));
        assertEquals(100240, kassapaate.kassassaRahaa());
    }

    @Test
    public void kateisostoEdullisestiLounaidenMaaraKasvaa(){
        kassapaate.syoEdullisesti(240);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void kateisostoEdullisestiMaksuRiittamaton(){
        assertEquals(230, kassapaate.syoEdullisesti(230));
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void kateisostoToimiiMaukkaasti(){
        assertEquals(10, kassapaate.syoMaukkaasti(410));
        assertEquals(100400, kassapaate.kassassaRahaa());
    }

    @Test
    public void kateisostoMaukkaastiLounaidenMaaraKasvaa(){
        kassapaate.syoMaukkaasti(400);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoMaukkaastiMaksuRiittamaton(){
        assertEquals(390, kassapaate.syoMaukkaasti(390));
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void maksukortinLataaminenToimii(){
        Maksukortti maksukortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(1000, maksukortti.saldo());
        assertEquals(101000, kassapaate.kassassaRahaa());
    }

    @Test
    public void maksukorttiEiLataaNegatiivistaSaldoa(){
        Maksukortti maksukortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(maksukortti, -100);
        assertEquals(0, maksukortti.saldo());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void maksukorttiToimiiEdullisesti(){
        Maksukortti maksukortti = new Maksukortti(1000);
        assertTrue(kassapaate.syoEdullisesti(maksukortti));
        assertEquals(760, maksukortti.saldo());
    }

    @Test
    public void maksukorttiEdullisestiLounaidenMaaraKasvaa(){
        Maksukortti maksukortti = new Maksukortti(240);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maksukorttiEdullisestiMaksuRiittamaton(){
        Maksukortti maksukortti = new Maksukortti(230);
        assertFalse(kassapaate.syoEdullisesti(maksukortti));
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(230, maksukortti.saldo());
    }

    @Test
    public void maksukorttiEdullisestiKassaEiMuutu(){
        Maksukortti maksukortti = new Maksukortti(240);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void maksukorttiToimiiMaukkaasti(){
        Maksukortti maksukortti = new Maksukortti(1000);
        assertTrue(kassapaate.syoMaukkaasti(maksukortti));
        assertEquals(600, maksukortti.saldo());
    }

    @Test
    public void maksukorttiMaukkaastiLounaidenMaaraKasvaa(){
        Maksukortti maksukortti = new Maksukortti(400);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maksukorttiMaukkaastiMaksuRiittamaton(){
        Maksukortti maksukortti = new Maksukortti(200);
        assertFalse(kassapaate.syoMaukkaasti(maksukortti));
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(200, maksukortti.saldo());
    }

    @Test
    public void maksukorttiMaukkaastiKassaEiMuutu(){
        Maksukortti maksukortti = new Maksukortti(400);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

}