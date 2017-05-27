
package com.jfixby.rana.api.asset;

import com.jfixby.rana.api.pkg.PackageReaderListener;
import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public class AssetsManager {

	public static final String UseAssetSandBox = "UseAssetSandBox";
	public static final String ReportUnusedAssets = "ReportUnusedAssets";

	static private ComponentInstaller<AssetsManagerComponent> componentInstaller = new ComponentInstaller<AssetsManagerComponent>(
		"AssetsManager");

	public static final void installComponent (final AssetsManagerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final AssetsManagerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final AssetsManagerComponent component () {
		return componentInstaller.getComponent();
	}

	public static void registerAssetsContainer (final SealedAssetsContainer container) {
		invoke().registerAssetsContainer(container);
	}

	public static void unRegisterAssetsContainer (final SealedAssetsContainer container) {
		invoke().unRegisterAssetsContainer(container);
	}

	public static AssetHandler obtainAsset (final ID asset_id, final AssetsConsumer consumer) {
		return invoke().obtainAsset(asset_id, consumer);
	}

	public static void releaseAsset (final AssetHandler handler, final AssetsConsumer consumer) {
		invoke().releaseAsset(handler, consumer);
	}

	public static void releaseAllAssets (final AssetsConsumer consumer) {
		invoke().releaseAllAssets(consumer);
	}

	public static void printAllLoadedAssets () {
		invoke().printAllLoadedAssets();
	}

// public static void checkAll () {
// invoke().checkAll();
// }

	public static boolean autoResolveAsset (final ID dependency, final PackageReaderListener listener) {
		return invoke().autoResolveAsset(dependency, listener);
	}

	public static void autoResolveAssets (final Collection<ID> dependencies, final PackageReaderListener listener) {
		invoke().autoResolveAssets(dependencies, listener);
	}

	public static boolean isRegisteredAsset (final ID dependency) {
		return invoke().isRegisteredAsset(dependency);
	}

	public static void purge () {
		invoke().purge();
	}

}
