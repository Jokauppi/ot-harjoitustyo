# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen päätarkoituksena on pitää kirjaa eri aktiviteetteihin kulutetusta ajasta ja nähdä tilastoja käytetystä ajasta erilaisina kaavioina. Aktiviteetit ja niiden alkamisajat sekä kestot on tarkoitus tallentaa tietokoneen muistissa sijaitsevaan tietokantaan.

## Käyttäjät

Sovelluksella on vain yksi käyttäjätyyppi, joka on *normaali käyttäjä*.

## Perustoiminnalisuus

### Päänäkymä

- Sovelluksen päänäkymässä ainoana pääasiallisena toimintona on kirjata uusi aktiviteetti **TEHTY**
    - Aktiviteetin alkamishetkeksi asetetaan aika kirjaushetkellä
    - Samalla edellinen aktiviteetti "lopetetaan" kirjaamalla sen kesto nykyiseen hetkeen
- Käyttäjä näytetään pikavalintoina kymmenen käytetyintä aktiviteettia, jotta niitä ei aina tarvitse kirjoittaa uusiksi **TEHTY**

### Aktiviteettinäkymä

- Aktiviteettinäkymässä aiempia aktiviteetteja voi selata ja niiden tyyppiä voi muuttaa. **TEHTY**
    - Aktiviteettien aikoja ei kuitenkaan voi muuttaa.
  
### Tilastonäkymä

- Kolmannessa näkymässä on mahdollista nähdä kaavioita ajankäytöstä **TEHTY**
    - Yhtenä kaaviotyyppinä voisi olla eri aktiviteetteihin käytetty aika yhtenä tämänhetkisenä päivänä tai valitun aikavälin päivinä pylväsdiagrammina. **TEHTY**
    - Toisena kaaviotyyppinä voisi olla valittuihin aktiviteetteihin päivässä käytetty aika ajan saatossa viivadiagrammina. **TEHTY**
    
### Asetukset

- Ohjelmistossa olisi myös mahdollista muuttaa joitain asetuksia kuten
    - aikavyöhyke **TEHTY**
    - ~~listanäkymässä näytettävien sarakkeiden näyttäminen (kesto ja alku- sekä loppumisaika)~~ 
    - tietokannan tyhjentäminen **TEHTY**
    - ~~aikaformaatin muuttaminen~~
    - aikaseurannan väliaikainen kekeyttäminen **TEHTY**
    
## Käyttöliittymä

Konsepti käyttöliittymästä:

![Konsepti](/Images/prodtime.png)
  
## Jatkokehitysideoita

Perustoiminnallisuuksien lisäksi voitaisiin ajan salliessa lisätä mahdollisuus

- ~~jakaa aktiviteetti kahteen eri tyyppiä olevaan  osaan, mikäli aktiviteetin aikana onkin tehty myös jotain muuta.~~ Mahdollista toteuttaa, ei kuitenkaan sovi kunnolla käyttöliittymään 
- kirjoittaa aktiviteeteille lisätietoja ja antaa niille omat väritunnisteet. **idea hylätty ajan puuttessa**
- viedä kerätty data esimerkiksi .csv-tiedostona **TEHTY**
- liittää aktiviteetteihin tunnisteita eli tageja. **idea hylätty ajan puuttessa**
