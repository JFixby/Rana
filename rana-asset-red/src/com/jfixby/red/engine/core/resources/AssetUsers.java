
package com.jfixby.red.engine.core.resources;

import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class AssetUsers {

	final Map<ID, List<AssetUser>> asset_users = Collections.newMap();
	private final RedLoadedAssets master;

	public AssetUsers (final RedLoadedAssets redAssetsManager) {
		this.master = redAssetsManager;
	}

	public void print (final String tag) {
		this.asset_users.print(tag);
	}

	public void addUser (final ID asset_id, final AssetUser user) {
		List<AssetUser> users = this.asset_users.get(asset_id);
		if (users == null) {
			users = Collections.newList();
			this.asset_users.put(asset_id, users);
		}
		{
			users.add(user);
		}
	}

	public void addUser (final ID asset_id) {
		List<AssetUser> users = this.asset_users.get(asset_id);
		if (users == null) {
			users = Collections.newList();
			this.asset_users.put(asset_id, users);
		}
	}

	public boolean removeUser (final ID asset_id, final AssetUser user) {
		final List<AssetUser> users = this.asset_users.get(asset_id);
		if (users == null) {
			L.e("There is no information about this asset usage", asset_id);
			L.e("     and especially it's relationship with the", user.consumer);
			return false;
		}
		users.remove(user);
		if (users.size() == 0) {
			final boolean reportUnused = SystemSettings.getFlag(LoadedAssets.ReportUnusedAssets);
			if (reportUnused) {
				L.e("Unused asset", asset_id);
			}
		}
		return true;
	}

	public int getNumberOfUsers (final ID key) {
		final List<AssetUser> users = this.asset_users.get(key);
		if (users == null) {
			return 0;
		} else {
			return users.size();
		}
	}

	public void removeAll (final List<ID> keys) {
		this.asset_users.removeAll(keys);
	}

}
