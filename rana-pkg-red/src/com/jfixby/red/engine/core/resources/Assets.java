
package com.jfixby.red.engine.core.resources;

import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.debug.Debug;

public class Assets {

	final Map<ID, RedAssetHandler> main_registry = Collections.newMap();

	final public void put (final ID asset_id, final RedAssetHandler info) {
		this.main_registry.put(asset_id, info);
	}

	public void remove (final ID asset, final SealedAssetsContainer container) {
		final RedAssetHandler handler = this.main_registry.get(asset);
		Debug.checkTrue(handler.getContainer() == container);
		this.main_registry.remove(asset);
	}

	final public RedAssetHandler get (final ID asset_id) {
		return this.main_registry.get(asset_id);
	}

	final public void print (final String tag) {
		Collections.newMap(this.main_registry).print(tag);
	}

	public void purgeAssets (final Collection<ID> assetsToDrop) {
		final Map<SealedAssetsContainer, Set<ID>> related = Collections.newMap();
		for (int i = 0; i < assetsToDrop.size(); i++) {
			final ID asset = (assetsToDrop.getElementAt(i));
			final SealedAssetsContainer handler = this.main_registry.get(asset).getContainer();
			Set<ID> set = related.get(handler);
			if (set == null) {
				set = Collections.newSet();
				related.put(handler, set);
			}
			set.add(asset);
		}

// related.print("related");
// this.main_registry.print("main_registry");
// related.keys().print("keys");
// final RedAssetAssetHandler key0 = related.keys().getElementAt(0);
// final RedAssetAssetHandler key1 = related.keys().getElementAt(1);
// L.d("key0 == key1", key0 == key1);
// L.d("key0 eq key1", key0.equals(key1));

		for (int i = 0; i < related.size(); i++) {
			final SealedAssetsContainer container = related.getKeyAt(i);
			final Set<ID> relatedAssets = related.get(container);
// final AssetsContainer container = handler.getContainer();
// relatedAssets.print("purge: " + container);
			container.purgeAssets(relatedAssets);
		}
	}

	public Collection<ID> keys () {
		return this.main_registry.keys();
	}

}
