package filter.fileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import download.GitProject;
/**
 * Class to track the number of classes and methods in a GitHub project.
 * 
 * @author mariapaquin
 *
 */
public class GeneralFileInfo {
	private ArrayList<File> totalFiles;
	private List<GitProject> gitProjects;
	private int methodCount;

	public GeneralFileInfo(List<GitProject> gitProjects) {
		this.gitProjects = gitProjects;
		totalFiles = new ArrayList<File>();
		methodCount = 0;
	}
	
	public int countFiles() {
		for (GitProject project : gitProjects) {
			ArrayList<File> files = project.getFiles();
			for (File file : files) {
				totalFiles.add(file);
			}
		}
		return totalFiles.size();
	}

	public int countMethods() throws IOException {

		for (GitProject project : gitProjects) {
			ArrayList<File> files = project.getFiles();

			for (File file : files) {
				String source = new String(Files.readAllBytes(file.toPath()));
				ASTParser parser = ASTParser.newParser(AST.JLS3);
				parser.setSource(source.toCharArray());
				parser.setKind(ASTParser.K_COMPILATION_UNIT);
				CompilationUnit cu = (CompilationUnit) parser.createAST(null);

				ASTVisitor visitor = new ASTVisitor() {
					@Override
					public boolean visit(MethodDeclaration node) {
						methodCount++;
						return false;
					}
				};
				cu.accept(visitor);
			}
		}
		return methodCount;
	}
}
