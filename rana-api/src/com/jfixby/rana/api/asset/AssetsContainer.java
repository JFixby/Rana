
package com.jfixby.rana.api.asset;

import com.jfixby.rana.api.asset.AbstractAssetsContainer;
import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.assets.ID;

public interface AssetsContainer extends AbstractAssetsContainer {

	void addAsset (ID assetID, Asset data);

	public SealedAssetsContainer seal ();

}
