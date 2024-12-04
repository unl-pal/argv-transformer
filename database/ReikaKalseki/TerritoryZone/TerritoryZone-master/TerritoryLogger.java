/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.TerritoryZone;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Level;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.IO.ReikaFileReader;
import Reika.DragonAPI.Instantiable.IO.ModLogger.LoggerOut;
import Reika.DragonAPI.Libraries.MathSci.ReikaDateHelper;


public class TerritoryLogger {

	public static final TerritoryLogger instance = new TerritoryLogger();

	private LoggerOut IOThread;

}
