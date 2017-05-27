
package com.jfixby.red.engine.core.resources;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.log.L;

public class UserAssets {

	Map<AssetUser, List<ID>> reg = Collections.newMap();

	public void addAsset (final AssetUser user, final ID asset_id) {
		List<ID> assets_by_user = this.reg.get(user);
		if (assets_by_user == null) {
			assets_by_user = Collections.newList();
			this.reg.put(user, assets_by_user);
		}
		assets_by_user.add(asset_id);
	}

	public boolean removeAsset (final AssetUser user, final ID asset_id) {
		final List<ID> assets_by_user = this.reg.get(user);
		if (assets_by_user == null) {
			L.e("There is no information about this AssetsConsumer", user.consumer);
			L.e("            a nd it's relationship with the asset", asset_id);
			return false;
		}
		assets_by_user.remove(asset_id);
		if (assets_by_user.size() == 0) {
			this.reg.remove(user);
		}
		return true;
	}

	public void print () {
		this.reg.print("assets used");
	}

	public Collection<ID> listAssetsUsedBy (final AssetUser consumer) {
		final List<ID> result = this.reg.get(consumer);
		if (result == null) {
			return Collections.newList();
		}
		return result;
	}
}
