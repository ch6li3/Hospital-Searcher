package ac.uk.dundee.agile7.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Database entity to store a Medical Procedure - just the name.
 */
@Entity
public class Procedure {
    @Id
    @GeneratedValue

    private Long id;
    private String name;
    public Procedure(){}

    public Procedure(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public Long getId(){
        return id;
    }


}
