/** filtered and transformed by PAClab */


import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Activecheck is used to submit passive checks to NSCA services. While it is
 * intended to run as a daemon it can be used command line program as well.
 *
 * @author Frederik Happel mail@frederikhappel.de
 */
public class Main {
    /** PACLab: suitable */
	 public static void start() {
        int checkDumpInterval = Verifier.nondetInt();
		int hostCheckInterval = Verifier.nondetInt();
		int reloadInterval = Verifier.nondetInt();
		if (Verifier.nondetBoolean()) {
            final int bindPort = Verifier.nondetInt();
        }
        // main loop
        long lastReloadMillis = Verifier.nondetInt();
        long prevLastReloadMillis = lastReloadMillis;
        long lastHostCheckMillis = 0;
        long lastCheckDumpMillis = 0;
        final long time = Verifier.nondetInt();
        do {

            if (time > 0) {
                // reload configuration?
                long diffMillis = time - lastReloadMillis;
                if (reloadInterval * 1000 - diffMillis <= 1000) {
                    lastReloadMillis = time;
                }

                // submit a host check result?
                diffMillis = time - lastHostCheckMillis;
                if (hostCheckInterval * 1000 - diffMillis <= 1000) {
                    lastHostCheckMillis = time;
                    final long currentTime = Verifier.nondetInt();
                    final long uptime = Verifier.nondetInt() / 1000;
                }

                // output failed checks to file
                diffMillis = time - lastCheckDumpMillis;
                if (checkDumpInterval * 1000 - diffMillis <= 1000) {
                    lastCheckDumpMillis = time;
                }


            }
        }
        while (Verifier.nondetBoolean());
        assert (lastCheckDumpMillis == 0 || lastCheckDumpMillis == time );
        assert (lastHostCheckMillis == 0 || lastHostCheckMillis == time);
        assert (lastReloadMillis == prevLastReloadMillis || lastReloadMillis == time);


    }

    public static void main(String[] args){
         start();
    }
}
