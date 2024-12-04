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
package org.shredzone.feinrip.util;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.shredzone.feinrip.lsdvd.DvdAudio;
import org.shredzone.feinrip.lsdvd.DvdSubtitle;
import org.shredzone.feinrip.lsdvd.DvdTitle;
import org.shredzone.feinrip.lsdvd.DvdTitleSet;
import org.shredzone.feinrip.lsdvd.IfoReader;
import org.shredzone.feinrip.model.AspectRatio;
import org.shredzone.feinrip.model.Audio;
import org.shredzone.feinrip.model.AudioType;
import org.shredzone.feinrip.model.Chapter;
import org.shredzone.feinrip.model.Language;
import org.shredzone.feinrip.model.Palette;
import org.shredzone.feinrip.model.Subtitle;
import org.shredzone.feinrip.model.SubtitleFormat;
import org.shredzone.feinrip.model.SubtitleType;
import org.shredzone.feinrip.model.Track;
import org.shredzone.feinrip.system.LanguageUtils;

/**
 * Analyzes a DVD structure.
 *
 * @author Richard "Shred" Körber
 */
public class DvdAnalyzer {

    private IfoReader info;
    private String title;

    /**
     * Returns the number of the longest track.
     */
    public int getLongestTrack() {
        int maxTitle = 0;
        long maxLength = 0;

        for (int ix = 0; ix < info.getTitles().size(); ix++) {
            DvdTitle title = info.getTitle(ix);
            if (title.getTotalTimeMs() > maxLength) {
                maxTitle = ix;
                maxLength = title.getTotalTimeMs();
            }
        }

        return maxTitle + 1;
    }

    /**
     * Creates a {@link Chapter} entry.
     *
     * @param chapter
     *            chapter number, starting from 1
     * @param ms
     *            position of that chapter
     * @param chapters
     *            number of chapters, used for detecting annexes
     * @return {@link Chapter} that was created
     */
    private Chapter createChapter(int chapter, long ms, int chapters) {
        Chapter chap = new Chapter();
        chap.setNumber(chapter);
        if (chapter > chapters) {
            chap.setTitle(String.format("Annex %02d", chapter - chapters));
        } else {
            chap.setTitle(String.format("Chapter %02d", chapter));
        }
        chap.setPosition(
                String.format("%2d:%02d:%02d.%03d",
                (ms / (60 * 60 * 1000L)),
                (ms / (60 * 1000L) % 60),
                ((ms / 1000L) % 60),
                ms % 1000L
        ));
        return chap;
    }

}
