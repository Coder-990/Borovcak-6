package main.java.hr.java.covidportal.mainFile;

import main.java.hr.java.covidportal.enumeracija.VrijednostiSimptoma;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;

import java.util.*;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.mainFile.GlavnaDatoteka.*;

/**
 *
 */
public class MetodeZaDohvacanjeDatotekePomocuParametra {

    /**
     * @param vrijednost
     * @return
     */
    static VrijednostiSimptoma VrijednostiSimptoma(Integer vrijednost) {
        return switch (vrijednost) {
            case 1 -> VrijednostiSimptoma.RIJETKO;
            case 2 -> VrijednostiSimptoma.SREDNJE;
            case 3 -> VrijednostiSimptoma.CESTO;
            default -> VrijednostiSimptoma.NIJE_ODREDENO;
        };
    }

    /**
     * @param splitStr
     * @return
     */
    static Zupanija dohvatiZupanijuPomocuIda(String splitStr) {

        return listaZupanija.stream()
                .filter(zupanija -> zupanija.getId()
                        .equals(Long.parseLong(splitStr)))
                .findFirst().orElse(null);
    }

    /**
     * @param splitStr
     * @return
     */
    static Set<Simptom> dohvatiListuSimptomaPomocuId(String splitStr) {

        List<String> simptomId = Arrays.asList(splitStr.split(","));
        return simptomId.stream()
                .map(id -> dohvatiSimptomPomocuIda(Long.parseLong(id))
                        .orElse(null))
                .collect(Collectors.toSet());
    }

    /**
     * @param splitStr
     * @return
     */
//    static Bolest dohvatiListuBolestiOrVirusaPomocuIda(String splitStr) {
//
//        return listaBolesti.stream()
//                .filter(bolest -> bolest.getId().equals(Long.parseLong(splitStr)))
//                .findFirst()
//                .orElse(null);
//    }

    static List<Osoba> dohvatiListuOsobaPomocuIda(String splitStr) {

        if (splitStr.toLowerCase().equals("nema")) {
            return new ArrayList<>();
        }
        List<String> osobeId = Arrays.asList(splitStr.split(","));
        return osobeId.stream()
                .map(id -> dohvatiOsobuPomocuIda(Long.parseLong(id))
                        .orElse(null))
                .collect(Collectors.toList());
    }

    /**
     * @param id
     * @return
     */
    private static Optional<Zupanija> dohvatiZupanijuPomocuIda(Long id) {

        return listaZupanija.stream()
                .filter(zupanija -> zupanija.getId().equals(id))
                .findFirst();
    }

    /**
     * @param id
     * @return
     */
    private static Optional<Simptom> dohvatiSimptomPomocuIda(Long id) {

        return listaSimptoma.stream()
                .filter(simptom -> simptom.getId().equals(id))
                .findFirst();
    }

    /**
     * @param id
     * @return
     */
    static Bolest dohvatiBolestOrVirusPomocuIda(String bolestOrVirus, Long id) {

        if(bolestOrVirus.toLowerCase().equals("bolest")){
            return listaBolesti.stream()
                    .filter(bolest -> bolest.getId().equals(id))
                    .findFirst().orElse(null);
        }else {
            return listaVirusa.stream()
                    .filter(virus-> virus.getId().equals(id))
                    .map(v -> (Bolest) v)
                    .findFirst().orElse(null);
        }
    }

    /**
     * @param id
     * @return
     */
    private static Optional<Osoba> dohvatiOsobuPomocuIda(Long id) {

        return listaOsoba.stream()
                .filter(osoba -> osoba.getId().equals(id))
                .findFirst();
    }
}
