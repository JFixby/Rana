
package com.jfixby.rana.api.asset;

import java.io.IOException;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface AssetsManagerComponent {

	void autoResolveAsset (ID dependency) throws IOException;

	void autoResolveAssets (Collection<ID> dependencies) throws IOException;

	AssetsPurgeResult purge ();

}
