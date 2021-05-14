package main.java.hr.java.covidportal.mainFile;

import main.java.hr.java.covidportal.model.*;

import java.util.List;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.mainFile.MetodeZaDohvacanjeDatoteka.*;
import static main.java.hr.java.covidportal.model.Bolest.FILE_BOLESTI;
import static main.java.hr.java.covidportal.model.Osoba.FILE_OSOBE;
import static main.java.hr.java.covidportal.model.Simptom.FILE_SIMPTOMI;
import static main.java.hr.java.covidportal.model.Virus.FILE_VIRUSI;
import static main.java.hr.java.covidportal.model.Zupanija.FILE_ZUPANIJE;

/**
 *  Glavna klasa za, unos podataka i ispis podataka u datoteku.
 */
public class GlavnaDatoteka {

    public static final String FILE_LOCATION = "dat/";
    public static final String SERIALIZATION_FILE_NAME = "dat/serijalizacijaZupanija.dat";

    protected static List<Zupanija> listaZupanija;
    protected static List<Simptom> listaSimptoma;
    protected static List<Bolest> listaBolesti;
    protected static List<Virus> listaVirusa;
    protected static List<Osoba> listaOsoba;

    /**
     * Glavna metoda za izvršavanje programa, u kojoj se radi unos podataka i pozivaju
     * dodatne druge metode.
     *
     * @param args ulazni parametri kod pokretanja glavne metode
     */
    public static void main(String[] args) {


        System.out.println("Ucitavanje datoteke '" + FILE_ZUPANIJE + "'...");
        listaZupanija = ucitajZupanije(FILE_ZUPANIJE);

        System.out.println("Ucitavanje datoteke '" + FILE_SIMPTOMI + "'...");
        listaSimptoma = ucitajSimptome(FILE_SIMPTOMI);

        System.out.println("Ucitavanje datoteke '" + FILE_BOLESTI + "'...");
        listaBolesti = ucitajBolesti(FILE_BOLESTI);

        System.out.println("Ucitavanje datoteke '" + FILE_VIRUSI + "'...");
        listaVirusa = ucitajViruse(FILE_VIRUSI);

        System.out.println("Ucitavanje datoteke '" + FILE_OSOBE + "'...");
        listaOsoba = ucitajOsobe(FILE_OSOBE);

        dodajKontaktiraneOsobeUListu(listaOsoba);

        serijalizirajZupanije();

        listaZupanija.forEach(System.out::println);
        System.out.println();
        listaSimptoma.forEach(System.out::println);
        System.out.println();
        listaBolesti.forEach(System.out::println);
        System.out.println();
        listaVirusa.forEach(System.out::println);
        System.out.println();
        listaOsoba.forEach(System.out::println);
        System.out.println();

        deserijalizirajZupanije();
    }


}
