package com.jfixby.rana.api.pkg;

import java.util.Comparator;

import com.jfixby.scarabei.api.collections.Collection;

public interface PackageSearchResult {

	PackageHandler getBest();

	boolean isEmpty();

	public void sort(Comparator<PackageHandler> comparator);

	PackageHandler getEntry(int i);

	int size();

	public void print();

	Collection<PackageHandler> list();

}
