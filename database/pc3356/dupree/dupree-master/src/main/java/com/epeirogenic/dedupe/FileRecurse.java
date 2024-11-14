package com.epeirogenic.dedupe;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@Slf4j
public class FileRecurse {

    private final Checksum checksum;
    private final Callback callback;
    private boolean ignoreHiddenDirectories;
    private boolean ignoreHiddenFiles;

    public interface Callback {
    }

    public final static Callback SYSTEM_CALLBACK = new Callback() {

        @Override
        public void currentFile(final File file) {
            System.out.println(file.getAbsolutePath());
        }

        @Override
        public void currentDirectory(final File directory) {
            System.out.println(directory.getAbsolutePath());
        }
    };

    public final static Callback NOOP_CALLBACK = new Callback() {

        @Override
        public void currentFile(final File file) {}

        @Override
        public void currentDirectory(final File directory) {}
    };

    public final static Callback LOG_CALLBACK = new Callback() {
        @Override
        public void currentFile(final File file) {
            log.info(file.getAbsolutePath());
        }

        @Override
        public void currentDirectory(final File directory) {
            log.info(directory.getAbsolutePath());
        }
    };
}
