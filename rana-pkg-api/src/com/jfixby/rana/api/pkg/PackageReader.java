
package com.jfixby.rana.api.pkg;

import java.io.IOException;

import com.jfixby.scarabei.api.collections.Collection;

public interface PackageReader {

	Collection<PackageFormat> listAcceptablePackageFormats ();

	void doReadPackage (PackageReaderInput input) throws IOException;

}
