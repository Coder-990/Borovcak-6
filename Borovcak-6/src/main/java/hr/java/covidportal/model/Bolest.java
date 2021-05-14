package main.java.hr.java.covidportal.model;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * Predstavlja model bolest
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bolest extends ImenovaniEntitet implements Serializable {

    public static final String FILE_BOLESTI = "bolesti.txt";
    public static final Integer BROJ_LINIJA_PO_ZAPISU = 3;

    Set<Simptom> simptomi;

    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Bolest služi za definiranje objeka bolest, nasljeđuje parametar naziv objekata klase tipa String, a prima naziv objkta klase tipa String i set objeta klase tipa Simptom.
     *
     * @param naziv    String naziv bolesti
     * @param simptomi Set Simptom set simptoma
     */
    public Bolest(Long id, String naziv, Set<Simptom> simptomi) {
        super(id, naziv);
        this.simptomi = simptomi;
    }

    @Override
    public String toString() {
        return "\nBolest{" + "\n" +
                "naziv='" + naziv + '\'' + "\n" +
                ",simptomi=" + simptomi + '\n' +
                '}';
    }
}
