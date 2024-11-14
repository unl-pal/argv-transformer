/*
 * feinrip
 *
 * Copyright (C) 2014 Richard "Shred" Körber
 *   https://github.com/shred/feinrip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
/** filtered and transformed by PAClab */
package org.shredzone.feinrip.gui.pane;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * PowerPane that shows the progress of a running conversion.
 *
 * @author Richard "Shred" Körber
 */
public class ProgressPane {
    /**
     * Estimates the time until 100% will be reached.
     * <p>
     * A first estimation is available after 3 seconds.
     *
     * @param percent
     *            current percent
     * @return Estimated number of seconds until 100% will be reached. Returns -1 if an
     *         estimation is not currently available.
     */
    /** PACLab: suitable */
	 private int estimateTime(float percent) {
        if (Verifier.nondetBoolean() || percent <= 0 || percent > 100) return -1;

        long current = Verifier.nondetInt();
        long elapsed = current - Verifier.nondetInt();

        // Wait at least 3 seconds before estimating.
        if (elapsed < 3000) return -1;

        // --- Compute ETA and required time ---
        // The ETA is extrapolated from the elapsed time and the index
        // in relation to the given maximum.
        double eta = Verifier.nondetDouble();
        double required = Verifier.nondetDouble(); // floored, in seconds

        // --- Out of range? ---
        if (required < 0 || required > Verifier.nondetDouble()) return -1;

        // --- Return the time ---
        return (int) required;
    }

    /**
     * Action for selecting a temp dir.
     */
    private class TempSelectAction {
    }

    /**
     * Action for selecting an mp3 file.
     */
    private class Mp3SelectAction {
    }

    /**
     * Action for selecting a script file.
     */
    private class ScriptSelectAction {
    }

}
