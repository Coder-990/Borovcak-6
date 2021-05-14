package main.java.hr.java.covidportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import main.java.hr.java.covidportal.enumeracija.VrijednostiSimptoma;

import java.io.Serializable;

/**
 * Predstavlja model simptom
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Simptom extends ImenovaniEntitet implements Serializable {

    public static final String FILE_SIMPTOMI = "simptomi.txt";
    public static final Integer BROJ_LINIJA_PO_ZAPISU = 3;

    VrijednostiSimptoma vrijednostiSimptoma;

    /**
     * Nasljeduje klasu ImenovaniEntitet. Konstruktor klase Simptom služi za definiranje objeka simptom, nasljeđuje parametar naziv, a prima naziv objkta klase tipa String i vrijednostiSimptoma objkta klase enumeracija tipa VrijednostiSimptoma.
     *
     * @param naziv               String naziv bolesti
     * @param vrijednostiSimptoma Enumeracija predstavlja vrijednost simptoma
     */
    public Simptom(Long id, String naziv, VrijednostiSimptoma vrijednostiSimptoma) {
        super(id, naziv);

        this.vrijednostiSimptoma = vrijednostiSimptoma;
    }

    @Override
    public String toString() {
        return "Simptom{" +
                "naziv='" + naziv + '\'' +
                ", vrijednostiSimptoma=" + vrijednostiSimptoma +
                '}';
    }
}
