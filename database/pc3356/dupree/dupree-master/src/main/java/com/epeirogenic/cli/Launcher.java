package com.epeirogenic.cli;

import com.epeirogenic.dedupe.Checksum;
import com.epeirogenic.dedupe.FileRecurse;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Launcher {

    private final static String DEFAULT_FILENAME_FORMAT = "dupree-{0}.csv";
}

class SummaryCallback implements FileRecurse.Callback {

    String currentOutput = "";
}
