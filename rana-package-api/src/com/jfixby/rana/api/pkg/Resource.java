
package com.jfixby.rana.api.pkg;

public interface Resource {

	PackageSearchResult findPackages (PackageSearchParameters search_params);

	void rebuildIndex (ResourceRebuildIndexListener listener);

	public String getName ();

	void printIndex ();

}
