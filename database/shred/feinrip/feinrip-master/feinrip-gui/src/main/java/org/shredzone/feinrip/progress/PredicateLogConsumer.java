/*
 * feinrip
 *
 * Copyright (C) 2015 Richard "Shred" Körber
 *   https://github.com/shred/feinrip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.shredzone.feinrip.progress;

import java.util.function.Predicate;

/**
 * A {@link LogConsumer} that tests a predicate in the output and sets a flag if it
 * matched at least once.
 *
 * @author Richard "Shred" Körber
 */
public class PredicateLogConsumer extends LogConsumer {

    private final Predicate<String> predicate;
    private boolean matched = false;

}
