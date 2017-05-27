package com.jfixby.rana.api.loader;

import com.jfixby.scarabei.api.ComponentInstaller;

public class PackagesLoader {

	static private ComponentInstaller<PackagesLoaderComponent> componentInstaller = new ComponentInstaller<PackagesLoaderComponent>(
		"PackagesLoader");

	public static final void installComponent (final PackagesLoaderComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final PackagesLoaderComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final PackagesLoaderComponent component () {
		return componentInstaller.getComponent();
	}
}
