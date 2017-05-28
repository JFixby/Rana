
package com.jfixby.red.triplane.resources.fsbased;

import com.jfixby.rana.api.pkg.AssetsTankSpecs;
import com.jfixby.scarabei.api.file.File;

public class RedResourceSpecs implements AssetsTankSpecs {

	private File bank;
	private boolean required;
	private File bank_cache;
	private String name;
	private String shortname;

	@Override
	public File getFolder () {
		return this.bank;
	}

	@Override
	public void setFolder (final File bank) {
		this.bank = bank;
	}

	@Override
	public boolean isChachingRequired () {
		return this.required;
	}

	@Override
	public void setCachingRequired (final boolean required) {
		this.required = required;
	}

	@Override
	public void setCacheFolder (final File bank_cache) {
		this.bank_cache = bank_cache;
	}

	@Override
	public File getCacheFolder () {
		return this.bank_cache;
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public void setName (final String bankName) {
		this.name = bankName;
	}

	@Override
	public void setShortName (final String tankName) {
		this.shortname = tankName;
	}

	@Override
	public String getShortName () {
		return this.shortname;
	}

}
