
package com.jfixby.rana.api.pkg;

import java.io.IOException;

import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.AssetsManagerFlags;
import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public interface PackageReaderListener {

	public static final PackageReaderListener DEFAULT = new PackageReaderListener() {

		@Override
		public void onError (final IOException e) {
			Err.reportError(e);
		}

		@Override
		public void onFailedToInstall (final IOException e) {
			Err.reportError(e);
		}

		@Override
		public void onDependenciesRequired (final PackageHandler handler, final Collection<ID> dependencies) {
			final boolean auto = SystemSettings.getFlag(AssetsManagerFlags.AutoresolveDependencies);

			if (!auto) {
				dependencies.print("Missing dependencies");
				Err.reportError("AssetsManagerFlags.AutoresolveDependencies flag is false.");
			} else {

				for (int i = 0; i < dependencies.size(); i++) {
					final ID dep = dependencies.getElementAt(i);
					if (AssetsManager.isRegisteredAsset(dep)) {

					} else {
						AssetsManager.autoResolveAsset(dep, DEFAULT);
					}
				}

			}
		}

		@Override
		public void onPackageDataDispose (final SealedAssetsContainer container) {
			AssetsManager.unRegisterAssetsContainer(container);
		}

		@Override
		public void onPackageDataLoaded (final SealedAssetsContainer container) {
			AssetsManager.registerAssetsContainer(container);
		}

	};

	void onError (IOException e);

	void onDependenciesRequired (PackageHandler requiredBy, Collection<ID> dependencies);

	void onPackageDataDispose (final SealedAssetsContainer data);

	void onPackageDataLoaded (final SealedAssetsContainer data);

	void onFailedToInstall (IOException e);

}
