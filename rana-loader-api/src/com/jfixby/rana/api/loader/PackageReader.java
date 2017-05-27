
package com.jfixby.rana.api.loader;

import java.io.IOException;

import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.scarabei.api.collections.Collection;

public interface PackageReader {
	Collection<PackageFormat> listAcceptablePackageFormats ();

	void doReadPackage (PackageReaderInput input) throws IOException;
}
