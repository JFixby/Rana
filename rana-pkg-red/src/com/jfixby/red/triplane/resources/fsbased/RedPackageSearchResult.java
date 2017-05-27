
package com.jfixby.red.triplane.resources.fsbased;

import java.util.Comparator;

import com.jfixby.rana.api.pkg.PackageHandler;
import com.jfixby.rana.api.pkg.PackageSearchParameters;
import com.jfixby.rana.api.pkg.PackageSearchResult;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.IntegerMath;

public class RedPackageSearchResult implements PackageSearchResult {

	final List<PackageHandler> list = Collections.newList();
	boolean is_sorted = false;
	private final Comparator<PackageHandler> default_comparator = new Comparator<PackageHandler>() {
		@Override
		public int compare (final PackageHandler o1, final PackageHandler o2) {
			final long ts1 = o1.getVersion().getTimeStamp();
			final long ts2 = o2.getVersion().getTimeStamp();
			return IntegerMath.compare(ts1, ts2);
		}
	};
	private final PackageSearchParameters search_params;;

	public RedPackageSearchResult (final PackageSearchParameters search_params) {
		this.search_params = search_params;
	}

	@Override
	public PackageHandler getBest () {
		if (this.size() == 0) {
			Err.reportError("PackageSearchResult is empty");
		}
		if (!this.is_sorted) {
			this.sort(this.default_comparator);
			this.is_sorted = true;
		}
		if (this.size() > 1) {
// this.list.print("options");
		}

		return this.list.getLast();
	}

	@Override
	public boolean isEmpty () {
		return this.list.size() == 0;
	}

	@Override
	public void sort (final Comparator<PackageHandler> comparator) {
		this.list.sort(comparator);
	}

	public void add (final PackageSearchResult result_i) {
		for (int i = 0; i < result_i.size(); i++) {
			final PackageHandler handler = result_i.getEntry(i);
			this.add(handler);
		}
	}

	public void add (final PackageHandler handler) {
		this.list.add(handler);
		this.is_sorted = false;
	}

	@Override
	public PackageHandler getEntry (final int i) {
		return this.list.getElementAt(i);
	}

	@Override
	public int size () {
		return this.list.size();
	}

	@Override
	public void print () {
		L.d("---PackageSearchResult----------------");
		this.search_params.print();
		this.list.print("packages");
	}

	@Override
	public Collection<PackageHandler> list () {
		return this.list;
	}

}
