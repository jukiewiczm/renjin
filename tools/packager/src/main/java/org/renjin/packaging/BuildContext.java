package org.renjin.packaging;

import org.renjin.primitives.packaging.PackageLoader;

import java.io.File;
import java.util.List;

/**
 * Provides general access to the build environment, whether
 * that is Maven, Gradle, etc.
 */
public interface BuildContext {
  
  BuildLogger getLogger();

  void setupNativeCompilation();


  /**
   * @return the path to the GCC Bridge plugin
   */
  File getGccBridgePlugin();

  /**
   * 
   * @return the path to the unpacked GNU R Home.
   */
  File getGnuRHomeDir();

  /**
   * 
   * @return the path to the unpacked include files of this package's dependencies.
   */
  File getUnpackedIncludesDir();

  /**
   * @return the output directory for Java class files.
   */
  File getOutputDir();


  /**
   * Returns the directory where package output is to be written.
   * 
   * <p>This will be a sub directory of the java output directory that includes the groupId as the 
   * (java) package. For example, if the output directory is {@code target/classes}, and the groupId of the package
   * is {@code org.renjin.cran} and the package name {@code Matrix}, then this directory will be 
   * {@code target/classes/org/renjin/cran/Matrix}</p>
   */
  File getPackageOutputDir();


  PackageLoader getPackageLoader();

  /**
   * @return the class loader to use for evaluating R sources
   */
  ClassLoader getClassLoader();

  /**
   * 
   * @return the nmaes of the packages that should be on the search path when the 
   * 
   */
  List<String> getDefaultPackages();
}
