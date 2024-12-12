/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/** filtered and transformed by PAClab */


import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * A canned request for getting an image at a given URL and calling
 * back with a decoded Bitmap.
 */
public class Main {
    /**
     * Scales one side of a rectangle to fit aspect ratio.
     *
     * @param maxPrimary Maximum size of the primary dimension (i.e. width for
     *        max width), or zero to maintain aspect ratio with secondary
     *        dimension
     * @param maxSecondary Maximum size of the secondary dimension, or zero to
     *        maintain aspect ratio with primary dimension
     * @param actualPrimary Actual size of the primary dimension
     * @param actualSecondary Actual size of the secondary dimension
     */
    /** PACLab: suitable */
    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary,int actualSecondary) {

        assert (actualPrimary != 0 && actualSecondary != 0);
        // If no dominant value at all, just return the actual.
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            assert(ratio != 0.0);
            return (int) (actualPrimary * ratio);
        }
        if (maxSecondary == 0) {
            return maxPrimary;
        }
        assert(actualPrimary > 0);
        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        if(ratio < 1){
            assert (actualPrimary > actualSecondary);
        }


        return resized;
    }

    public static void main(String[] args){
        int maxPrimary = Verifier.nondetInt();
        int actualPrimary = Verifier.nondetInt();
        int actualSecondary = Verifier.nondetInt();
        int maxSecondary = Verifier.nondetInt();
        //as dimenssion can not be neagtive and actualPrimary and actualSecondary can not be 0 or neagtive as we are resizing the image
        if(maxPrimary >= 0 && maxSecondary >= 0 && actualPrimary > 0 && actualSecondary > 0 ){
            getResizedDimension(maxPrimary,maxSecondary,actualPrimary,actualSecondary);
        }




    }
}
