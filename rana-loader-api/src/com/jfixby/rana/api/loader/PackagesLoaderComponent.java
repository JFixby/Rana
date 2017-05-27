
package com.jfixby.rana.api.loader;

import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.scarabei.api.collections.Collection;

public interface PackagesLoaderComponent {
	Collection<PackageReader> findPackageReaders (PackageFormat format);

	void registerPackageReader (PackageReader loader);

	Collection<PackageFormat> listAcceptablePackageFormats ();

	void printInstalledPackageReaders ();
}
