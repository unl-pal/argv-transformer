/*
 * feinrip
 *
 * Copyright (C) 2014 Richard "Shred" Körber
 *   https://github.com/shred/feinrip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.shredzone.feinrip.lsdvd;

import org.shredzone.feinrip.lsdvd.DvdSubtitleAttributes.SubType;

/**
 * Information about a title's individual subtitle stream.
 *
 * @author Richard "Shred" Körber
 */
public class DvdSubtitle {

    private final DvdSubtitleAttributes attrs;
    private Integer stream43Id;
    private Integer streamWideId;
    private Integer streamLetterboxId;
    private Integer streamPanScanId;

}
