
package com.jfixby.rana.api.pkg;

import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.scarabei.api.file.File;

public interface PackageReaderInput {

	PackageReaderListener getPackageReaderListener ();

	File getRootFile ();

	PackageHandler getPackageHandler ();

	AssetsContainer getStorage ();

}
