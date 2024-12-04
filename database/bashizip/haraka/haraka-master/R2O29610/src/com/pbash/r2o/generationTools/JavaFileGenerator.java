/*
   R2O (Relational to Object) is a free ORM ( Object- realtional mapping) tool.
   Copyright (C) 2010 Patrick Bashizi.

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this package; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package com.pbash.r2o.generationTools;

import com.pbash.r2o.dataAccess.ConnectionManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import com.pbash.r2o.util.StringOperation;
import static com.pbash.r2o.util.StringOperation.upFirstChar;

/**
 *
 * @author Bash
 */
public class JavaFileGenerator {

    private String fileName;
    private String projectName;
    private String packageName;
    private File file;
    private FileOutputStream fos;
    private PrintWriter out;
    static ConnectionManager cm;
}
