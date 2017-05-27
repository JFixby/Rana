
package com.jfixby.rana.api.format;

public class PackageFormat {

	public String format_string;

	public PackageFormat () {
	}

	public PackageFormat (final String format_string) {
		this.format_string = format_string;
	}

	@Override
	public String toString () {
		return "PackageFormat [name=" + this.format_string + "]";
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.format_string == null) ? 0 : this.format_string.hashCode());
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final PackageFormat other = (PackageFormat)obj;
		if (this.format_string == null) {
			if (other.format_string != null) {
				return false;
			}
		} else if (!this.format_string.equals(other.format_string)) {
			return false;
		}
		return true;
	}

}
