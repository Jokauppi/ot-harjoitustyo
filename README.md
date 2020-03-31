# Ohjelmistotekniikka-harjoitustyö

Tämä repositorio sisältää *Ohjelmistotekniikka*-kurssin harjoitustyön kansiossa [**ProductiveTime**](ProductiveTime) ja laskaritehtävät kansiossa [**laskarit**](laskarit).

## Dokumentaatio

[Vaatimusmäärittely](dokumentointi/vaatimusmäärittely.md)

[Työaikakirjanpito](dokumentointi/työaikakirjanpito.md)

## Komentorivin toiminnot

Ohjelmiston voi käynnistää suorittamalla hakemistossa **ProductiveTime** komennon

```mvn compile exec:java -Dexec.mainClass=productivetime.Main```

tai luomalla ja käynnistämällä .jar-tiedoston

```mvn package```

```java -jar target/ProductiveTime-1.0-SNAPSHOT.jar```

Testien suoritus onnistuu komennolla

```mvn test```

ja testikattavuusraportin luominen komennolla

```mvn test jacoco:report```

Raportti luodaan kansioon

```target/site/jacoco/index.html```
