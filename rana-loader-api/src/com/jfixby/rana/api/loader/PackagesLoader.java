
package com.jfixby.rana.api.loader;

import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.collections.Collection;

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

	public static Collection<PackageReader> findPackageReaders (final PackageFormat format) {
		return invoke().findPackageReaders(format);
	}

	public static Collection<PackageFormat> listAcceptablePackageFormats () {
		return invoke().listAcceptablePackageFormats();
	}

	public static void registerPackageReader (final PackageReader reader) {
		invoke().registerPackageReader(reader);
	}

	public static void printInstalledPackageReaders () {
		invoke().printInstalledPackageReaders();
	}
}
