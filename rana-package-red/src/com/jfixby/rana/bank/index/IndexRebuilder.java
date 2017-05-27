
package com.jfixby.rana.bank.index;

import java.io.IOException;

import com.jfixby.rana.api.pkg.bank.BankHeaderInfo;
import com.jfixby.rana.api.pkg.bank.BankIndex;
import com.jfixby.rana.api.pkg.fs.PackageDescriptor;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileFilter;
import com.jfixby.scarabei.api.file.FileInputStream;
import com.jfixby.scarabei.api.file.FileOutputStream;
import com.jfixby.scarabei.api.file.FilesList;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.json.JsonString;
import com.jfixby.scarabei.api.log.L;

public class IndexRebuilder {

	public static void rebuild (final IndexRebuilderParams rebuilderParams) throws IOException {
		final File target = rebuilderParams.getBankFolder();
		L.d("target", target + " " + target.isFolder());
		Debug.checkNull("target", target);
		Debug.checkTrue("target not found", target.exists());
		Debug.checkTrue("target is folder", target.isFolder());
		if (!(target.isFolder() && target.child(BankHeaderInfo.FILE_NAME).exists())) {
			Err.reportError("is not bank: " + target + " bank header is missing: " + BankHeaderInfo.FILE_NAME);
		}

		final Collection<String> tanks = rebuilderParams.listTanks();
		for (final String tankName : tanks) {
			Debug.checkNull("tankName", tankName);
			final File tankFolder = target.child(tankName);
			L.d("   indexing", tankName);
			indexTank(tankFolder);
		}
	}

	private static void indexTank (final File tank) throws IOException {
		final File indexFile = tank.child(BankIndex.FILE_NAME);
		final File indexDebugFile = tank.child(BankIndex.FILE_NAME_DEBUG);
		final BankIndex index = new BankIndex();
		final FilesList folders = tank.listDirectChildren(new FileFilter() {
			@Override
			public boolean fits (final File file) {
				try {
					if (file.isFile()) {
						return false;
					}

					final File bankHeader = file.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME);
					if (!bankHeader.exists()) {
						return false;
					}
					return true;

				} catch (final IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});

		for (final File packageFolder : folders) {
			final File descriptorFile = packageFolder.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME);
			final PackageDescriptor descriptor = descriptorFile.readData(PackageDescriptor.class);
			final String packageFolderName = packageFolder.getName();
			L.d("package found", packageFolderName);
			index.add(packageFolderName, descriptor);
		}
		{
			final FileOutputStream os = indexFile.newOutputStream();
			L.d("writing", indexFile);
			os.open();
			BankIndex.serializeGZIPJava(index, os);
			os.close();
		}

		{
			final FileOutputStream os = indexDebugFile.newOutputStream();
			L.d("writing", indexDebugFile);
			os.open();
			BankIndex.serializeJson(index, os);
			os.close();
		}
		{
			final FileInputStream is = indexFile.newInputStream();
			is.open();
			final BankIndex indexCheck = BankIndex.deSerializeGZIPJava(is);
			is.close();
			final JsonString j1 = Json.serializeToString(indexCheck);
			final JsonString j2 = Json.serializeToString(index);
			Debug.checkTrue("failed serialization", j1.equals(j2));

		}

	}

}
