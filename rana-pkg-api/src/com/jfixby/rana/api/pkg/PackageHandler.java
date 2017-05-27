
package com.jfixby.rana.api.pkg;

import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface PackageHandler {

	public String getPackageName ();

	public PackageVersion getVersion ();

	public PackageFormat getFormat ();

	public PACKAGE_STATUS getStatus ();

	public Collection<ID> listPackedAssets ();

	public Collection<ID> listDependencies ();

	public void print ();

	public void install (PackageReaderListener reader_listener);

	public SealedAssetsContainer doReadPackage (PackageReaderListener reader_listener, PackageReader reader);

	public boolean isLoaded ();

}
