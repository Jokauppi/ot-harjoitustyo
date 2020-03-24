# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen päätarkoituksena on pitää kirjaa eri aktiviteetteihin kulutetusta ajasta ja nähdä tilastoja käytetystä ajasta erilaisina kaavioina. Aktiviteetit ja niiden alkamisajat sekä kestot on tarkoitus tallentaa tietokoneen muistissa sijaitsevaan tietokantaan.

## Käyttäjät

Sovelluksella on vain yksi käyttäjätyyppi, joka on *normaali käyttäjä*.

## Perustoiminnalisuus

### Päänäkymä

- Sovelluksen päänäkymässä ainoana pääasiallisena toimintona on kirjata uusi aktiviteetti
    - Aktiviteetin alkamishetkeksi asetetaan aika kirjaushetkellä
    - Samalla edellinen aktiviteetti "lopetetaan" kirjaamalla sen kesto nykyiseen hetkeen
- Käyttäjä voi myös lisätä usein käytettyjä aktiviteetteja muistiin, jotta niitä ei aina tarvitse kirjoittaa uusiksi

### Aktiviteettinäkymä

- Aktiviteettinäkymässä aiempia aktiviteetteja voi selata ja niiden tyyppiä voi muuttaa.
    - Aktiviteettien aikoja ei kuitenkaan voi muuttaa.
  
### Tilastonäkymä

- Kolmannessa näkymässä on mahdollista nähdä kaavioita ajankäytöstä
    - Yhtenä kaaviotyyppinä voisi olla eri aktiviteetteihin käytetty aika yhtenä tämänhetkisenä päivänä tai valitun aikavälin päivinä pylväsdiagrammina. 
    - Toisena kaaviotyyppinä voisi olla valittuihin aktiviteetteihin päivässä käytetty aika ajan saatossa viivadiagrammina.
  
## Jatkokehitysideoita

Perustoiminnallisuuksien lisäksi voitaisiin ajan salliessa lisätä mahdollisuus

- jakaa aktiviteetti kahteen eri tyyppiä olevaan  osaan, mikäli aktiviteetin aikana onkin tehty myös jotain muuta.
- kirjoittaa aktiviteeteille lisätietoja ja antaa niille omat väritunnisteet.
- viedä kerätty data esimerkiksi .csv-tiedostona
- liittää aktiviteetteihin tunnisteita eli tageja
