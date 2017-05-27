
package com.jfixby.rana.api.asset;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface AssetsManagerComponent {

	void autoResolveAsset (ID dependency) throws AssetResolutionException;

	void autoResolveAssets (Collection<ID> dependencies) throws AssetResolutionException;
}
