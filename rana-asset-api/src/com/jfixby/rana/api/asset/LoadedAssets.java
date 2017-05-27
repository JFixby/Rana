
package com.jfixby.rana.api.asset;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public class LoadedAssets {

	public static final String ReportUnusedAssets = "ReportUnusedAssets";

	static private ComponentInstaller<LoadedAssetsComponent> componentInstaller = new ComponentInstaller<LoadedAssetsComponent>(
		"LoadedAssets");

	public static final void installComponent (final LoadedAssetsComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final LoadedAssetsComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final LoadedAssetsComponent component () {
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
