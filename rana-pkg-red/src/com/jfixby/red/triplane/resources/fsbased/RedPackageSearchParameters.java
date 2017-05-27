package com.jfixby.red.triplane.resources.fsbased;

import com.jfixby.rana.api.pkg.PACKAGE_STATUS;
import com.jfixby.rana.api.pkg.PackageSearchParameters;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.log.L;

public class RedPackageSearchParameters implements PackageSearchParameters {

	private List<PackageFormatImpl> acceptablePackageFormats = Collections.newList();
	private List<PACKAGE_STATUS> acceptablePackageStatuses = Collections.newList(
			PACKAGE_STATUS.INSTALLED, PACKAGE_STATUS.NOT_INSTALLED);
	{

	}

	private ID asset_to_find;
	private boolean GET_ALL = false;

	@Override
	public void setAssetId(ID asset_to_find) {
		this.asset_to_find = asset_to_find;
	}

	@Override
	public ID getAssetId() {
		return asset_to_find;
	}

//	@Override
//	public List<PackageFormat> acceptPackageFormat() {
//		return acceptablePackageFormats;
//	}

	@Override
	public void print() {
		L.d(this.toString());
	}

	@Override
	public String toString() {
		return "RedPackageSearchParameters: acceptablePackageFormats="
				+ acceptablePackageFormats + ", acceptablePackageStatuses="
				+ acceptablePackageStatuses + ", asset_to_find=<"
				+ asset_to_find + ">";
	}

	@Override
	public List<PACKAGE_STATUS> acceptPackageStatus() {
		return acceptablePackageStatuses;
	}

	@Override
	public void setGetAllAssetsFlag(boolean b) {
		GET_ALL = b;
	}

	@Override
	public boolean isGetAllAssetsFlagActive() {
		return GET_ALL;
	}

}
