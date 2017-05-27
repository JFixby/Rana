
package com.jfixby.rana.api.asset;

import com.jfixby.scarabei.api.ComponentInstaller;

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

}
