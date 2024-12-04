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
package org.shredzone.feinrip.dvb;

import static java.lang.Math.*;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.shredzone.feinrip.dvb.si.CharsetMapper;
import org.shredzone.feinrip.dvb.si.descriptor.Descriptor;
import org.shredzone.feinrip.dvb.si.descriptor.DescriptorFactory;

/**
 * InputStream for reading DVB streams.
 *
 * @author Richard "Shred" Körber
 * @see <a href="http://www.etsi.org/deliver/etsi_en/300400_300499/300468/01.12.01_40/en_300468v011201o.pdf">ETSI EN 300 468</a>
 */
public class DvbInputStream extends DataInputStream {

    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

}
