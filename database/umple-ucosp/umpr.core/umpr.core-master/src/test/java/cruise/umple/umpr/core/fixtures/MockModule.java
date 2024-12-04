package cruise.umple.umpr.core.fixtures;

import cruise.umple.umpr.core.DocumentFactory;
import cruise.umple.umpr.core.RealDocumentFactory;
import cruise.umple.umpr.core.consistent.ConsistentsModule;
import cruise.umple.umpr.core.entities.EntityModule;

import com.google.inject.AbstractModule;

/**
 * Test module to load files locally instead of from web pages, allowing for
 * consistency
 */
public class MockModule extends AbstractModule {
}
