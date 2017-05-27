
package com.jfixby.rana.test;

import java.io.IOException;

import com.jfixby.rana.api.pkg.RanaPackageManager;
import com.jfixby.red.triplane.resources.fsbased.RedPackageManager;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManagerSpecs;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.gson.GoogleGson;

public class TestRana {

	public static void main (final String[] args) throws IOException {
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
		RanaPackageManager.installComponent(resman);

		RanaPackageManager.printAllIndexes();

	}

}
