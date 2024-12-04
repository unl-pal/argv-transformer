package reflection;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.io.IOException;
import java.lang.reflect.*;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Inspector {
    private URLClassLoader cl;
    private JarFile jar;
    private Set<Class> dependencies;
}
