/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2012 Regents of the University of Minnesota and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.grouplens.lenskit.graphchi.util.matrixmarket;

import org.grouplens.grapht.annotation.DefaultProvider;
import org.grouplens.lenskit.cursors.AbstractPollingCursor;
import org.grouplens.lenskit.data.dao.DataAccessObject;
import org.grouplens.lenskit.data.pref.IndexedPreference;
import org.grouplens.lenskit.data.snapshot.PreferenceSnapshot;
import org.grouplens.lenskit.data.snapshot.PackedPreferenceSnapshot;
import org.grouplens.lenskit.graphchi.util.MatrixEntry;
import org.grouplens.lenskit.util.Index;

import javax.inject.Inject;
import java.util.Iterator;

/**
 * The wrapper for a PreferenceSnapshot to act as a matrix source.
 * This is the default provider for userItemMatrixSource.
 *
 * Every public function is inherited or implemented from <code>AbstractPollingCursor</code> or <code>UserItemMatrixSource</code>
 *
 * @author Daniel Gratzer < danny.gratzer@gmail.com >
 */
@DefaultProvider(PreferenceSnapshotMatrixSource.Provider.class)
public class PreferenceSnapshotMatrixSource extends AbstractPollingCursor<MatrixEntry> implements UserItemMatrixSource{

    public static class Provider implements javax.inject.Provider<PreferenceSnapshotMatrixSource>{

        private PreferenceSnapshot snapshot;
        private boolean sorted = false;
    }

    private int rows;
    private int columns;
    private int entries;
    private boolean isSorted;
    private MatrixEntry nextEntry;
    private Iterator<IndexedPreference> fastIterator;
    private Index userIds;
    private Index itemIds;
}
