
package com.jfixby.rana.api.asset;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface AssetsManagerComponent {

	AssetHandler obtainAsset (ID asset_id, AssetsConsumer consumer);

	void releaseAsset (AssetHandler handler, AssetsConsumer consumer);

	void releaseAllAssets (AssetsConsumer consumer);

	void printAllLoadedAssets ();

	AssetHandler useAsset (ID spriteAssetID);

	boolean isRegisteredAsset (ID dependency);

// void purge ();

	void printUsages ();

	Collection<SealedAssetsContainer> listUnusedContainers ();

	void registerAssetsContainer (SealedAssetsContainer container);

	void unRegisterAssetsContainer (SealedAssetsContainer container);

	void unRegisterAssetsContainers (Collection<SealedAssetsContainer> containers);

}
