package main.java.hr.java.covidportal.model;

import lombok.*;

import java.io.Serializable;

/**
 * Predstavlja model Županija
 */
@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Zupanija extends ImenovaniEntitet implements Serializable {

    public static final String FILE_ZUPANIJE = "zupanije.txt";
    public static final Integer BROJ_LINIJA_PO_ZAPISU = 4;

    Integer brojStanovnikaZupanije;
    Double brojZarazenihOsoba;

    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Županija služi za definiranje objeka zupanija, nasljeđuje parametar: naziv, a prima naziv objkta klase tipa String, broj stanovnika po županiji objeta klase tipa Integer i broj zaraženih osoba po zupaniji objekta klase tipa Double.
     *
     * @param naziv                  String naziv županije
     * @param brojStanovnikaZupanije Integer broj stanovnika po županiji
     * @param brojZarazenihOsoba     Double broj zarazenih osoba po zupaniji
     */
    public Zupanija(Long id, String naziv, Integer brojStanovnikaZupanije, Double brojZarazenihOsoba) {
        super(id, naziv);

        this.brojStanovnikaZupanije = brojStanovnikaZupanije;
        this.brojZarazenihOsoba = brojZarazenihOsoba;
    }

    @Override
    public String toString() {
        return "\nZupanija{" + "\n" +
                "naziv='" + naziv + '\'' + "\n" +
                "brojStanovnikaZupanije=" + brojStanovnikaZupanije + "\n" +
                "brojZarazenihOsoba=" + brojZarazenihOsoba +
                '}';
    }
}
