
package com.jfixby.red.triplane.resources.fsbased;

import java.io.IOException;

import com.jfixby.rana.api.pkg.PACKAGE_STATUS;
import com.jfixby.rana.api.pkg.PackageHandler;
import com.jfixby.rana.api.pkg.PackageSearchParameters;
import com.jfixby.rana.api.pkg.PackageSearchResult;
import com.jfixby.rana.api.pkg.io.PackageDescriptor;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.file.File;

public class ResourceIndex {
	Set<PackageHandler> all_handlers = Collections.newSet();
	Map<ID, Set<PackageHandler>> handlers_by_asset_id = Collections.newMap();
	private final RedResource master;

	public void reset () {
		this.handlers_by_asset_id.clear();
		this.all_handlers.clear();
	}

	public void add (final PackageDescriptor descriptor, final File package_folder) throws IOException {

		final RedPackageHandler handler;
		if (this.master.isCachingRequired()) {
			final String package_folder_name = package_folder.getName();
			final File cache_folder = this.master.getCacheFolder().child(package_folder_name);
			handler = new RedPackageHandler(package_folder, this, cache_folder);
		} else {
			handler = new RedPackageHandler(package_folder, this);
		}
		final String format_string = descriptor.format;
		handler.setFormat(format_string);
		handler.setVersion(descriptor.version);
		handler.setTimestamp(descriptor.timestamp());
		handler.setRootFileName(descriptor.root_file_name);

		for (int i = 0; i < descriptor.packed_assets.size(); i++) {
			final String name = descriptor.packed_assets.get(i);
			final ID element = Names.newID(name);
			handler.descriptors.add(element);
		}

		for (int i = 0; i < descriptor.package_dependencies.size(); i++) {
			final String name = descriptor.package_dependencies.get(i);
			final ID element = Names.newID(name);
			handler.dependencies.add(element);
		}

		this.addHandler(handler);

	}

	private void addHandler (final PackageHandler handler) {
		for (int i = 0; i < handler.listPackedAssets().size(); i++) {
			final ID key = handler.listPackedAssets().getElementAt(i);
			Set<PackageHandler> list = this.handlers_by_asset_id.get(key);
			if (list == null) {
				list = Collections.newSet();
				this.handlers_by_asset_id.put(key, list);
			}
			list.add(handler);
			this.all_handlers.add(handler);
		}
	}

	public PackageSearchResult findPackages (final PackageSearchParameters search_params) {
		final RedPackageSearchResult result = new RedPackageSearchResult(search_params);
		Set<PackageHandler> handlers = null;
		if (search_params.getAllFlag) {
			handlers = this.getAllHandlers();
		} else {
			final ID asset_id = search_params.asset_to_find;
			handlers = this.filterHandlers(asset_id);
		}

		final int n = handlers.size();
		for (int i = 0; i < n; i++) {
			final PackageHandler handler = handlers.getElementAt(i);
			if (this.handler_fits(handler, search_params)) {
				result.add(handler);
			}
		}

		return result;
	}

	private Set<PackageHandler> getAllHandlers () {
		return this.all_handlers;
	}

	private Set<PackageHandler> filterHandlers (final ID asset_id) {
		Set<PackageHandler> handlers = this.handlers_by_asset_id.get(asset_id);
		if (handlers == null) {
			handlers = Collections.newSet();
		}
		return handlers;
	}

	private boolean handler_fits (final PackageHandler handler, final PackageSearchParameters search_params) {
		if (search_params.getAllFlag) {
			return true;
		}

		final List<PACKAGE_STATUS> acccepted_statuses = search_params.acceptablePackageStatuses;

		if (!acccepted_statuses.contains(handler.getStatus())) {
			return false;
		}
		final ID asset_id = search_params.asset_to_find;
		final Collection<ID> descriptors = handler.listPackedAssets();
		final boolean contains = descriptors.contains(asset_id);
		if (!contains) {
			return false;
		}

		return true;
	}

	public void print () {

		this.handlers_by_asset_id.print("index");
	}

	public ResourceIndex (final RedResource fileSystemBasedResource) {
		super();
		this.master = fileSystemBasedResource;

	}

	public long reReadTimeStamp (final RedPackageHandler packageHandlerImpl) {
		return this.master.reReadTimeStamp(packageHandlerImpl);
	}

}
