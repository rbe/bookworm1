package eu.artofcoding.bookworm.common.persistence.basket;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Abrufkennwort implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String abrufkennwort;

    public Abrufkennwort() {
    }

    public Abrufkennwort(final String abrufkennwort) {
        this.abrufkennwort = abrufkennwort;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getAbrufkennwort() {
        return abrufkennwort;
    }

    public void setAbrufkennwort(final String aghNummer) {
        this.abrufkennwort = aghNummer;
    }

}
