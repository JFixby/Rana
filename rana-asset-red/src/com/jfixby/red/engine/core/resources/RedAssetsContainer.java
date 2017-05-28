
package com.jfixby.red.engine.core.resources;

import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;

public class RedAssetsContainer implements SealedAssetsContainer, AssetsContainer {

	final Map<ID, Asset> assets = Collections.newMap();

	boolean sealed = false;

	@Override
	public Asset getAsset (final ID asset_id) {
		return this.assets.get(asset_id);
	}

	@Override
	public void printAll () {
		this.assets.print("assets container");
	}

	@Override
	public Collection<ID> listAssets () {
		return this.assets.keys();
	}

	@Override
	public void addAsset (final ID raster_id, final Asset data) {
		Debug.checkTrue(!this.sealed);
		Debug.checkNull("raster_id", raster_id);
		Debug.checkNull("data", data);
		this.assets.put(raster_id, data);
	}

	@Override
	public SealedAssetsContainer seal () {
		this.sealed = true;
		return this;
	}

}
