
package com.jfixby.rana.api.asset;

public class AssetResolutionException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 218869059621766699L;

	public AssetResolutionException () {
		super();
	}

	public AssetResolutionException (final String arg0, final Throwable arg1, final boolean arg2, final boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public AssetResolutionException (final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public AssetResolutionException (final String arg0) {
		super(arg0);
	}

	public AssetResolutionException (final Throwable arg0) {
		super(arg0);
	}

}
