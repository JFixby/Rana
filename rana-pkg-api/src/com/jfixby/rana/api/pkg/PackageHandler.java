
package com.jfixby.rana.api.pkg;

import java.io.IOException;

import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.file.File;

public interface PackageHandler {

	public String getPackageName ();

	public PackageVersion getVersion ();

	public PackageFormat getFormat ();

	public PACKAGE_STATUS getStatus ();

	public Collection<ID> listPackedAssets ();

	public Collection<ID> listDependencies ();

	public void print ();

	public void install (PackageInstallerListener reader_listener);

	public File getRootFile (boolean sandBoxMode) throws IOException;

}
