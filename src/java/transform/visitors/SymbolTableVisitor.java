package transform.visitors;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.*;

import transform.SymbolTable.BlockSTE;
import transform.SymbolTable.ClassSTE;
import transform.SymbolTable.MethodSTE;
import transform.SymbolTable.SymbolTable;
import transform.SymbolTable.VarSTE;
import transform.TypeChecking.TypeChecker;

/**
 * Visitor class used to build the symbol table.
 * 
 * In the current implementation, we are only creating a new SymbolTable,
 * i.e., a new scope, for type declarations (classes) and methods. Need to
 * create a new scope for all blocks/loop bodies.
 * 
 * @author mariapaquin
 *
 */
public class SymbolTableVisitor extends ASTVisitorUtil {
  private SymbolTable root;
  private Stack<SymbolTable> symbolTableStack;
  private TypeChecker typeChecker;

  /**
   * Create a new SymbolTableVisitor.
   * 
   * @param typeChecker TypeChecker defining allowed types.
   */
  public SymbolTableVisitor(TypeChecker typeChecker) {
    root = new SymbolTable(null);
    symbolTableStack = new Stack<SymbolTable>();
    this.typeChecker = typeChecker;
  }

  /**
   * 
   * @return root, i.e., parent symbol table
   */
  public SymbolTable getRoot() {
    return root;
  }

  @Override
  public boolean visit(CompilationUnit node) {
    symbolTableStack.push(root);
    return true;
  }

  @Override
  public boolean visit(FieldDeclaration node) {
    SymbolTable currScope = symbolTableStack.peek();
    Type type = node.getType();

    @SuppressWarnings("unchecked")
    List<VariableDeclarationFragment> fragments = node.fragments();
    for (VariableDeclarationFragment fragment : fragments) {
      VarSTE sym = new VarSTE(fragment.getName().getIdentifier(), type);
      sym.setFieldVar(true);
      sym.setSymbolTable(currScope);
      String name = "@" + fragment.getName().getIdentifier();
      currScope.put(name, sym);
    }

    return true;
  }

//  @Override
//  public void endVisit(FieldDeclaration node) {
//    if (node.getType().toString().equals("enum")) {
//      symbolTableStack.pop();
//    }
//  }

  @Override
  public boolean visit(MethodDeclaration node) {
    @SuppressWarnings("unchecked")
    List<SingleVariableDeclaration> params = node.parameters();
    for (SingleVariableDeclaration param : params) {
      Type type = param.getType();
      /*
       * If the method has types that are not allowed, we don't need
       * to create a symbol table for it, since we are going to
       * remove it anyway. This simplifies the process of identifying a
       * method symbol table element based on its generated name (i.e., see
       * the method getMethodSTEName(), where we only consider primitive type
       * parameters).
       */
      // if (!typeChecker.allowedType(type)) {
      // logger.logln("Removing method " + node.getName() + " from SYMBOL table
      // because of parameter '"+ param + "' of type " + type, 5);
      // return false;
      // }
    }

    SymbolTable currScope = symbolTableStack.peek();

    String name = getMethodSTEName(node);
    MethodSTE sym = new MethodSTE(name);
    Type returnType = node.getReturnType2();
    sym.setReturnType(returnType);

    currScope.put(name, sym);

    SymbolTable newScope = new SymbolTable(currScope);
    sym.setSymbolTable(newScope);
    symbolTableStack.push(newScope);

    return true;
  }

  @Override
  public void endVisit(MethodDeclaration node) {
    boolean pushedMethod = true;
    @SuppressWarnings("unchecked")
    List<SingleVariableDeclaration> params = node.parameters();
    for (SingleVariableDeclaration param : params) {
      Type type = param.getType();
      /*
       * If the method has a parameter type that is not allowed,
       * we did not create a symbol table element for it and thus
       * there is nothing to pop.
       */
      // if (!typeChecker.allowedType(type)) {
      // pushedMethod = false;
      // }
    }
    if (pushedMethod) {
      symbolTableStack.pop();
    }
  }

  @Override
  public boolean visit(SingleVariableDeclaration node) {
    Type type = node.getType();
    SymbolTable currScope = symbolTableStack.peek();

    VarSTE sym = new VarSTE(node.getName().getIdentifier(), type);
    String name = node.getName().getIdentifier();
    currScope.put(name, sym);
    return true;
  }

  @Override
  public boolean visit(VariableDeclarationExpression node) {
    Type type = node.getType();
    SymbolTable currScope = symbolTableStack.peek();

    @SuppressWarnings("unchecked")
    List<VariableDeclarationFragment> fragments = node.fragments();
    for (VariableDeclarationFragment fragment : fragments) {
      VarSTE sym = new VarSTE(fragment.getName().getIdentifier(), type);
      String name = fragment.getName().getIdentifier();
      currScope.put(name, sym);
    }
    return true;
  }

//   @Override
//   public boolean visit(WhileStatement node) {
//     SymbolTable currScope = symbolTableStack.peek();
//
//     int startPosition = node.getStartPosition();
//     String name = "WhileStatement@" + startPosition;
//
//     BlockSTE sym = new BlockSTE(name);
//     SymbolTable newScope = new SymbolTable(currScope);
//     sym.setSymbolTable(newScope);
//     currScope.put(name, sym);
//     symbolTableStack.push(newScope);
//
//     return true;
//
//   }
//
//   @Override
//   public void endVisit(WhileStatement node) {
//     symbolTableStack.pop();
//   }

   @Override
   public boolean visit(ForStatement node) {
     SymbolTable currScope = symbolTableStack.peek();

     // Create a new scope for the for loop (includes initializers and body)
     int startPosition = node.getStartPosition();
     String name = "ForStatement@" + startPosition;

     BlockSTE sym = new BlockSTE(name);
     SymbolTable newScope = new SymbolTable(currScope);
     sym.setSymbolTable(newScope);
     currScope.put(name, sym);
     symbolTableStack.push(newScope);

     return true;
   }

   @Override
   public void endVisit(ForStatement node) {
     symbolTableStack.pop();
   }

   @Override
   public boolean visit(EnhancedForStatement node) {
     SymbolTable currScope = symbolTableStack.peek();

     int startPosition = node.getStartPosition();
     String name = "EnhancedForStatement@" + startPosition;

     BlockSTE sym = new BlockSTE(name);
     SymbolTable newScope = new SymbolTable(currScope);
     sym.setSymbolTable(newScope);
     currScope.put(name, sym);
     symbolTableStack.push(newScope);

     return true;
   }

   @Override
   public void endVisit(EnhancedForStatement node) {
     symbolTableStack.pop();
   }

//   @Override
//   public boolean visit(DoStatement node) {
//     SymbolTable currScope = symbolTableStack.peek();
//
//     int startPosition = node.getStartPosition();
//     String name = "DoStatement@" + startPosition;
//
//     BlockSTE sym = new BlockSTE(name);
//     SymbolTable newScope = new SymbolTable(currScope);
//     sym.setSymbolTable(newScope);
//     currScope.put(name, sym);
//     symbolTableStack.push(newScope);
//     return true;
//   }
//
//   @Override
//   public void endVisit(DoStatement node) {
//     symbolTableStack.pop();
//   }

  @Override
  public boolean visit(TypeDeclaration node) {
    if (node.isInterface()) {
      return false;
    }

    SymbolTable currScope = symbolTableStack.peek();

    ClassSTE sym = new ClassSTE(node.getName().getIdentifier());
    String name = "#" + node.getName().getIdentifier();
    currScope.put(name, sym);

    SymbolTable newScope = new SymbolTable(currScope);

    sym.setSymbolTable(newScope);
    symbolTableStack.push(newScope);
    return true;
  }

  @Override
  public void endVisit(TypeDeclaration node) {
    if (!node.isInterface()) {
      symbolTableStack.pop();
    }
  }

  @Override
  public boolean visit(VariableDeclarationStatement node) {
    Type type = node.getType();
    SymbolTable currScope = symbolTableStack.peek();

    @SuppressWarnings("unchecked")
    List<VariableDeclarationFragment> fragments = node.fragments();

    for (VariableDeclarationFragment fragment : fragments) {
      VarSTE sym = new VarSTE(fragment.getName().getIdentifier(), type);
      String name = fragment.getName().getIdentifier();
      currScope.put(name, sym);
    }
    return true;
  }

  //
  @Override
  public boolean visit(Block node) {
    // ASTNode parent = node.getParent();
    // boolean needsScope = false;
    String name = "Block@" + node.getStartPosition();
    // if (parent instanceof IfStatement) {
    //   name = "IfBlock@" + name;
    //   needsScope = true;
    // } else if (parent instanceof TryStatement){
    //   TryStatement tryNode = (TryStatement) parent;
    //   if (tryNode.getFinally() == node) {
    //     name = "TryBlock@" + name;
    //     needsScope = true;
    //   }
    // } else if (parent instanceof SwitchStatement) {
    //   name = "SwitchBlock@" + name;
    //   needsScope = true;
    // }
    // if (needsScope) {
      SymbolTable currScope = symbolTableStack.peek();
      BlockSTE sym = new BlockSTE(name);
      SymbolTable newScope = new SymbolTable(currScope);
      sym.setSymbolTable(newScope);
      currScope.put(name, sym);
      symbolTableStack.push(newScope);
    // }
    return true;
  }

  @Override
  public void endVisit(Block node) {
    // ASTNode parent = node.getParent();
    // if (parent instanceof IfStatement) {
    //   symbolTableStack.pop();
    // } else if (parent instanceof TryStatement){
    //   TryStatement tryNode = (TryStatement) parent;
    //   if (tryNode.getFinally() == node) {
    //     symbolTableStack.pop();
    //   }
    // } else if (parent instanceof SwitchStatement) {
      symbolTableStack.pop();
    // }
  }

  // @Override
  // public boolean visit(IfStatement node) {
  //
  //   // each block in for loops and if statemnts creates a new scope
  //   SymbolTable currScope = symbolTableStack.peek();
  //
  //   // just need a unique identifier
  //   String name = "IfStatement@" + node.getStartPosition();
  //
  //   BlockSTE sym = new BlockSTE(name);
  //   SymbolTable newScope = new SymbolTable(currScope);
  //   sym.setSymbolTable(newScope);
  //   currScope.put(name, sym);
  //   symbolTableStack.push(newScope);
  //
  //   return true;
  // }
  //
  // @Override
  // public void endVisit(IfStatement node) {
  //     symbolTableStack.pop();
  // }

  @Override
  public boolean visit(EnumDeclaration node) {
    String name = node.getName().getIdentifier();
    SymbolTable currScope = symbolTableStack.peek();
    ClassSTE sym = new ClassSTE(name);
    SymbolTable newScope = new SymbolTable(currScope);
    sym.setSymbolTable(newScope);
    currScope.put(name, sym);
    symbolTableStack.push(newScope);
    return true;
  }

  @Override
  public void endVisit(EnumDeclaration node) {
    symbolTableStack.pop();
  }

@Override
public boolean visit(TryStatement node) {
  String name = "TryStatement@" + node.getStartPosition();
  SymbolTable currScope = symbolTableStack.peek();
  BlockSTE sym = new BlockSTE(name);
  SymbolTable newScope = new SymbolTable(currScope);
  sym.setSymbolTable(newScope);
  currScope.put(name, sym);
  symbolTableStack.push(newScope);
  return true;
}

@Override
public void endVisit(TryStatement node) {
  symbolTableStack.pop();
}

   @Override
   public boolean visit(CatchClause node) {
     String name = "CatchClause@" + node.getStartPosition();
     SymbolTable currScope = symbolTableStack.peek();
     BlockSTE sym = new BlockSTE(name);
     SymbolTable newScope = new SymbolTable(currScope);
     sym.setSymbolTable(newScope);
     currScope.put(name, sym);
     symbolTableStack.push(newScope);
     return true;
   }

   @Override
   public void endVisit(CatchClause node) {
     symbolTableStack.pop();
   }

  @Override
  public boolean visit(AnonymousClassDeclaration node) {
    String name = "AnonymousClassDeclaration@" + node.getStartPosition();
    SymbolTable currScope = symbolTableStack.peek();
    ClassSTE sym = new ClassSTE(name);
    SymbolTable newScope = new SymbolTable(currScope);
    sym.setSymbolTable(newScope);
    currScope.put(name, sym);
    symbolTableStack.push(newScope);
    return true;
  }

  @Override
  public void endVisit(AnonymousClassDeclaration node) {
    symbolTableStack.pop();
  }

  // @Override
  // public boolean visit(SwitchCase node) {
  //   SymbolTable currScope = symbolTableStack.peek();
  //   String name = "SwitchCase@" + node.getStartPosition();
  //   BlockSTE sym = new BlockSTE(name);
  //   SymbolTable newScope = new SymbolTable(currScope);
  //   sym.setSymbolTable(newScope);
  //   currScope.put(name, sym);
  //   symbolTableStack.push(newScope);
  //   return true;
  // }
  //
  // @Override
  // public void endVisit(SwitchCase node) {
  //   symbolTableStack.pop();
  // }
  
  @Override
  public boolean visit(LambdaExpression node) {
    String name = "LambdaExpression@" + node.getStartPosition();
    SymbolTable currScope = symbolTableStack.peek();
    BlockSTE sym = new BlockSTE(name);
    SymbolTable newScope = new SymbolTable(currScope);
    sym.setSymbolTable(newScope);
    currScope.put(name, sym);
    symbolTableStack.push(newScope);
    return true;
  }

  @Override
  public void endVisit(LambdaExpression node) {
    symbolTableStack.pop();
  }
}
