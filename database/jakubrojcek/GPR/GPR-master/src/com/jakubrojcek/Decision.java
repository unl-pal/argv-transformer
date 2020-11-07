package com.jakubrojcek;

import java.util.IdentityHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Jakub
 * Date: 12.11.12
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class Decision {
    private byte nP;
    private int LL;
    private int FVpos;
    private int e;
    private int breakPoint;
    private int[] counts;           // holds counts of decisions. [0] overall count
    private int[] countsLiquidity;  // holds counts of LOs vs MOs vs NOs
    private int[] countsSimple;     // holds counts of decisions not conditioned on previous action or book
        /*
        Table V, DA = depth at ask
        DA 1-2, spread 1,   [41] count, [1] SBMOs, [86] LBO, [2] AggBLO [94] AtBLO [95] BelowBLO
        DA 1-2, spread > 1, [42] count, [3] SBMOs, [87] LBO, [4] AggBLO [96] AtBLO [97] BelowBLO
        DA 3-7, spread 1,   [43] count, [5] SBMOs, [88] LBO, [6] AggBLO [98] AtBLO [99] BelowBLO
        DA 3-7, spread > 1, [44] count, [7] SBMOs, [89] LBO, [8] AggBLO [100] AtBLO [101] BelowBLO
        DA > 7, spread 1,   [146] count, [147] SBMOs, [148] LBO, [149] AggBLO [150] AtBLO [151] BelowBLO
        DA > 7, spread > 1, [152] count, [153] SBMOs, [154] LBO, [155] AggBLO [156] AtBLO [157] BelowBLO

        Table I: conditional frequencies of buy orders at t+1 given order at t
        BMO ->      [66] LBM [9]  BMO [10] AggBLO [11] AtBLO [12] BelowBLO [45] count
        LBM ->      [67] LBM [68] BMO [69] AggBLO [70] AtBLO [71] BelowBLO [72] count
        SMO ->      [73] LBM [13] BMO [14] AggBLO [15] AtBLO [16] BelowBLO [46] count
        LSO->       [74] LBM [75] BMO [76] AggBLO [77] AtBLO [78] BelowBLO [79] count
        AggBLO ->   [80] LBM [17] BMO [18] AggBLO [19] AtBLO [20] BelowBLO [47] count
        AggSLO ->   [81] LBM [21] BMO [22] AggBLO [23] AtBLO [24] BelowBLO [48] count
        AtBLO ->    [82] LBM [25] BMO [26] AggBLO [27] AtBLO [28] BelowBLO [49] count
        AtSLO ->    [83] LBM [29] BMO [30] AggBLO [31] AtBLO [32] BelowBLO [50] count
        BelowBLO -> [84] LBM [33] BMO [34] AggBLO [35] AtBLO [36] BelowBLO [51] count
        AboveSLO -> [85] LBM [37] BMO [38] AggBLO [39] AtBLO [40] BelowBLO [52] count

        Table III, DB = depth at bid
        DB 1-2, spread 1,   [53] count, [54] BMOs, [90] LBO, [55] AggBLO [102] AtBLO [103] BelowBLO
        DB 1-2, spread > 1, [56] count, [57] BMOs, [91] LBO, [58] AggBLO [104] AtBLO [105] BelowBLO
        DB 3-7, spread 1,   [59] count, [60] BMOs, [92] LBO, [61] AggBLO [106] AtBLO [107] BelowBLO
        DB 3-7, spread > 1, [62] count, [63] BMOs, [93] LBO, [64] AggBLO [108] AtBLO [109] BelowBLO
        DB > 7, spread 1,   [134] count, [135] BMOs, [136] LBO, [137] AggBLO [138] AtBLO [139] BelowBLO
        DB > 7, spread > 1, [140] count, [141] BMOs, [142] LBO, [143] AggBLO [144] AtBLO [145] BelowBLO

        Table IV, DB = depth below bid
        DB < 15, spread 1,   [110] count, [111] BMOs, [112] LBO, [113] AggBLO [114] AtBLO [115] BelowBLO
        DB > 15, spread 1, [116] count, [117] BMOs, [118] LBO, [119] AggBLO [120] AtBLO [121] BelowBLO
        DB < 15, spread > 1,   [122] count, [123] BMOs, [124] LBO, [125] AggBLO [126] AtBLO [127] BelowBLO
        DB > 15, spread > 1, [128] count, [129] BMOs, [130] LBO, [131] AggBLO [132] AtBLO [133] BelowBLO

        com.jakubrojcek.Diagnostics: too many sellers
        [65] count of sell orders
        */

    // adds information to the decision
    public void addDecision(int[] bi, short ac, boolean cancelled){
        countsSimple[0]++;
        if (ac == (2 * e + 1)){                     // Buy market order
            countsSimple[1]++;
        } else if (ac >= e && ac < 2 * e){          // Buy Limit order
            if ((ac - e) < (bi[0] - LL)){           // BelowBLO
                countsSimple[4]++;
            } else if ((ac - e) == (bi[0] - LL)){   // AtBLO
                countsSimple[3]++;
            } else if ((ac - e) > (bi[0] - LL)){    // AggBLO
                countsSimple[2]++;
            }
        } else if (ac == 2 * e + 2){
            countsSimple[5]++;                  // no-orders
        }
        if (cancelled){                         // cancellations
            countsSimple[6]++;
        }
    }

    public void addDecisionLiquidity(int ac, boolean hft, boolean cancelled){
        if (hft){
            if (ac < (2 * e)){              // LO
                countsLiquidity[4]++;
            } else if (ac == (2 * e + 2)){  // NO
                countsLiquidity[5]++;
            } else {
                countsLiquidity[3]++;       // MO
            }
            if (cancelled){
                countsLiquidity[7]++;       // cancellation
            }
        } else {
            if (ac < (2 * e)){              // LO
                countsLiquidity[1]++;
            } else if (ac == (2 * e + 2)){
                countsLiquidity[2]++;       // NO
            } else {
                countsLiquidity[0]++;       // MO
            }
            if (cancelled){
                countsLiquidity[6]++;       // cancellation
            }
        }
    }
}
