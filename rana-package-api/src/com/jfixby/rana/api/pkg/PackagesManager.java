
package com.jfixby.rana.api.pkg;

import java.io.IOException;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;

public class PackagesManager {

	static private ComponentInstaller<PackagesManagerComponent> componentInstaller = new ComponentInstaller<PackagesManagerComponent>(
		"PackagesManager");

	public static final void installComponent (final PackagesManagerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final PackagesManagerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final PackagesManagerComponent component () {
		return componentInstaller.getComponent();
	}

	public static PackageSearchResult findPackages (final PackageSearchParameters search_params) {
		return invoke().findPackages(search_params);
	}

// public static void updateAll (final ResourceRebuildIndexListener listener) {
// invoke().updateAll(listener);
// }

	public static void printAllPackages () {
		invoke().printAllPackages();
	}

// public static PackageFormat newPackageFormat (final String format_name) {
// return invoke().newPackageFormat(format_name);
// }

// public static Collection<PackageReader> findPackageReaders (final PackageFormat format) {
// return invoke().findPackageReaders(format);
// }
//
// public static void registerPackageReader (final PackageReader loader) {
// invoke().registerPackageReader(loader);
// }

// public static Collection<PackageFormat> listAcceptablePackageFormats () {
// return invoke().listAcceptablePackageFormats();
// }

// public static void printAllPackageReaders () {
// invoke().printInstalledPackageReaders();
// }

	public static AssetsTankSpecs newResourceSpecs () {
		return invoke().newResourceSpecs();
	}

	public static PackagesTank newResource (final AssetsTankSpecs specs) throws IOException {
		return invoke().newResource(specs);
	}

// public static Resource getResource (final String name) {
// return invoke().getResource(name);
// }

	public static void printAllResources () {
		invoke().printAllResources();
	}

	public static void printAllIndexes () {
		invoke().printAllIndexes();
	}

	public static PackagesBank getBank (final ID name) {
		return invoke().getBank(name);
	}

}
