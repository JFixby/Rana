
package com.jfixby.rana.test;

import java.io.IOException;

import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.rana.api.loader.PackagesLoader;
import com.jfixby.rana.api.pkg.PackageHandler;
import com.jfixby.rana.api.pkg.PackageSearchParameters;
import com.jfixby.rana.api.pkg.PackageSearchResult;
import com.jfixby.rana.api.pkg.PackagesManager;
import com.jfixby.rana.red.loader.RedPackagesLoader;
import com.jfixby.red.engine.core.resources.RedAssetsManager;
import com.jfixby.red.engine.core.resources.RedLoadedAssets;
import com.jfixby.red.triplane.resources.fsbased.RedPackageManager;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManagerSpecs;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.gson.GoogleGson;

public class TestRana {

	public static void main (final String[] args) throws IOException, IOException {
		ScarabeiDesktop.deploy();
		final String java_path = "D:/[DATA]/[RED-ASSETS]/Art-Private/tinto-assets/assets/com.jfixby.tinto.assets";
		final File bankFolder = LocalFileSystem.newFile(java_path);
		Json.installComponent(new GoogleGson());
// bankFolder.listAllChildren().print("files");

		final File assets_cache_folder = LocalFileSystem.ApplicationHome().child("assets-cache");
		final File assets_folder = LocalFileSystem.ApplicationHome().child("assets");

		final RedResourcesManagerSpecs resman_spec = new RedResourcesManagerSpecs();
		resman_spec.assets_cache_folder = assets_cache_folder;
		resman_spec.assets_folder = assets_folder;
		resman_spec.readResourcesConfigFile = true;
		final RedPackageManager resman = new RedPackageManager(resman_spec);
		PackagesManager.installComponent(resman);
		LoadedAssets.installComponent(new RedLoadedAssets());
		PackagesLoader.installComponent(new RedPackagesLoader());
		AssetsManager.installComponent(new RedAssetsManager());
		AssetsManager.autoResolveAsset(Names.newID("com.jfixby.tinto.preloader.psd.raster_0"));
// PackagesManager.printAllIndexes();

		final PackageFormat format = new PackageFormat("test");
		final PackageSearchParameters search_params = new PackageSearchParameters();
		search_params.getAllFlag = (true);
		search_params.acceptablePackageFormats.add(format);
		final PackageSearchResult resiult = PackagesManager.findPackages(search_params);
// resiult.print();

		final PackageHandler packagebest = resiult.getBest();

		final Collection<PackageReader> readers = PackagesLoader.findPackageReaders(format);
		final PackageReader reader = readers.getLast();

		final PackageReaderInput readArgs = new PackageReaderInput();
		readArgs.packageRootFile = packagebest.getRootFile(true);
		readArgs.assetsContainer = LoadedAssets.newAssetsContainer();
		reader.doReadPackage(readArgs);
		LoadedAssets.registerAssetsContainer(readArgs.assetsContainer.seal());
		final Collection<SealedAssetsContainer> unused = LoadedAssets.listUnusedContainers();
		unused.print("unused");
		LoadedAssets.unRegisterAssetsContainers(unused);

		PackagesLoader.printInstalledPackageReaders();

// L.d();f
// packagebest.print();
//
// final SealedAssetsContainer container = null;
// AssetsManager.registerAssetsContainer(container);

	}

}
