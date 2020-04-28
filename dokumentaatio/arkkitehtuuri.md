# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa kerrosarkkitehtuuria, joka sisältää käyttöliittymän luovan
pakkauksen *productivetime.ui*, sovelluslogiikasta vastaavan pakkauksen *productivetime.domain*, ja
aktiviteettien tallennuksesta vastaavan pakkauksen *productivetime.dao*

![Pakettikaavio](/Images/PackageDiagram.png)

## Sovelluksen toiminta

Sovelluksen keskeisin toiminto on lisätä tietokantaan *aktiviteetteja*, joille käyttäjä antaa jokin tyypin. Tämä tyyppi tallennetaan
tietokantaan yhdessä tallentamisajankohdan kanssa. Samalla edellisen aktiviteetin kesto merkitään tietokantaan laskemalla aika sen
alkamishetkestä uuden aktiviteetin alkamishetkeen.

## Käyttöliittymä

Käyttöliittymä koostuu neljästä eri näkymästä:

- aktiviteetin lisäys
- lista menneistä aktiviteeteista
- diagramminäkymä
- asetusnäkymä

Näkymät on toteutettu projektiin luodun [UIElement](https://github.com/Jokauppi/ot-harjoitustyo/blob/master/ProductiveTime/src/main/java/productivetime/ui/UIElement.java)-rajapinnan toteuttavina luokkina, jotka rakentavat näkymän käyttäen apunaan muita pakkaustensa UIElement-luokkia ja sovelluslogiiikkaa. Ylempi luokka pyytää rakennetun näkymän UIElement-luokilta getterin avulla.

Näkymät asetetaan BorderPaneen siihen myös asetetun valitsimen avulla, joka rakentaa näkymää vaihdettaessa näkymän aina uudestaan.

## Sovelluslogiikka

Sovellus pääasiallisesti käsittelee aktiviteetteja Activity-tyyppisinä olioina.

![Activity](/Images/Activity.png)

Olioiden käsittelystä vastaavat ActivityInsertService ja ActivityListService. Sovelluslogiikan kannalta olennaisimpia metodeita ovat addActivity() ja getActivities(). Sovelluksen useiden näkymien johdosta sovelluslogiikassa on useita erilaisia listausmetodeita. Sovelluslogiikka tallentaa ja listaa aktiviteetteja SQLActivityDao-luokan avulla SQLite-tietokantaan.

### Olennaiset toiminnot

Aktiviteetin lisäystä kuvaa seuraava sekvenssikaavio:

![Aktiviteetin lisääminen](/Images/ActivityInsertion.png)

### Luokkarakenne

Sovelluksen keskeinen luokkarakenne ilmenee seuraavasta kaaviosta. Sovelluksen UI-pakkauksesta on sisällytetty kaavioon vain
näkymänvaihdin *ViewSelector* ja keskeiset näkymät *ActivityInsertionLayout* ja *ActivityListLayout*.

![Luokkakaavio](/Images/ClassDiagram.png)

## Tiedon tallennus

Sovellus käyttää aktiviteettien pysyväistallennukseen Data Access Object -mallin toteuttavaa tietokantaluokkaa SQLActivityDao. SQL-kielenä luokka käyttää SQLiteä.

Sovelluksen konfiguraatiotiedot tallennetaan tietokoneen kotikansioon luotavaan .ProductiveTime-kansioon config.properties-tiedostona. Myös Tietokanta tallennetaan tähän kansioon.