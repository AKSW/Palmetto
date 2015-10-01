/**
 * This file is part of Palmetto.
 *
 * Palmetto is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Palmetto is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Palmetto.  If not, see <http://www.gnu.org/licenses/>.
 */
							(roeder@informatik.uni-leipzig.de)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.aksw.palmetto.prob;

import org.aksw.palmetto.data.CountedSubsets;
import org.aksw.palmetto.data.SegmentationDefinition;
import org.aksw.palmetto.data.SubsetProbabilities;

public abstract class AbstractProbabilitySupplier implements
        ProbabilityEstimator {

    public static final int DEFAULT_MIN_FREQUENCY = 10;

    protected FrequencyDeterminer freqDeterminer;
    protected int minFrequency = DEFAULT_MIN_FREQUENCY;

    protected AbstractProbabilitySupplier(FrequencyDeterminer freqDeterminer) {
        this.freqDeterminer = freqDeterminer;
    }

    public SubsetProbabilities[] getProbabilities(String wordsets[][],
            SegmentationDefinition definitions[]) {
        CountedSubsets subsets[] = freqDeterminer.determineCounts(wordsets,
                definitions);
        SubsetProbabilities probabilities[] = new SubsetProbabilities[subsets.length];
        for (int i = 0; i < subsets.length; i++) {
            probabilities[i] = getProbabilities(subsets[i]);
        }
        return probabilities;
    }

    protected abstract SubsetProbabilities getProbabilities(
            CountedSubsets countedSubsets);

    public void setMinFrequency(int minFrequency) {
        this.minFrequency = minFrequency;
    }

    public int getMinFrequency() {
        return minFrequency;
    }

    public FrequencyDeterminer getFrequencyDeterminer() {
        return freqDeterminer;
    }

    public void setFrequencyDeterminer(FrequencyDeterminer freqDeterminer) {
        this.freqDeterminer = freqDeterminer;
    }
}
