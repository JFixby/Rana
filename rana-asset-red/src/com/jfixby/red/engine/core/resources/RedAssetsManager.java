
package com.jfixby.red.engine.core.resources;

import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.rana.api.asset.AssetsManagerComponent;
import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.CollectionFilter;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class RedAssetsManager implements AssetsManagerComponent {

	final AssetUsers asset_users = new AssetUsers(this);

	final Assets assets = new Assets();
// final Map<AssetsContainer, Set<AssetID>> containers = Collections.newMap();
	int i = 0;
	final AssetsConsumer stub_consumer = new AssetsConsumer() {
		{

		}
	};

// @Override
// public void checkAll () {
// for (this.i = 0; this.i < this.containers.size(); this.i++) {
// final AssetsContainer container = this.containers.getKeyAt(this.i);
// container.checkAll();
// }
// }

	final UserAssets user_assets = new UserAssets();

// @Override
// public boolean autoResolveAsset (final ID dependency, final PackageReaderListener listener) {
// Debug.checkNull("PackageReaderListener", listener);
//
// final AssetHandler asset_entry = AssetsManager.obtainAsset(dependency, this.stub_consumer);
// L.e("AssetsConsumer leak public boolean autoResolveAsset (final ID dependency, final PackageReaderListener listener)");
//
// if (asset_entry != null) {
// AssetsManager.releaseAsset(asset_entry, this.stub_consumer);
// return true;
// }
// L.e("Asset[" + dependency + "] delays loading since it is not pre-loaded.");
//// Debug.printCallStack();
//// ResourcesManager.updateAll();
// final boolean success = this.resolve(dependency, true, listener);
// if (!success) {
// L.e("Asset[" + dependency + "] was not resolved!");
// }
// return success;
// }
//
// @Override
// public void autoResolveAssets (final Collection<ID> dependencies, final PackageReaderListener listener) {
// Debug.checkNull(listener);
// Debug.checkNull("dependencies", dependencies);
// boolean updated = true;
// for (final ID dependency : dependencies) {
//
// final AssetHandler asset_entry = AssetsManager.obtainAsset(dependency, this.stub_consumer);
// L.e(
// "AssetsConsumer leak public void autoResolveAssets (final Collection<ID> dependencies, final PackageReaderListener listener)");
//
// if (asset_entry != null) {
// L.d("already loaded", dependency);
// AssetsManager.releaseAsset(asset_entry, this.stub_consumer);
// continue;
// }
// if (!updated) {
// // ResourcesManager.updateAll();
// updated = true;
// }
// this.resolve(dependency, true, listener);
// }
// }

	@Override
	public boolean isRegisteredAsset (final ID dependency) {
		Debug.checkNull("asset_id", dependency);
		final RedAssetHandler asset = this.assets.get(dependency);
		return asset != null;
	}

	@Override
	public AssetHandler obtainAsset (final ID asset_id, final AssetsConsumer consumer) {
		Debug.checkNull("asset_id", asset_id);
		Debug.checkNull("consumer", consumer);
		final RedAssetHandler asset = this.assets.get(asset_id);
		if (asset == null) {
			// assets.print("available assets");
			L.e("Asset not found <" + asset_id + "> (https://github.com/JFixby/RedTriplane/issues/7)");
			return null;
		} else {
			final AssetUser user = new AssetUser(consumer);
			this.asset_users.addUser(asset_id, user);
			this.user_assets.addAsset(user, asset_id);
			// asset_users.print("on obtainAsset");
			return asset;
		}
	}

	@Override
	public void printAllLoadedAssets () {
		this.assets.print("all loaded assets");
	}

	@Override
	public void printUsages () {
		this.asset_users.print("asset_users");
		this.user_assets.print();
	}

// public void purgeAssets (final Collection<ID> assetsToDrop) {
// this.assets.purgeAssets(assetsToDrop);
// }

	public void registerAssetContainer (final ID asset_id, final SealedAssetsContainer container) {
		Debug.checkNull("asset_id", asset_id);
		Debug.checkNull("container", container);

// Set<AssetID> set = this.containers.get(asset_id);
// if (set == null) {
// set = Collections.newSet();
// this.containers.put(container, set);
// }
// set.add(asset_id);

		if (this.assets.main_registry.containsKey(asset_id)) {
			this.printAllLoadedAssets();
			Err.reportError("Asset is already loaded " + asset_id);
		}

		final RedAssetHandler info = new RedAssetHandler(asset_id);
		this.assets.put(asset_id, info);
		this.asset_users.addUser(asset_id);
		info.setAssetContainer(container);

	}

	@Override
	public void registerAssetsContainer (final SealedAssetsContainer container) {
		final Collection<ID> list = container.listAssets();
		for (final ID rester_id : list) {
			this.registerAssetContainer(rester_id, container);
		}
	}

	@Override
	public void releaseAllAssets (final AssetsConsumer consumer) {
		Debug.checkNull("consumer", consumer);
		final AssetUser user = new AssetUser(consumer);
		final Collection<ID> assets_list = Collections.newList(this.user_assets.listAssetsUsedBy(user));
		if (assets_list == null) {
			L.d("AssetsConsumer: " + user.consumer, "is not using any assets");
			this.user_assets.print();
			this.asset_users.print("on releaseAllAssets");
			return;
		}
		for (final ID asset_id : assets_list) {
			final RedAssetHandler asset_info = this.assets.get(asset_id);
			if (asset_info == null) {
				this.asset_users.print("on releaseAllAssets");
				this.user_assets.print();
				Err.reportError("Assets usage register is corrupted. Asset <" + asset_id + "> not found.");
			}
			this.releaseAsset(asset_info, consumer);
		}

	}

	@Override
	public void releaseAsset (final AssetHandler asset_info, final AssetsConsumer consumer) {
		Debug.checkNull("asset_info", asset_info);
		final ID asset_id = asset_info.ID();
		Debug.checkNull("asset_id", asset_id);
		Debug.checkNull("consumer", consumer);
		final RedAssetHandler asset = this.assets.get(asset_id);
		if (asset == null) {
			Err.reportError("Asset " + asset_id + " is not registred here");
		}

		final AssetUser user = new AssetUser(consumer);
		final boolean succ_a = this.asset_users.removeUser(asset_id, user);
		final boolean succ_u = this.user_assets.removeAsset(user, asset_id);
		if (succ_a != succ_u) {
			this.asset_users.print("on releaseAsset");
			this.user_assets.print();
			Err.reportError("Assets usage register is corrupted");
		}
		// asset_users.print("on releaseAsset");

	}

// private boolean resolve (final ID dependency, final boolean print_debug_output, final PackageReaderListener listener) {
//
//// L.d("RESOLVING DEPENDENCY", dependency);
// final PackageSearchParameters search_params = ResourcesManager.newSearchParameters();
// search_params.setAssetId(dependency);
//
// final PackageSearchResult search_result = ResourcesManager.findPackages(search_params);
// if (print_debug_output) {
// // search_result.print();
//
// // this.printAllLoadedAssets();
// // L.d();
// }
// if (search_result.isEmpty()) {
// final String msg = "Asset [" + dependency + "] was not found in any package.";
//// L.e()
// L.d(msg);
//
// if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
// ResourcesManager.printAllPackages();
// }
//
// if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
// ResourcesManager.printAllIndexes();
// }
// Err.reportError(msg);
// //
// }
//
// final PackageHandler package_handler = search_result.getBest();
// // package_handler.print();
//
// final PACKAGE_STATUS package_status = package_handler.getStatus();
// if (PACKAGE_STATUS.NOT_INSTALLED == package_status) {
// package_handler.install(listener);
// }
//
// final PackageFormat format = package_handler.getFormat();
// final Collection<PackageReader> package_loaders = ResourcesManager.findPackageReaders(format);
// if (package_loaders.isEmpty()) {
// ResourcesManager.printAllPackageReaders();
// L.e("Failed to read package", package_handler);
// Err.reportError("No package reader for " + format);
// //
// }
//
// final PackageReader package_reader = package_loaders.getLast();
//// final DebugTimer debigTimer = Debug.newTimer();
//// debigTimer.reset();
// package_handler.doReadPackage(listener, package_reader);
//// debigTimer.printTimeAbove(50L, "LOAD-TIME: Asset[" + dependency + "] loaded");
//
// return true;
// }

	public void unRegisterAssetContainer (final ID asset, final SealedAssetsContainer container) {
		this.assets.remove(asset, container);
	}

	@Override
	public void unRegisterAssetsContainer (final SealedAssetsContainer container) {
		final Collection<ID> list = container.listAssets();
		for (final ID asset : list) {
			this.unRegisterAssetContainer(asset, container);
		}
	}

	@Override
	public AssetHandler useAsset (final ID asset_id) {
		return this.assets.get(asset_id);
	}

	@Override
	public Collection<SealedAssetsContainer> listUnusedContainers () {
		final Set<SealedAssetsContainer> unusedContainers = Collections.newSet();
		final List<ID> unusedAssets = this.assets.keys().filter(new CollectionFilter<ID>() {
			@Override
			public boolean fits (final ID key) {
				return RedAssetsManager.this.asset_users.getNumberOfUsers(key) == 0;
			}
		});
		final List<ID> assetsToDrop = unusedAssets;
		final Map<SealedAssetsContainer, Set<ID>> related = Collections.newMap();
		for (int i = 0; i < assetsToDrop.size(); i++) {
			final ID asset = (assetsToDrop.getElementAt(i));
			final RedAssetHandler handler = this.assets.get(asset);
			final SealedAssetsContainer container = handler.getContainer();
			Set<ID> set = related.get(container);
			if (set == null) {
				set = Collections.newSet();
				related.put(container, set);
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
// container.purgeAssets(relatedAssets);
			final Collection<ID> assets = container.listAssets();
			final Set<ID> assetsToPurge = relatedAssets;
			if (!assetsToPurge.containsAll(assets)) {
				continue;
			}
			if (!assets.containsAll(assetsToPurge)) {
				assets.print("  assets");
				assetsToPurge.print("to purge");
				Err.reportError("Not equal sets");
				continue;
			}
// this.tryToPurgeContainer(relatedAssets, container);
			unusedContainers.add(container);

		}

		return unusedContainers;
	}

	@Override
	public void unRegisterAssetsContainers (final Collection<SealedAssetsContainer> containers) {
		for (final SealedAssetsContainer c : containers) {
			this.unRegisterAssetsContainer(c);
		}
	}

	@Override
	public AssetsContainer newAssetsContainer () {
		return new RedAssetsContainer();
	}

}
