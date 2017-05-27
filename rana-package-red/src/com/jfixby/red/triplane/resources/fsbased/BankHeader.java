
package com.jfixby.red.triplane.resources.fsbased;

import com.jfixby.rana.api.pkg.bank.BankHeaderInfo;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.file.File;

public class BankHeader {

	private final File bank_folder;
	private final String bank_name;

	public BankHeader (final BankHeaderInfo headerInfo, final File bank_folder) {
		this.bank_folder = bank_folder;
		this.bank_name = headerInfo.bank_name;
		Debug.checkNull("bank_name", this.bank_name);
		Debug.checkEmpty("bank_name", this.bank_name);
	}

	public File getRoot () {
		return this.bank_folder;
	}

	@Override
	public String toString () {
		return "Bank[" + this.bank_folder + "]";
	}

	public String getName () {
		return this.bank_name;
	}

}
