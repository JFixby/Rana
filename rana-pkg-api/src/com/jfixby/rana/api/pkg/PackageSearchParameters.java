package com.jfixby.rana.api.pkg;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.List;

public interface PackageSearchParameters {

	void setAssetId(ID asset_to_find);

	ID getAssetId();

//	List<PackageFormat> acceptPackageFormat();

	List<PACKAGE_STATUS> acceptPackageStatus();

	void print();

	void setGetAllAssetsFlag(boolean b);

	boolean isGetAllAssetsFlagActive();

}
