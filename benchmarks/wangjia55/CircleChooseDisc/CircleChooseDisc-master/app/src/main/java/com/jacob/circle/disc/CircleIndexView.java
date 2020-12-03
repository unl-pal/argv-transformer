/** filtered and transformed by PAClab */
package com.jacob.circle.disc;

import gov.nasa.jpf.symbc.Debug;

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
            int childW = Debug.makeSymbolicInteger("x0");
            int childH = Debug.makeSymbolicInteger("x1");

            int left = Debug.makeSymbolicInteger("x2") / 2 - childW / 2;
            int top = Debug.makeSymbolicInteger("x3") / 2 - childH / 2;
        }
    }
}
