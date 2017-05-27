
package com.jfixby.rana.api.asset;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public class AssetsManager {

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

	public static void unRegisterAssetsContainers (final Collection<SealedAssetsContainer> containers) {
		invoke().unRegisterAssetsContainers(containers);
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

	public static boolean isRegisteredAsset (final ID dependency) {
		return invoke().isRegisteredAsset(dependency);
	}

	public static Collection<SealedAssetsContainer> listUnusedContainers () {
		return invoke().listUnusedContainers();
	}

	public static AssetsContainer newAssetsContainer () {
		return invoke().newAssetsContainer();
	}

}
