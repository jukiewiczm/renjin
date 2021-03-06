package org.renjin.packaging;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.renjin.eval.Context;
import org.renjin.primitives.packaging.FqPackageName;
import org.renjin.primitives.packaging.Package;
import org.renjin.sexp.NamedValue;
import org.renjin.util.NamedByteSource;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class InitializingPackage extends Package {

  private final File packageRoot;
  private ClassLoader classLoader;

  protected InitializingPackage(FqPackageName name, File packageRoot, ClassLoader classLoader) {
    super(name);
    this.packageRoot = packageRoot;
    this.classLoader = classLoader;
  }

  @Override
  public Iterable<NamedValue> loadSymbols(Context context) throws IOException {
    return Collections.emptySet();
  }

  @Override
  public NamedByteSource getResource(String name) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Class loadClass(String className) throws ClassNotFoundException {
    return classLoader.loadClass(className);
  }

  @Override
  public FileObject resolvePackageRoot(FileSystemManager fileSystemManager) throws FileSystemException {
    return fileSystemManager.toFileObject(packageRoot);
  }
}
