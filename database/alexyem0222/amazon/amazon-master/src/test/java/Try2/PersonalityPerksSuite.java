package Try2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;



import Try2.PersonalityAssertLinks;
import Try2.PersonalityPerks1;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PersonalityAssertLinks.class,
        PersonalityPerks1.class,
        Packages.class,
        RegisteredUserLogin.class,
        WrongPassword.class,
        EmailPresentInSystem.class

})

public class PersonalityPerksSuite {
}
