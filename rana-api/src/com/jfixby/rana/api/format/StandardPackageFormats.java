
package com.jfixby.rana.api.format;

public class StandardPackageFormats {
	public static final class Adobe {
		public static final PackageFormat PSD = new PackageFormat("Photoshop.PSD");
	}

	public static final class libGDX {
		public static final PackageFormat Atlas = new PackageFormat("libGDX.Atlas");
		public static final PackageFormat Texture = new PackageFormat("libGDX.Texture");
// public static final String Font = new PackageFormat("libGDX.Font.Otf";
		public static final PackageFormat TTFFont = new PackageFormat("TrueTypeFont");
	}

	public static final class RedTriplane {
		public static final PackageFormat TiledRaster = new PackageFormat("RedTriplane.TiledRaster");
		public static final PackageFormat Shader = new PackageFormat("RedTriplane.Shader");
		public static final PackageFormat Text = new PackageFormat("RedTriplane.Text");
		public static final PackageFormat String = new PackageFormat("RedTriplane.String");

	}

	public static final class Box2DEditor {
		public static final PackageFormat Project = new PackageFormat("Box2DEditor.Project");
	}

	public static final class Java {
		public static final PackageFormat Jar = new PackageFormat("java.jar");
	}
}
