package org.aksw.palmetto.calculations;

import java.util.Arrays;
import java.util.Collection;

import org.aksw.palmetto.subsets.OneOne;
import org.aksw.palmetto.subsets.SubsetCreator;
import org.aksw.palmetto.subsets.SubsetDefinition;
import org.aksw.palmetto.subsets.SubsetProbabilities;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class LogJaccardCoherenceCalculationTest {

    private static final double DOUBLE_PRECISION_DELTA = 0.00000001;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                /*
                 * word1 1 1 1
                 * 
                 * word2 0 1 1
                 * 
                 * word3 0 1 1
                 * 
                 * C_lj,oneone= 1/3 * (log(P(w_1,w_2)/(P(w_1 or w_2))) +
                 * log(P(w_1,w_3)/(P(w_1 or w_3))) + log(P(w_2,w_3)/(P(w_2 or
                 * w_3)))) = 1/3 * (log(2/3 / 1) + log(2/3 / 1) + log(2/3 /
                 * 2/3)) = 1/3 * (2*log(2/3))
                 */
                { new OneOne(), 3,
                        new double[] { 0, 1.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0 },
                        2.0 * Math.log(2.0 / 3.0) / 3.0 },

                /*
                 * word1 0 1 1
                 * 
                 * word2 1 0 1
                 * 
                 * word3 1 1 0
                 * 
                 * C_lj,oneone= 1/3 * (log(P(w_1,w_2)/(P(w_1 or w_2))) +
                 * log(P(w_1,w_3)/(P(w_1 or w_3))) + log(P(w_2,w_3)/(P(w_2 or
                 * w_3)))) = 1/3 * (log(1/3 / 1) + log(1/3 / 1) + log(1/3 / 1))
                 * = log(1/3)
                 */{ new OneOne(), 3,
                        new double[] { 0, 2.0 / 3.0, 2.0 / 3.0, 1.0 / 3.0, 2.0 / 3.0, 1.0 / 3.0, 1.0 / 3.0, 0 },
                        Math.log(1.0 / 3.0) },
                /*
                 * word1 0 0 0 1
                 * 
                 * word2 0 1 0 1
                 * 
                 * word3 0 0 1 1
                 * 
                 * C_lj,oneone= 1/3 * (log(P(w_1,w_2)/(P(w_1 or w_2))) +
                 * log(P(w_1,w_3)/(P(w_1 or w_3))) + log(P(w_2,w_3)/(P(w_2 or
                 * w_3)))) = 1/3 * (log(1/4 / 1/2) + log(1/4 / 3/4)) + log(1/4 /
                 * 1/2)) = 1/3 * (log(1/2) + log(1/3) + log(1/2)) = (2*log(0.5)
                 * + log(1/3))/3.0
                 */
                { new OneOne(), 3, new double[] { 0, 0.25, 0.5, 0.25, 0.5, 0.25, 0.25, 0.25 },
                        (2 * Math.log(0.5) + Math.log(1.0 / 3.0)) / 3.0 } });
    }

    private SubsetCreator subsetCreator;
    private int wordsetSize;
    private double probabilities[];
    private double expectedCoherence;

    public LogJaccardCoherenceCalculationTest(SubsetCreator subsetCreator, int wordsetSize, double[] probabilities,
            double expectedCoherence) {
        this.probabilities = probabilities;
        this.wordsetSize = wordsetSize;
        this.subsetCreator = subsetCreator;
        this.expectedCoherence = expectedCoherence;
    }

    @Test
    public void test() {
        LogJaccardCoherenceCalculation calculation = new LogJaccardCoherenceCalculation(null);
        SubsetDefinition subsets = subsetCreator.getSubsetDefinition(wordsetSize);
        Assert.assertEquals(expectedCoherence, calculation.calculateCoherence(new SubsetProbabilities(subsets.segments,
                subsets.conditions, probabilities)), DOUBLE_PRECISION_DELTA);
    }
}
