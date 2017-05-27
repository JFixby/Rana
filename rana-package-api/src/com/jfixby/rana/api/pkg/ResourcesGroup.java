
package com.jfixby.rana.api.pkg;

import java.io.IOException;

import com.jfixby.scarabei.api.assets.ID;

public interface ResourcesGroup {

	ID getName ();

	void printAllIndexes ();

	PackageSearchResult findPackages (PackageSearchParameters search_params);

	void rebuildAllIndexes () throws IOException;

}
