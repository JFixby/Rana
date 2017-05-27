
package com.jfixby.rana.api.asset;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface AbstractAssetsContainer {

	Collection<ID> listAssets ();

	Asset getAsset (ID asset_id);

	void printAll ();

// public AssetsContainerOwner getOwner ();

// long getPackageTimeStamp ();
//
// long readPackageTimeStamp ();
//
// boolean purgeAssets (Collection<ID> relatedAssets);

}
