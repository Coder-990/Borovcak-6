package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import main.java.hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.main.MetodeZaIspis.*;
import static main.java.hr.java.covidportal.main.MetodeZaValidaciju.*;

/**
 * Glavna klasa za, unos podataka i ispis podataka.
 */
public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    protected static int BROJ_ZUPANIJA;
    protected static int BROJ_SIMPTOMA;
    protected static int BROJ_BOLESTI;
    protected static int BROJ_OSOBA;

    /**
     * Glavna metoda za izvršavanje programa, u kojoj se radi unos podataka i pozivaju
     * dodatne druge metode.
     *
     * @param args ulazni parametri kod pokretanja glavne metode
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        KlinikaZaInfektivneBolesti<Virus, Osoba> klinikaZaInfektivneBolesti = new KlinikaZaInfektivneBolesti<>();

        Set<Zupanija> zupanije = new HashSet<>();
        Set<Simptom> simptomi = new HashSet<>();
        Set<Bolest> bolesti = new HashSet<>();
        List<Osoba> osobe = new ArrayList<>();


        Map<Bolest, List<Osoba>> bolestiOsobaMap = new HashMap<>();


        System.out.print("Unesite broj zupanija: ");
        BROJ_ZUPANIJA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_ZUPANIJA + " zupanija!");
        for (int i = 0; i < BROJ_ZUPANIJA; i++) {
            zupanije.add(MetodeZaUnosPodataka.unosZupanije(scan, i));
        }

        System.out.print("Unesite broj simptoma: ");
        BROJ_SIMPTOMA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_SIMPTOMA + " simptoma!");
        for (int i = 0; i < BROJ_SIMPTOMA; i++) {
            simptomi.add(MetodeZaUnosPodataka.unosSimptoma(scan, i));
        }

        System.out.print("Unesite broj bolesti: ");
        BROJ_BOLESTI = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_BOLESTI + " bolesti!");
        for (int i = 0; i < BROJ_BOLESTI; i++) {
            hvatanjeNeoznaceneIznimkeBolestIstihSimptoma(scan, simptomi, bolesti, klinikaZaInfektivneBolesti, i);
        }

        System.out.print("Unesite broj osoba: ");
        BROJ_OSOBA = IntegerExHandler(scan);
        System.out.println("Unesite podatke o " + BROJ_OSOBA + " osobe!");
        for (int i = 0; i < BROJ_OSOBA; i++) {
            hvatanjeOznaceneIznimkePonovljenaOsobaKodUnosa(scan, zupanije, bolesti, osobe, bolestiOsobaMap, klinikaZaInfektivneBolesti, i);
        }


        System.out.println("Popis osoba: ");
        for (Osoba osoba : osobe) {
            System.out.println(osoba.toString());
        }

        ispisMapomBolestiOsoba(zupanije, bolestiOsobaMap);


        Instant startTimeLambda = Instant.now();
        sortiraniVirusiLambdom(klinikaZaInfektivneBolesti);
        Instant stopTimeLambda = Instant.now();
        System.out.println("Vrijeme sortiranja lambdom : " + Duration.between(startTimeLambda, stopTimeLambda).toNanos()+" nanos");


        Instant startTime = Instant.now();
        sortiraniVirusiBezLambde(klinikaZaInfektivneBolesti);
        Instant stopTime = Instant.now();
        System.out.println("Sortiranje bez lambdi : " + Duration.between(startTime, stopTime).toNanos()+" nanos");


        Optional<List<Osoba>> ispisPronadenihOsobaPoPrezimenu = pronadiOsobuPoPrezimenu(scan, osobe);
        if (ispisPronadenihOsobaPoPrezimenu.isPresent() && !ispisPronadenihOsobaPoPrezimenu.get().isEmpty()) {
            ispisPronadenihOsobaPoPrezimenu.get().forEach(System.out::println);
        } else {
            System.out.println("Nema pronadenih osoba");
        }

        System.out.println("Broj simptoma bolesti: ");
        List<String> brojSimptomabolesti = bolesti.stream().map(bolest -> bolest.getNaziv() + ":" + bolest.getSimptomi().size()).collect(Collectors.toList());
        brojSimptomabolesti.forEach(System.out::println);

        scan.close();
    }
}
