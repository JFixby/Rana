
package com.jfixby.rana.api.pkg;

import java.io.IOException;

public interface PackagesTank {

	PackageSearchResult findPackages (PackageSearchParameters search_params);

	void rebuildIndex () throws IOException;

	public String getName ();

	void printIndex ();

	String getShortName ();

	void installPackage (PackagePackingArgs pkgSpec) throws IOException;

}
