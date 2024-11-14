package org.rakam.server.http;

import org.rakam.server.http.annotations.JsonRequest;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import javax.ws.rs.Path;
import java.util.Set;

import static java.lang.String.format;

@SupportedAnnotationTypes(
        {"org.rakam.server.Service", "org.rakam.server.annotations.JsonRequest"}
)
public class HttpServiceProcessor extends AbstractProcessor {
}
