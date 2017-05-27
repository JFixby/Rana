
package com.jfixby.rana.api.pkg;

import java.io.IOException;

import com.jfixby.rana.api.asset.Asset;
import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public class RanaPackageManager {

	static private ComponentInstaller<RanaPackageManagerComponent> componentInstaller = new ComponentInstaller<RanaPackageManagerComponent>(
		"ResourcesManager");

	public static final void installComponent (final RanaPackageManagerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final RanaPackageManagerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final RanaPackageManagerComponent component () {
		return componentInstaller.getComponent();
	}

	public static PackageSearchParameters newSearchParameters () {
		return invoke().newSearchParameters();
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

	public static PackageFormat newPackageFormat (final String format_name) {
		return invoke().newPackageFormat(format_name);
	}

	public static Collection<PackageReader> findPackageReaders (final PackageFormat format) {
		return invoke().findPackageReaders(format);
	}

	public static <T extends Asset> void registerPackageReader (final PackageReader loader) {
		invoke().registerPackageReader(loader);
	}

	public static Collection<PackageFormat> listAcceptablePackageFormats () {
		return invoke().listAcceptablePackageFormats();
	}

	public static void printAllPackageReaders () {
		invoke().printInstalledPackageReaders();
	}

	public static ResourceSpecs newResourceSpecs () {
		return invoke().newResourceSpecs();
	}

	public static Resource newResource (final ResourceSpecs specs) throws IOException {
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

	public static ResourcesGroup getResourcesGroup (final ID name) {
		return invoke().getResourcesGroup(name);
	}

	public static DeployRemoteBanksTask prepareDeployRemoteBanksTask () {
		return invoke().prepareDeployRemoteBanksTask();
	}

}
