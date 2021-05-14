package main.java.hr.java.covidportal.mainFile;

import main.java.hr.java.covidportal.enumeracija.VrijednostiSimptoma;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static main.java.hr.java.covidportal.mainFile.GlavnaDatoteka.*;
import static main.java.hr.java.covidportal.mainFile.MetodeZaDohvacanjeDatotekePomocuParametra.*;

/**
 *Klasa svih pomocnih metoda za unos podataka u datoteke koje se pozivaju prilikom izvrsavanja programa
 */
public class MetodeZaDohvacanjeDatoteka {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static final String newLine = "#newLine#";
    private static final Pattern pattern = Pattern.compile(newLine);
    private static List<String> kontaktiraneOsobe = new ArrayList<>();

    /**
     * Služi za učitavanje datoteke u program, prima objekt klase tipa String, a vraća listu objekata klase tipa String.
     *
     * @param datoteka String vrijednost za slanje podataka u računalo preko datoteke
     * @return List String stvorena nova lista objekata stringova
     */
    private static List<String> ucitajDatoteku(String datoteka) {

        Path putanjaDatoteke = Paths.get(FILE_LOCATION + datoteka);
        Charset encoding = StandardCharsets.UTF_8;
        List<String> listaDatoteka = new ArrayList<>();
        if (Optional.ofNullable(datoteka).isPresent()) {
            try (Stream<String> linije = Files.lines(Path.of(String.valueOf(putanjaDatoteke)), encoding)) {
                listaDatoteka = linije
                        .map(String::trim)
                        .filter(file -> !file.isEmpty())
                        .collect(Collectors.toList());
            } catch (IOException ex) {
                System.err.println("Pogreska kod ucitavanja datoteke " + datoteka + "\n" + ex.getMessage());
                logger.error("Pogreska kod ucitavanja datoteke " + datoteka + "\n" + ex.getMessage());
            }
        } else {
            System.err.println("Nema takve datoteke, ili je pogreska u nazivu datoteke");
            logger.error("Nema takve datoteke, ili je pogreska u nazivu datoteke");
        }
        return listaDatoteka;
    }

    /**
     * Služi za stvaranje liste stringova spajanja stringa liste datoteke u jednu liniju, prima listu objekata klase tipa String, objekt klase tipa String, objekt klase tipa Integer,a vraća listu objekata klase tipa String.
     *
     * @param datotekaAsListOfStr List String slanje podataka datoteke u listi
     * @param delimeterLine String granicna vrijednost oznacava spajanje izmedu linija
     * @param brojLinija Integer cjelobrojna vrijednost oznacava broj linija po zapisu u datoteci
     * @return List String stvorena nova lista objekta stringova od kojih svaki predstavlja jedan objekt, s podacima odvojenim delimeter stringom
     */
    private static List<String> convertFileListStrAsDatInLine(List<String> datotekaAsListOfStr, String delimeterLine, Integer brojLinija) {

        List<String> oneLineFile = new ArrayList<>();
        for (int i = 0; i < datotekaAsListOfStr.size(); i += brojLinija) {

            List<String> linijeZupanije = new ArrayList<>();
            for (int j = 0; j < brojLinija; j++) {
                linijeZupanije.add(datotekaAsListOfStr.get(i + j));
            }
            oneLineFile.add(String.join(delimeterLine, linijeZupanije));
        }
        return oneLineFile;
    }

    /**
     * Služi za stvaranje objekta liste Županija , prima objekt klase tipa String, a vraća listu objekata klase tipa Županija.
     *
     * @param imeDatoteke String naziv datoteke
     * @return List Zupanija stvorena nova lista objekta zupanija
     */
    static List<Zupanija> ucitajZupanije(String imeDatoteke) {

        logger.info("ucitavanje zupanija...");
        List<String> ucitavanje = ucitajDatoteku(imeDatoteke);

        return convertFileListStrAsDatInLine(ucitavanje, newLine, Zupanija.BROJ_LINIJA_PO_ZAPISU)
                .stream()
                .map(line -> {
                    List<String> zupanija = Arrays.asList(pattern.split(line));
                    Long id = Long.parseLong(zupanija.get(0));
                    String naziv = zupanija.get(1);
                    Integer brojStanovnika = Integer.parseInt(zupanija.get(2));
                    Double brojZarazenih = Double.parseDouble(zupanija.get(3));
                    return new Zupanija(id, naziv, brojStanovnika, brojZarazenih);
                }).collect(Collectors.toList());
    }

    /**
     * Služi za stvaranje liste objekta Simptom , prima objekt klase tipa String, a vraća listu objekata klase tipa Stimptom.
     *
     * @param imeDatoteke String naziv datoteke
     * @return List Stimptom stvorena nova lista objekta simptom
     */
    static List<Simptom> ucitajSimptome(String imeDatoteke) {

        logger.info("ucitavanje simptoma...");
        List<String> ucitavanje = ucitajDatoteku(imeDatoteke);

        return convertFileListStrAsDatInLine(ucitavanje, newLine, Simptom.BROJ_LINIJA_PO_ZAPISU)
                .stream()
                .map(line -> {
                    List<String> simptom = Arrays.asList(pattern.split(line));
                    Long id = Long.parseLong(simptom.get(0));
                    String naziv = simptom.get(1);
                    VrijednostiSimptoma vrijednostiSimptoma = MetodeZaDohvacanjeDatotekePomocuParametra.VrijednostiSimptoma(Integer.parseInt(simptom.get(2)));
                    return new Simptom(id, naziv, vrijednostiSimptoma);
                }).collect(Collectors.toList());
    }

    /**
     * Služi za stvaranje liste objekta Bolest , prima objekt klase tipa String, a vraća listu objekata klase tipa Bolest.
     *
     * @param imeDatoteke String naziv datoteke
     * @return List Bolest stvorena nova lista objekta bolest
     */
    static List<Bolest> ucitajBolesti(String imeDatoteke) {

        logger.info("ucitavanje bolesti...");
        List<String> ucitavanje = ucitajDatoteku(imeDatoteke);

        return convertFileListStrAsDatInLine(ucitavanje, newLine, Bolest.BROJ_LINIJA_PO_ZAPISU)
                .stream()
                .map(line -> {
                    List<String> bolest = Arrays.asList(pattern.split(line));
                    Long id = Long.parseLong(bolest.get(0));
                    String naziv = bolest.get(1);
                    Set<Simptom> simptom = (dohvatiListuSimptomaPomocuId(bolest.get(2)));
                    return new Bolest(id, naziv, simptom);
                }).collect(Collectors.toList());
    }

    /**
     * Služi za stvaranje liste objekta Virus, prima objekt klase tipa String, a vraća listu objekata klase tipa Virus.
     *
     * @param imeDatoteke String naziv datoteke
     * @return List Virus stvorena nova lista objekta virus
     */
    static List<Virus> ucitajViruse(String imeDatoteke) {

        logger.info("ucitavanje virusa...");
        List<String> ucitavanje = ucitajDatoteku(imeDatoteke);

        return convertFileListStrAsDatInLine(ucitavanje, newLine, Virus.BROJ_LINIJA_PO_ZAPISU)
                .stream()
                .map(line -> {
                    List<String> virus = Arrays.asList(pattern.split(line));
                    Long id = Long.parseLong(virus.get(0));
                    String naziv = virus.get(1);
                    Set<Simptom> simptom = dohvatiListuSimptomaPomocuId(virus.get(2));
                    return new Virus(id, naziv, simptom);
                }).collect(Collectors.toList());
    }

    /**
     Služi za stvaranje liste objekta Osoba, prima objekt klase tipa String, a vraća listu objekata klase tipa Osoba.
     *
     * @param imeDatoteke String naziv datoteke
     * @return List Osoba stvorena nova lista objekta osoba
     */
    static List<Osoba> ucitajOsobe(String imeDatoteke) {

        logger.info("ucitavanje osoba...");
        List<String> ucitavanje = ucitajDatoteku(imeDatoteke);

        return convertFileListStrAsDatInLine(ucitavanje, newLine, Osoba.BROJ_LINIJA_PO_ZAPISU)
                .stream()
                .map(line -> {
                    List<String> osoba = Arrays.asList(pattern.split(line));
                    Long id = Long.parseLong(osoba.get(0));
                    String ime = osoba.get(1);
                    String prezime = osoba.get(2);
                    Integer starost = Integer.parseInt(osoba.get(3));
                    Zupanija prebivaliste = dohvatiZupanijuPomocuIda(osoba.get(4));
                    Bolest zarazenBolescuVirusom = dohvatiBolestOrVirusPomocuIda(osoba.get(5), Long.parseLong(osoba.get(6)));
                    kontaktiraneOsobe.add(osoba.get(7));
                    return new Osoba.Builder()
                            .id(id)
                            .ime(ime)
                            .prezime(prezime)
                            .starost(starost)
                            .zupanija(prebivaliste)
                            .zarazenBolescu(zarazenBolescuVirusom).build();
                }).collect(Collectors.toList());
    }

    /**
     *Služi za dodavanje kontaktiranih osoba, prima listu objekata klase tipa Osoba, a ne vraća ništa.
     *
     * @param listaOsoba List Osoba lista objekata osoba
     */
    static void dodajKontaktiraneOsobeUListu(List<Osoba> listaOsoba) {

        for (int i = 0; i < listaOsoba.size(); i++) {
            List<Osoba> osobaList = dohvatiListuOsobaPomocuIda(kontaktiraneOsobe.get(i));
            listaOsoba.get(i).setKontaktiraneOsobe(osobaList);

            if (Optional.ofNullable(listaOsoba.get(i).getKontaktiraneOsobe()).isPresent() && (listaOsoba.get(i).getZarazenBolescu()) instanceof Virus virus) {
                for (Osoba osb : osobaList) {
                    virus.prelazakZarazeNaOsobu(osb);
                    if (osb.getZarazenBolescu() instanceof Virus vir) {
                        vir.prelazakZarazeNaOsobu(listaOsoba.get(i));
                    }
                }
            }
        }
    }

    /**
     * Slauži za izračun postotka zaraženosti, prima objekt klase tipa Zupanija, a vraća objekt klase tipa Double.
     *
     * @param zupanija Zupanija objekt klase tipa Zupanija
     * @return Double izračunava postotaka zaraženosti
     */
    private static Double izracunPostotkaZarazenosti(Zupanija zupanija) {

        return (zupanija.getBrojZarazenihOsoba() / zupanija.getBrojStanovnikaZupanije()) * 100;
    }

    /**
     * Sluzi za serijalizaciju.
     */
    static void serijalizirajZupanije() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(GlavnaDatoteka.SERIALIZATION_FILE_NAME))) {

            out.writeObject(listaZupanija.stream()
                    .filter(zupanija -> izracunPostotkaZarazenosti(zupanija) >= 2)
                    .collect(Collectors.toList()));
        } catch (IOException ex) {
            System.err.println("Pogreska: " + ex);
            logger.error("Pogreska kod serijalizacije", ex);
        }
    }

    /**
     * Sluzi za deserijalizaciju
     */
    static void deserijalizirajZupanije() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(GlavnaDatoteka.SERIALIZATION_FILE_NAME))) {

            List<Zupanija> procitanaZupanija = (List<Zupanija>) ois.readObject();
            System.out.println("Podaci o procitanom objektu: ");
            procitanaZupanija.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Pogreska: " + ex);
            logger.error("Pogreska kod deserijalizacije", ex);
        }
    }
}
