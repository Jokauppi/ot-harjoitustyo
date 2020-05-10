# Testausdokumentti

Sovelluksen logiikan ja tietokannan testaus on toteutettu JUnit-kirjaston avulla yksikkö- ja integraatiotesteinä. Käyttöliittymän testaaminen on jätetty automaattisten testien ulkopuolelle, mutta sitä on testattu manuaalisesti.

## Yksikkö- ja integraatiotestaus

### Tietokanta

Sovelluksen SQLite-tietokantaa käyttävän SQLActivityDao-luokan metodeita testattiin yksikkötesteillä lisäämällä tietokantaa aktiviteetteja ja testaamalla sitten luokan muiden metodien toimintaa. Testauksessa käytettiin test.db -testitietokantaa.

### Sovelluslogiikka

#### Activity

Aktiviteettia kuvaavan luokan testaamiseen käytettiin yksikkötestejä. Suurin osa luokan metodeista on suhteellisen yksinkertaisia gettereitä, mutta se sisältää myö pari monimutkaisempaa metodia. 

#### ActivityInsertService ja ActivityListService

Aktiviteetteja käsitteleviä Service-luokkia testattiin integraatiotestien avulla yhdessä SQLActivityDao-luokan kanssa.

ActivityInsertServicen testaus suoritettiin käyttämällä ensin luokan metodeita ja tarkistamalla tietokantaluokan metodien avulla, että tietokanta sisälsi halutut tiedot.

ActivityListServicen testaus suoritettiin päinvaistoin tallentamalla ensin aktiviteetteja tietokantaluokan metodien avulla ja kutsumalla sitten ActivityListServicen metodeita ja tarkistamalla listojen oikeellisuus. Luokan testit on jaettu kolmeen eri tiedostoon kahden metodin erilaisen testausainseiston vuoksi. Luokissa on hyvin paljon erilaisia poikkeustapauksia ja haaroja ja nämä kaikki on pyritty testeissä kattamaan.

#### TimeService

Suurin osa TimeService-luokan metodeista on suhteellisen yksinkertaisia ja hankalia/turhia testata, joten luokasta on testattu vain paria metodia. TimeService on kuitenkin mukana sovelluslogiikan integraatiotesteissä.

#### Settings

Settings-luokkaa ei testata yksikkötesteillä, mutta se on mukana itegraatiotesteissä TimeService-luokan kautta.

#### CSVExport

CSVExport-luokka oli hyvin myöhäinen lisäys sovellukseen, joten testaamiseen ei enää jäänyt aikaa. Luokan yksi ja ainoa metodi on kuitenkin helppo testata käyttöliittymän manuaalisissa testeissä eikä luokan toimimattomuus voi aiheuttaa sovelluksen muulle toiminnalle haittaa.

### Testauskattavuus

Sovelluksen testien rivikattavuus on % ja haarautumakattavuus %. Testaamatta on jäänyt muutamia sovelluksen toiminnan kannalta vähäpäitöisempiä metodeita ja metodien haaroja, joissa SQLite ei toimi oikein ja heittää poikkeuksen.

[!IMAGE]()

## Järjestelmätestaus

Sovelluksen käyttöliittymää on testattu manuaalisesti. Olemassa olevien konfiguraatio- ja tietokantatiedostojen hakemista ja puuttuvien tiedostojen luomista on testattu.

Kaikki määrittelydokumentissa ilmaistut perusominaisuudet sekä jatkokehitysideoiden CSV-vienti on toteutettu ja testattu. Tekstikentät eivät hyväksy liian suuria tai tyhjiä syötteitä. Päiväkohtainen diagramminäkymä kuitenkin aikaistaa osaa aktiviteeteista, mikäli valitun päivän aikana on käytetty seurannan keskeytystoimintoa. Virheen korjaaminen olisi vaatinut uuden diagrammiluokan kirjoittamista, mikä ei enää ehtinyt loppupalautukseen.

## Virheilmotukset

Sovellus antaa ilmenneistä poikkeuksista pääsääntöisesti virheilmoituksia. Mahdollisimpia poikkeuksia on, että tietokanta poistetaan sovelluksen ollessa käynnissä tai tietokantaa tarkastellaan samanaikaisesti sovelluksen ulkopuolelta.