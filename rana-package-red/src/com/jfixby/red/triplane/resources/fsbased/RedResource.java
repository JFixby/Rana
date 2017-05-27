
package com.jfixby.red.triplane.resources.fsbased;

import java.io.IOException;

import com.jfixby.rana.api.pkg.PackageSearchParameters;
import com.jfixby.rana.api.pkg.PackageSearchResult;
import com.jfixby.rana.api.pkg.Resource;
import com.jfixby.rana.api.pkg.ResourceRebuildIndexListener;
import com.jfixby.rana.api.pkg.ResourceSpecs;
import com.jfixby.rana.api.pkg.io.BankIndex;
import com.jfixby.rana.api.pkg.io.PackageDescriptor;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileInputStream;
import com.jfixby.scarabei.api.file.FileSystem;
import com.jfixby.scarabei.api.file.FilesList;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.log.L;

public class RedResource implements Resource {

	private final File bank_folder;

	private File cache;
	private final boolean caching_required;
	ResourceIndex index = new ResourceIndex(this);
	private final String name;

	RedResource (final ResourceSpecs specs) throws IOException {

		final File bank_folder = specs.getFolder();
		this.caching_required = specs.isChachingRequired();
		this.name = Debug.checkNull("name", specs.getName());
		if (this.caching_required) {
			this.cache = specs.getCacheFolder();
			this.cache.makeFolder();
		}
		if (!bank_folder.exists() || !bank_folder.isFolder()) {
			final String msg = "Resource root folder was not found: " + bank_folder;
			L.e(msg);
			throw new IOException(msg);
		}
		this.bank_folder = bank_folder;

	}

	@Override
	public PackageSearchResult findPackages (final PackageSearchParameters search_params) {
// if (this.indexNeverTouched) {
// this.rebuildIndex(null);
// }
		return this.index.findPackages(search_params);
	}

	public File getCacheFolder () {
		return this.cache;
	}

// boolean indexNeverTouched = true;

	@Override
	public String getName () {
		return this.name;
	}

	private void index (final PackageDescriptor descriptor, final File package_folder) throws IOException {
		this.index.add(descriptor, package_folder);
	}

	public boolean isCachingRequired () {
		return this.caching_required;
	}

	@Override
	public void printIndex () {
		this.index.print();
	}

	@Override
	public void rebuildIndex (final ResourceRebuildIndexListener listener) {
		L.d("rebuilding index", this.toString());
		this.index.reset();
// this.indexNeverTouched = false;
		if (this.cache == null) {
			this.rebuildIndexLocal(listener, this.bank_folder);
			return;
		}
		try {
			final File indexFile = this.bank_folder.child(BankIndex.FILE_NAME);
			if (indexFile.exists()) {
				L.d("index found", indexFile);
			}
			final FileInputStream is = indexFile.newInputStream();
			is.open();
			final BankIndex index = BankIndex.deSerializeGZIPJava(is);
			is.close();

			for (final String packageName : index.descriptors.keySet()) {
				final PackageDescriptor descriptor = index.descriptors.get(packageName);
				final File package_folder = this.bank_folder.child(packageName);
				this.index(descriptor, package_folder);
			}

		} catch (final IOException e) {
			e.printStackTrace();
			this.rebuildIndexLocal(listener, this.cache);
			listener.onError(e);
			return;
		}
	}

	private void rebuildIndexLocal (final ResourceRebuildIndexListener listener, final File folder) {
		try {
			final FilesList list = folder.listDirectChildren();
			for (int i = 0; i < list.size(); i++) {
				final File file_i = list.getElementAt(i);
				if (file_i.isFolder()) {
					this.try_to_index(file_i);
				}
			}
		} catch (final IOException e) {
			listener.onError(e);
		}
	}

	public long reReadTimeStamp (final RedPackageHandler packageHandlerImpl) {
		final File package_folder = packageHandlerImpl.getPackageFolder();
		final File file = package_folder.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME);
		try {
			L.d("re-reading", file);
			final PackageDescriptor descriptor = file.readData(PackageDescriptor.class);
			return descriptor.timestamp();
		} catch (final Exception e) {
			L.e(e.toString());
			e.printStackTrace();
			try {
				L.d(file.readToString());
			} catch (final IOException e1) {
				// e1.printStackTrace();
			}
			L.e("failed to read", file);
		}

		return 0;
	}

	@Override
	public String toString () {
		return "Resource[" + this.name + "] " + this.bank_folder;
	}

	private void try_to_index (final File package_folder) {
		final FileSystem FS = package_folder.getFileSystem();
		final File json_file = package_folder.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME);
// try {
// final PackageDescriptor descriptor = file.readData(PackageDescriptor.class);
// this.index(descriptor, package_folder);
// } catch (final Exception e)
		{
// L.d("reading json", file);
// L.e(e.toString());
// e.printStackTrace();

			try {
// L.e("retry json file", json_file);
				final String json = json_file.readToString();

				final PackageDescriptor descriptor = Json.deserializeFromString(PackageDescriptor.class, json);
				this.index(descriptor, package_folder);
				return;
			} catch (final IOException e2) {
				e2.printStackTrace();
				L.e(e2.toString());

				try {
					L.d(json_file.readToString());
				} catch (final IOException e1) {
					// e1.printStackTrace();
				}
			}

			try {
				L.d(json_file.readToString());
			} catch (final IOException e1) {
				// e1.printStackTrace();
			}
			L.e("failed to read", json_file);

// if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
// Err.reportError(json_file + " " + e);
// }
		}
	}

}
