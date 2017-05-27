
package com.jfixby.red.engine.core.resources;

import java.io.IOException;

import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.AssetsManagerComponent;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.rana.api.loader.PackagesLoader;
import com.jfixby.rana.api.pkg.PACKAGE_STATUS;
import com.jfixby.rana.api.pkg.PackageHandler;
import com.jfixby.rana.api.pkg.PackageSearchParameters;
import com.jfixby.rana.api.pkg.PackageSearchResult;
import com.jfixby.rana.api.pkg.PackagesManager;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.settings.ExecutionMode;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RedAssetsManager implements AssetsManagerComponent {
	final AssetsConsumer stub_consumer = new StubAssetsConsumer();

	@Override
	public void autoResolveAsset (final ID dependency) throws IOException {

		final AssetHandler asset_entry = LoadedAssets.obtainAsset(dependency, this.stub_consumer);
		L.e("AssetsConsumer leak public boolean autoResolveAsset (final ID dependency, final PackageReaderListener listener)");

		if (asset_entry != null) {
			LoadedAssets.releaseAsset(asset_entry, this.stub_consumer);
			return;
		}
		L.e("Asset[" + dependency + "] delays loading since it is not pre-loaded.");
// Debug.printCallStack();
// ResourcesManager.updateAll();

		try {
			this.resolve(dependency, true);
		} catch (final IOException e) {
			throw new IOException("Failed to resolve asset[" + dependency + "]", e);
		}

	}

	private void resolve (final ID dependency, final boolean print_debug_output) throws IOException {

		// L.d("RESOLVING DEPENDENCY", dependency);
		final PackageSearchParameters search_params = new PackageSearchParameters();
		search_params.asset_to_find = dependency;

		final PackageSearchResult search_result = PackagesManager.findPackages(search_params);
		if (print_debug_output) {
			// search_result.print();

			// this.printAllLoadedAssets();
			// L.d();
		}
		if (search_result.isEmpty()) {
			final String msg = "Asset [" + dependency + "] was not found in any package.";
			// L.e()
			L.d(msg);

			if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
				PackagesManager.printAllPackages();
			}

			if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
				PackagesManager.printAllIndexes();
			}
// Err.reportError(msg);
			throw new IOException(msg);
			//
		}

		final PackageHandler package_handler = search_result.getBest();
		// package_handler.print();

		final PACKAGE_STATUS package_status = package_handler.getStatus();
		if (PACKAGE_STATUS.NOT_INSTALLED == package_status) {
			package_handler.install();
		}

		final PackageFormat format = package_handler.getFormat();
		final Collection<PackageReader> package_loaders = PackagesLoader.findPackageReaders(format);
		if (package_loaders.isEmpty()) {
			PackagesLoader.printInstalledPackageReaders();
// L.e("Failed to read package", package_handler);
			throw new IOException("Failed to read package: " + package_handler + " No package reader for " + format);
			//
		}

		final PackageReader package_reader = package_loaders.getLast();
		// final DebugTimer debigTimer = Debug.newTimer();
		// debigTimer.reset();

		final PackageReaderInput readArgs = new PackageReaderInput();
		readArgs.packageRootFile = package_handler.getRootFile(true);
		readArgs.assetsContainer = LoadedAssets.newAssetsContainer();
		package_reader.doReadPackage(readArgs);

// package_handler.doReadPackage(listener, package_reader);
// debigTimer.printTimeAbove(50L, "LOAD-TIME: Asset[" + dependency + "] loaded");

// return true;
	}

	@Override
	public void autoResolveAssets (final Collection<ID> dependencies) throws IOException {
		Debug.checkNull("dependencies", dependencies);
// boolean updated = true;
		for (final ID dependency : dependencies) {

			final AssetHandler asset_entry = LoadedAssets.obtainAsset(dependency, this.stub_consumer);
			L.e(
				"AssetsConsumer leak public void autoResolveAssets (final Collection<ID> dependencies, final PackageReaderListener listener)");

			if (asset_entry != null) {
				L.d("already loaded", dependency);
				LoadedAssets.releaseAsset(asset_entry, this.stub_consumer);
				continue;
			}
// if (!updated) {
//// ResourcesManager.updateAll();
// updated = true;
// }
// this.resolve(dependency, true, listener);
			try {
				this.resolve(dependency, true);
			} catch (final IOException e) {
				throw new IOException("Failed to resolve asset[" + dependency + "]", e);
			}
		}
	}

}
