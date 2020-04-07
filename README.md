# Ohjelmistotekniikka-harjoitustyö

Tämä repositorio sisältää *Ohjelmistotekniikka*-kurssin harjoitustyön kansiossa [**ProductiveTime**](ProductiveTime) ja laskaritehtävät kansiossa [**laskarit**](laskarit).

## Dokumentaatio

[Vaatimusmäärittely](dokumentaatio/vaatimusmäärittely.md)

[Työaikakirjanpito](dokumentaatio/työaikakirjanpito.md)

[Arkkitehtuurikuvaus](dokumentaatio/arkkitehtuuri.md)

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

Checkstyle-raportin voi luoda komennolla

```mvn jxr:jxr checkstyle:checkstyle```

ja se luodaan kansioon

```target/site/checkstyle.html```
