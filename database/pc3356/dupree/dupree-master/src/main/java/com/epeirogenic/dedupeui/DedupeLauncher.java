package com.epeirogenic.dedupeui;

import com.epeirogenic.dedupe.Checksum;
import com.epeirogenic.dedupe.FileRecurse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class DedupeLauncher extends JDialog {
    
    private File startDirectory;
    private final Map<String, Set<File>> checksumMap;
    private final DedupeWorker worker;

    class DedupeWorker extends SwingWorker<Void, String> {
    }

    static class DirectoryFilter extends FileFilter {
    }

    class DedupeUICallback implements FileRecurse.Callback {

        private final DedupeWorker dw;
    }

    private JPanel mainDialog;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton chooseButton;
    private JScrollPane infoPanel;
    private JTextField pathField;
    private JTextField startDirectoryField;
}
