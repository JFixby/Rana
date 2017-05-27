
package com.jfixby.rana.api.asset;

import java.io.IOException;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public class AssetsManager {

	public static final String AutoresolveDependencies = "AutoresolveDependencies";

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

	public static void autoResolveAsset (final ID dependency) throws IOException {
		invoke().autoResolveAsset(dependency);
	}

	public static void autoResolveAssets (final Collection<ID> dependencies) throws IOException {
		invoke().autoResolveAssets(dependencies);
	}

}
