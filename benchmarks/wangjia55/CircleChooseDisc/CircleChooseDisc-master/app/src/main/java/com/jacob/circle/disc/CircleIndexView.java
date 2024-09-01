/** filtered and transformed by PAClab */
package com.jacob.circle.disc;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Package : com.jacob.circle.disc
 * Author : jacob
 * Date : 15-4-1
 * Description : 这个类是用来xxx
 */
public class CircleIndexView {

    /** PACLab: suitable */
	 protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childW = Verifier.nondetInt();
            int childH = Verifier.nondetInt();

            int left = Verifier.nondetInt() / 2 - childW / 2;
            int top = Verifier.nondetInt() / 2 - childH / 2;
        }
    }
}
