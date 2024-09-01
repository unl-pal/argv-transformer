package characters;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 * Created by stephensanchez on 3/17/15.
 */
public class BaseCharacterService implements CharacterService {

    private EntityManagerFactory entityManagerFactory;
}
