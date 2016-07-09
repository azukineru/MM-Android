package com.minemeander.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.minemeander.Constant;
import com.minemeander.MyMineMeander;import java.io.FileFilter;
import java.io.IOException;
import java.io.File;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mine Meander";
		config.width = 1000;
		config.height = 600;
		Constant.ZOOM_FACTOR = 0.5f;

		deleteFiles();
		processSprites();
		copyTiledMaps();

		new LwjglApplication(new MyMineMeander(), config);
	}

	public static void deleteFiles() {
		File outputDir = new File("material/output");
		File[] listFiles = outputDir.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				file.delete();
			}
		}
	}

	public static void processSprites(){
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxWidth = 512;
		settings.maxHeight = 512;
		TexturePacker.processIfModified(settings, "material/input/sprites3", "material/output", "pack");
	}

	public static void copyTiledMaps() throws IOException {
		File inputDir = new File("material/input");
		File outputDir = new File("material/output");

		System.out.println("Copying tiled map to output...");
		File[] listFiles = inputDir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith("tsx") || pathname.getName().endsWith("tmx") || pathname.getName().endsWith("png");
			}
		});
		for (File file : listFiles) {
			Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(outputDir.getAbsolutePath()+"/"+file.getName()), StandardCopyOption.REPLACE_EXISTING);
		}
	}

}
