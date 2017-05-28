
package com.jfixby.red.engine.core.resources;

import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetsGroup;
import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.debug.Debug;

public class AssetsContainerOwner {

	private final SealedAssetsContainer container;

	@Override
	public String toString () {
		return "AssetsContainerOwner[" + this.container + "]";
	}

	public AssetsContainerOwner (final SealedAssetsContainer container) {
		this.container = container;
	}

	public void onAssetsUnload (final SealedAssetsContainer c) {
		Debug.checkTrue(this.container == c);
		final Set<AssetsGroup> G = Collections.newSet();
		for (final ID id : this.container.listAssets()) {
			final Asset a = this.container.getAsset(id);
			G.add(a.getGroup());
		}
		for (final AssetsGroup g : G) {
			g.dispose();
		}
	}

}
