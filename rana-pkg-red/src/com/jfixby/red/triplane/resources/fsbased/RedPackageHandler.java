
package com.jfixby.red.triplane.resources.fsbased;

import java.io.IOException;

import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.rana.api.pkg.PACKAGE_STATUS;
import com.jfixby.rana.api.pkg.PackageHandler;
import com.jfixby.rana.api.pkg.PackageReader;
import com.jfixby.rana.api.pkg.PackageReaderListener;
import com.jfixby.rana.api.pkg.PackageVersion;
import com.jfixby.rana.api.pkg.fs.PackageDescriptor;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileConflistResolver;
import com.jfixby.scarabei.api.file.FileSystem;
import com.jfixby.scarabei.api.file.FileSystemSandBox;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.scarabei.api.util.StateSwitcher;

public class RedPackageHandler implements PackageHandler, PackageVersion {

	final List<ID> dependencies = Collections.newList();
	final List<ID> descriptors = Collections.newList();

	// private File root_file;
	private PackageFormatImpl format;
	boolean isLoaded = false;
	private final String name;

	private final File package_cache;
	private final File package_folder;
	private final ResourceIndex resourceIndex;
	private String root_file_name;
	private final StateSwitcher<PACKAGE_STATUS> status;
	private long timestamp;

	private String version;

	public RedPackageHandler (final File package_folder, final ResourceIndex resourceIndex) throws IOException {
		this(package_folder, resourceIndex, null);
	}

	public RedPackageHandler (final File package_folder, final ResourceIndex resourceIndex, final File package_cache)
		throws IOException {
		this.resourceIndex = resourceIndex;
		this.package_folder = package_folder;
		this.status = JUtils.newStateSwitcher(PACKAGE_STATUS.NOT_INSTALLED);
		if (package_cache == null) {
			this.status.switchState(PACKAGE_STATUS.INSTALLED);
		}
		final File content_folder = package_folder.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
		if (!content_folder.exists()) {
			this.status.switchState(PACKAGE_STATUS.BROKEN);
		}
		this.package_cache = package_cache;
		this.name = package_folder.getName();
	}

	@Override
	public SealedAssetsContainer doReadPackage (final PackageReaderListener reader_listener, final PackageReader reader) {
		this.status.expectState(PACKAGE_STATUS.INSTALLED);

		File read_folder = null;
		if (this.package_cache == null) {
			read_folder = this.package_folder.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
		} else {
			read_folder = this.package_cache.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
		}

		FileSystem FS = read_folder.getFileSystem();

		File sandbox_folder = null;
		final boolean use_sandbox = SystemSettings.getFlag(AssetsManager.UseAssetSandBox);
		if (FS.isReadOnlyFileSystem() || !use_sandbox) {
			sandbox_folder = read_folder;
		} else {
			try {
				sandbox_folder = FileSystemSandBox.wrap(this.name, read_folder).ROOT();
			} catch (final IOException e) {
				this.status.switchState(PACKAGE_STATUS.BROKEN);
				reader_listener.onError(e);
				return null;
			}
			FS = sandbox_folder.getFileSystem();
		}

		final File root_file = sandbox_folder.child(this.root_file_name);

		try {
			final RedSealedContainer packageData = new RedSealedContainer(this, reader_listener, reader);
			final PackageInputImpl input = new PackageInputImpl(reader_listener, root_file, packageData, this);
// L.d("reading", root_file);
			reader.doReadPackage(input);
			packageData.seal();
			this.isLoaded = true;
			return packageData;
		} catch (final IOException e) {
			this.status.switchState(PACKAGE_STATUS.BROKEN);
			reader_listener.onError(e);
			return null;
		}

	}

	public void flagUnload () {
		this.isLoaded = false;
	}

	@Override
	public PackageFormatImpl getFormat () {
		return this.format;
	}

	public File getPackageFolder () {
		return this.package_folder;
	}

	@Override
	public String getPackageName () {
		return this.name;
	}

	@Override
	public PACKAGE_STATUS getStatus () {
		return this.status.currentState();
	}

	@Override
	public long getTimeStamp () {
		return this.timestamp;
	}

	@Override
	public PackageVersion getVersion () {
		return this;
	}

	@Override
	public String getVersionName () {
		return this.version;
	}

	@Override
	public void install (final PackageReaderListener reader_listener) {
		this.status.expectState(PACKAGE_STATUS.NOT_INSTALLED);
// L.d("install ?", this);
		final FileSystem fs = this.package_folder.getFileSystem();
		try {
			fs.copyFolderContentsToFolder(this.package_folder, this.package_cache, FileConflistResolver.OVERWRITE_IF_NEW);
// if (1 == 1) {
// throw new IOException("Failed to install");
// }
			this.status.switchState(PACKAGE_STATUS.INSTALLED);

		} catch (final IOException e) {
			reader_listener.onFailedToInstall(e);
// this.status.switchState(PACKAGE_STATUS.BROKEN);
// Err.reportError(e);
		}

	}

	@Override
	public boolean isLoaded () {
		return this.isLoaded;
	}

	@Override
	public Collection<ID> listDependencies () {
		return this.dependencies;
	}

	@Override
	public Collection<ID> listPackedAssets () {
		return this.descriptors;
	}

	@Override
	public void print () {
		L.d(this);
	}

	@Override
	public long reReadTimeStamp () {
		return this.resourceIndex.reReadTimeStamp(this);
	}

	public void setFormat (final String format_string) {
		Debug.checkNull("format", format_string);
		Debug.checkEmpty("format", format_string);
		this.format = new PackageFormatImpl(format_string);
	}

	public void setRootFileName (final String root_file_name) {
		this.root_file_name = root_file_name;
	}

	public void setTimestamp (final long timestamp) {
		this.timestamp = timestamp;
	}

	public void setVersion (final String version) {
		Debug.checkNull("version", version);
		Debug.checkEmpty("version", version);
		this.version = version;
	}

	@Override
	public String toString () {
		return "PackageHandler[" + this.format + "] ver." + this.version + " " + this.descriptors + " timestamp=" + this.timestamp;
	}

}
