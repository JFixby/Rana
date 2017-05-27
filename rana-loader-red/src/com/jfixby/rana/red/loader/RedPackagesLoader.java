
package com.jfixby.rana.red.loader;

import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackagesLoaderComponent;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;

public class RedPackagesLoader implements PackagesLoaderComponent {
	final List<PackageReader> loaders = Collections.newList();

	final Map<PackageFormat, List<PackageReader>> loaders_by_format = Collections.newMap();

	@Override
	public Collection<PackageReader> findPackageReaders (final PackageFormat format) {
		final List<PackageReader> list = this.loaders_by_format.get(format);
		if (list == null) {
			// L.d("format", format);
			// loaders_by_format.print("acceptable formats");
			return Collections.newList();
		}
		return list;
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.loaders_by_format.keys();
	}

	@Override
	public void printInstalledPackageReaders () {
		this.loaders_by_format.print("Installed package readers");
	}

	@Override
	public void registerPackageReader (final PackageReader loader) {
		if (this.loaders.contains(loader)) {
			L.d("Loader is already registred:" + loader);
			return;
		}

		final Collection<PackageFormat> can_read = loader.listAcceptablePackageFormats();
		Debug.checkNull("PackageReader.listAcceptablePackageFormats()", can_read);
		this.loaders.add(loader);
		for (int i = 0; i < can_read.size(); i++) {
			final PackageFormat format = can_read.getElementAt(i);
			List<PackageReader> loaders_list = this.loaders_by_format.get(format);
			if (loaders_list == null) {
				loaders_list = Collections.newList();
				this.loaders_by_format.put(format, loaders_list);
			}
			loaders_list.add(loader);
		}

	}
}
