
package com.jfixby.rana.api.pkg;

import com.jfixby.scarabei.api.assets.ID;

public interface ResourcesGroup {

	ID getName ();

	void printAllIndexes ();

	PackageSearchResult findPackages (PackageSearchParameters search_params);

	void rebuildAllIndexes (ResourceRebuildIndexListener listener);

}
