
package com.jfixby.rana.api.pkg;

import java.io.IOException;

public interface Resource {

	PackageSearchResult findPackages (PackageSearchParameters search_params);

	void rebuildIndex () throws IOException;

	public String getName ();

	void printIndex ();

}
