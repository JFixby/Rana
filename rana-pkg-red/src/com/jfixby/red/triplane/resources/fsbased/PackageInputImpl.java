
package com.jfixby.red.triplane.resources.fsbased;

import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.rana.api.pkg.PackageHandler;
import com.jfixby.rana.api.pkg.PackageReaderInput;
import com.jfixby.rana.api.pkg.PackageReaderListener;
import com.jfixby.scarabei.api.file.File;

public class PackageInputImpl implements PackageReaderInput {

	private final PackageReaderListener reader_listener;

	private final File root_file;
	private final RedPackageHandler packageHandlerImpl;

	private final RedSealedContainer packageData;

	public PackageInputImpl (final PackageReaderListener reader_listener, final File root_file,
		final RedSealedContainer packageData, final RedPackageHandler packageHandlerImpl) {
		this.reader_listener = reader_listener;
		this.root_file = root_file;
		this.packageHandlerImpl = packageHandlerImpl;
		this.packageData = packageData;

	}

	@Override
	public PackageReaderListener getPackageReaderListener () {
		return this.reader_listener;
	}

	@Override
	public File getRootFile () {
		return this.root_file;
	}

	@Override
	public PackageHandler getPackageHandler () {
		return this.packageHandlerImpl;
	}

	@Override
	public AssetsContainer getStorage () {
		return this.packageData;
	}

}
