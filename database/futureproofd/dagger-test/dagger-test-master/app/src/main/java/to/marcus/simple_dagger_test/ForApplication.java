package to.marcus.simple_dagger_test;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import javax.inject.Qualifier;

/**
 * Created by marcus on 23/03/15.
 */

@Qualifier @Retention(RUNTIME)
    public @interface ForApplication {
}
