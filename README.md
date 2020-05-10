# Ohjelmistotekniikka-harjoitustyö

Tämä repositorio sisältää *Ohjelmistotekniikka*-kurssin harjoitustyön kansiossa [**ProductiveTime**](ProductiveTime) ja laskaritehtävät kansiossa [**laskarit**](laskarit).

## ProductiveTime

⌛ ProductiveTime on ajanhallintaan avuksi tarkoitettu aktiviteettien aikaseurantasovellus eli ns. "time tracker".

## Releaset

Viikko 5: [Ensimmäinen release](https://github.com/Jokauppi/ot-harjoitustyo/releases/tag/v0.9-beta)

Viikko 6: [Toinen release](https://github.com/Jokauppi/ot-harjoitustyo/releases/tag/v0.9.1-beta)

Viikko 7: [Loppupalautus](https://github.com/Jokauppi/ot-harjoitustyo/releases/tag/v1.0)

## Dokumentaatio

[Vaatimusmäärittely](dokumentaatio/vaatimusmäärittely.md)

[Käyttöhjeet](dokumentaatio/käyttöohje.md)

[Arkkitehtuurikuvaus](dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](dokumentaatio/testaus.md)

[Työaikakirjanpito](dokumentaatio/työaikakirjanpito.md)

## Komentorivin toiminnot

Ohjelmiston voi käynnistää suorittamalla hakemistossa **ProductiveTime** komennon

```mvn compile exec:java -Dexec.mainClass=productivetime.main.Main```

tai luomalla ja käynnistämällä .jar-tiedoston

```mvn package```

```java -jar target/ProductiveTime-1.0-SNAPSHOT.jar```

Testien suoritus onnistuu komennolla

```mvn test```

ja testikattavuusraportin luominen komennolla

```mvn test jacoco:report```

Raportti luodaan kansioon

```target/site/jacoco/index.html```

Checkstyle-raportin voi luoda komennolla

```mvn jxr:jxr checkstyle:checkstyle```

ja se luodaan kansioon

```target/site/checkstyle.html```
