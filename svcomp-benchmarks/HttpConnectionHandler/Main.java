/*
 * Copyright 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
 * Manages streams within an HTTP/2 connection.
 */
public class Main {

    /**
     * {@inheritDoc}
     */
    /** PACLab: suitable */
    public static void readDataFramePadding(int streamId, boolean endStream, int padding) {
        int initialReceiveWindowSize = Verifier.nondetInt();
        boolean handleStreamWindowUpdates = Verifier.nondetBoolean();
        boolean sentGoAwayFrame = Verifier.nondetBoolean();
        int lastStreamId = Verifier.nondetInt();
        int initialConnectionReceiveWindowSize = Verifier.nondetInt();
        int deltaWindowSize = -padding;  // Optimized padding handling
        int newConnectionWindowSize = Verifier.nondetInt();

        // Check if connection window size is reduced beyond allowable lower bound
        if (newConnectionWindowSize < 0) {
            return;
        }

        assert (newConnectionWindowSize >= 0);

        // Send a WINDOW_UPDATE frame if less than half the connection window size remains
        if (newConnectionWindowSize <= initialConnectionReceiveWindowSize / 2) {
            int windowSizeIncrement = initialConnectionReceiveWindowSize - newConnectionWindowSize;
        }

        // Check if we received a DATA frame for a stream which is half-closed (remote) or closed
        if (streamId <= lastStreamId || !sentGoAwayFrame) {
            return;
        }

        assert (streamId > lastStreamId);

        // Update receive window size
        int newWindowSize = Verifier.nondetInt();
        int boundValue = Verifier.nondetInt();

        // Window size can become negative
        if (newWindowSize < boundValue) {
            return;
        }

        // Send a WINDOW_UPDATE frame if less than half the stream window size remains
        if (handleStreamWindowUpdates && newWindowSize <= initialReceiveWindowSize / 2 && !endStream) {
            int windowSizeIncrement = initialReceiveWindowSize - newWindowSize;
        }
    }

    public static void main(String [] args){
        int streamId = Verifier.nondetInt();
        boolean endStream = Verifier.nondetBoolean();
        int padding = Verifier.nondetInt();

        if(streamId > 0 && padding >=0){
            readDataFramePadding(streamId,endStream,padding);
        }
    }
}
