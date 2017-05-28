
package com.jfixby.rana.api.pkg;

import com.jfixby.scarabei.api.file.File;

public interface AssetsTankSpecs {

	File getFolder ();

	void setFolder (File bank);

	boolean isChachingRequired ();

	void setCachingRequired (boolean required);

	void setCacheFolder (File bank_cache);

	File getCacheFolder ();

	String getName ();

	void setName (String bankName);

	void setShortName (String tankName);

	String getShortName ();

}
