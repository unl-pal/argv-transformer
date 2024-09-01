package characters;

import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Basic Character Implementation.
 */
@Entity
public class BaseCharacter implements Character {
    private String name;

}
