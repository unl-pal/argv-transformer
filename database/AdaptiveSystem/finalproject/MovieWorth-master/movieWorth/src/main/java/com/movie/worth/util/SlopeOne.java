package com.movie.worth.util;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.worth.dao.Ratings;

@Service
public class SlopeOne {

    @Autowired
    private Ratings scCalcDAO;

    private int maxItemsId = 0;
    private int maxItem = 0;
    private int targetUser; //=900;
    private float mteste[][];
    private float mFreq1[][];
    private int mFreq[][];
    private float mDiff[][];
    private Map<Integer, Map<Integer, Float>> usersMatrix = null;
    private Map<Integer, Float> user = null;
    private HashMap<Integer, Float> predictions = null;
    private int[] slopeoneresult;

    private int userid[]; //= {3, 47, 103, 100, 134, 171, 220, 248, 712, 771, 49, 9, 876, 761};

    /*
     * Function buildDiffMatrix()
     * Calculates the DiffMatrix for all items
     *
     */
    public void buildDiffMatrix() {

        mteste = new float[maxItemsId + 1][maxItemsId + 1];
        mFreq = new int[maxItemsId + 1][maxItemsId + 1];

        for (int i = 1; i <= maxItemsId; i++) {
            for (int j = 1; j <= maxItemsId; j++) {
                mteste[i][j] = 0;
                mFreq[i][j] = 0;
            }
        }

        /* Iterate through all users, and then, through all items do calculate the diffs */
        for (int cUser : usersMatrix.keySet()) {
            for (int i : usersMatrix.get(cUser).keySet()) {
                for (int j : usersMatrix.get(cUser).keySet()) {
                    mteste[i][j] = mteste[i][j]
                            + (usersMatrix.get(cUser).get(i).floatValue() - (usersMatrix.get(cUser).get(j).floatValue()));
                    mFreq[i][j] = mFreq[i][j] + 1;
                }
            }
        }

        /*  Calculate the averages (diff/freqs) */
        for (int i = 1; i <= maxItemsId; i++) {
            for (int j = i; j <= maxItemsId; j++) {
                if (mFreq[i][j] > 0) {
                    mteste[i][j] = mteste[i][j] / mFreq[i][j];
                }
            }
        }
    }

    class FloatEntryComparator implements Comparator<Map.Entry>{
    }

    public void Predict() {

        float totalFreq[] = new float[maxItem + 1];

        /* Start prediction */
        for (int j = 1; j <= maxItem; j++) {
            predictions.put(j, 0.0f);
            totalFreq[j] = 0;
        }

        for (int j : user.keySet()) {
            for (int k = 1; k <= maxItem; k++) {
                if (j != k) {
                    /* Only for items the user has not seen */
                    if (!user.containsKey(k)) {
                        float newVal = 0;
                        if (k < j) {
                            newVal = mFreq1[j][k] * (mDiff[j][k] + user.get(j).floatValue());
                        } else {
                            newVal = mFreq1[j][k] * (-1 * mDiff[j][k] + user.get(j).floatValue());
                        }
                        totalFreq[k] = totalFreq[k] + mFreq1[j][k];
                        predictions.put(k, predictions.get(k).floatValue() + newVal);
                    }
                }
            }
        }

        /* Calculate the average */
        for (int j : predictions.keySet()) {
            predictions.put(j, predictions.get(j).floatValue() / (totalFreq[j]));
        }

        /* Fill the predictions vector with the already known rating values
        for (int j : user.keySet()) {
            predictions.put(j, user.get(j));
        }
        */

        /* Print predictions */
        List<Map.Entry> lists = new ArrayList<Map.Entry>(predictions.entrySet());
        java.util.Collections.sort(lists, new FloatEntryComparator());


        slopeoneresult = new int[5];
        int q = 0;
        //System.out.println("\n" + "#### Predictions Here #### ");
        for (Map.Entry<Integer, Float> entry : lists) {
        	 if(!Float.isNaN(predictions.get(entry.getKey()))){
        		 slopeoneresult[q] = entry.getKey();
        		 q++;
            }
        	 if(q>=5)
        		 break;
        //System.out.println(entry.getKey().toString() + " : " + predictions.get(entry.getKey()).floatValue());
        }

    }
}
