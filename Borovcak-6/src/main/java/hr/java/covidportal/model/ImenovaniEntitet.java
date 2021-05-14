package main.java.hr.java.covidportal.model;

import lombok.*;

import java.io.Serializable;

/**
 * Apstrakna klasa koju nasljeđuje svaka klasa koja prima parametar objekt klase tipa String
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ImenovaniEntitet implements Serializable {

    Long id;
    String naziv;
}
