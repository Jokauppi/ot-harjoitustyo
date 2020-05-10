# Arkkitehtuurikuvaus

## Rakenne

Ohjelma on rakennettu kerrosarkkitehtuuria noudattaen, sillä se sisältää käyttöliittymän luovan
pakkauksen *productivetime.ui*, sovelluslogiikasta vastaavan pakkauksen *productivetime.domain*, ja
aktiviteettien tallennuksesta vastaavan pakkauksen *productivetime.dao* Lisäksi käyttöliittymäpakkaus sisältää muita pienempiä pakkauksia, joihin on jaettu eri näkymiin liittyviä luokkia. 

![Pakettikaavio](/Images/Architecture/PackageDiagram.png)

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

Näkymät asetetaan BorderPaneen sen alalaitaan asetetun, [UISelectorElement](https://github.com/Jokauppi/ot-harjoitustyo/blob/master/ProductiveTime/src/main/java/productivetime/ui/UISelectorElement.java)-rajapinnan toteuttavan valitsimen avulla, joka rakentaa näkymää vaihdettaessa näkymän aina uudestaan.

## Sovelluslogiikka

Sovellus pääasiallisesti käsittelee aktiviteetteja Activity-tyyppisinä olioina. Activity-olioilla on useita metodeita, joka antavat tietoa aktiviteetista halutussa muodossa esimerkiksi aktiviteetin keston ajaksi muotoiltuna merkkijonona.

![Activity](/Images/Architecture/Activity.png)

Olioiden käsittelystä vastaavat ActivityInsertService ja ActivityListService. Sovelluslogiikan kannalta olennaisimpia metodeita ovat addActivity() ja getActivities(). Sovelluksen useiden näkymien johdosta sovelluslogiikassa on useita erilaisia listausmetodeita. Sovelluslogiikka tallentaa ja listaa aktiviteetteja SQLActivityDao-luokan avulla SQLite-tietokantaan. Service- ja tietokantaoliot luodaan sovellusta käynnistettäessä ja Service-olioita injektoidaan eteenpäin niitä tarvitseville luokille.

### Olennaiset toiminnot

Aktiviteetin lisäystä kuvaa seuraava sekvenssikaavio:

![Aktiviteetin lisääminen](/Images/Architecture/ActivityInsertion.png)

### Luokkarakenne

Sovelluksen keskeinen luokkarakenne ilmenee seuraavasta kaaviosta. Sovelluksen UI-pakkauksesta on sisällytetty kaavioon vain
näkymänvaihdin *ViewSelector* ja keskeiset näkymät *ActivityInsertionLayout* ja *ActivityListLayout*.

![Luokkakaavio](/Images/Architecture/ClassDiagram.png)

## Tiedon tallennus

Sovellus käyttää aktiviteettien pysyväistallennukseen Data Access Object -mallin toteuttavaa tietokantaluokkaa SQLActivityDao. SQL-kielenä luokka käyttää SQLiteä.

Sovelluksen konfiguraatiotiedot tallennetaan tietokoneen kotikansioon luotavaan .ProductiveTime-kansioon config.properties-tiedostona. Myös Tietokanta tallennetaan tähän kansioon.

Sovelluksen asetuksista on myös mahdollista viedä tietokantaan tallennetut tiedot csv-tiedostoon. Tallentamiseen käytetään OpenCSV-kirjastoa. Tallentaminen suoritetaan kokonaan sovelluslogiikan luokassa, sillä luokalla ei ole projektin DAO-rajapinnan vaatimia metodeita vaan ainoastaan tallennus.