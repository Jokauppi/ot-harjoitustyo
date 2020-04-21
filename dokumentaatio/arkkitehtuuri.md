# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattaa kerrosarkkitehtuuria, joka sisältää käyttöliittymän luovan
pakkauksen *productivetime.ui*, sovelluslogiikasta vastaavan pakkauksen *productivetime.domain*, ja
aktiviteettien tallennuksesta vastaavan pakkauksen *productivetime.dao*


## Luokkakaavio

Sovelluksen keskeinen luokkarakenne ilmenee seuraavasta kaaviosta. Sovelluksen UI-pakkauksesta on sisällytetty kaavioon vain
näkymänvaihdin *ViewSelector* ja keskeiset näkymät *ActivityInsertionLayout* ja *ActivityListLayout*.

![Luokkakaavio](/Images/ClassDiagram.png)

## Sovelluksen toiminta

Sovelluksen keskeisin toiminto on lisätä tietokantaan *aktiviteetteja*, joille käyttäjä antaa jokin tyypin. Tämä tyyppi tallennetaan
tietokantaan yhdessä tallentamisajankohdan kanssa. Samalla edellisen aktiviteetin kesto merkitään tietokantaan laskemalla aika sen
alkamishetkestä uuden aktiviteetin alkamishetkeen. Aktiviteetin lisäystä kuvaa seuraava sekvenssikaavio:

![Aktiviteetin lisääminen](/Images/ActivityInsertion.png)
