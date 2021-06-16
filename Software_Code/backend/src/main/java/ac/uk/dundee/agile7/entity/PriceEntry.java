package ac.uk.dundee.agile7.entity;

import javax.persistence.*;

/**
 * Joining table between Procedure and Provider, each entry representing a single row in the input table.
 *
 */
@Entity
public class PriceEntry {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private Procedure procedure;

    @ManyToOne
    @JoinColumn
    private Provider provider;

    private double price;

    public PriceEntry(){}

    public PriceEntry(Provider provider, Procedure procedure, double price) {
        this.provider = provider;
        this.procedure = procedure;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Long getId() {
        return id;
    }
}
