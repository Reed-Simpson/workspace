package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AppData implements Serializable {
	private static final long serialVersionUID = 3020260880237317265L;
	private static final int MAXRECENT = 10;
	private static File directory = new File(System.getenv("APPDATA")+"\\ReedsHexcrawl");
	private static File file = new File(directory,"\\appdata");
	private ArrayList<File> recent;

	public static File getDirectory() {
		return directory;
	}

	public AppData() {
		this.recent = new ArrayList<File>();
	}

	public ArrayList<File> getRecent() {
		return recent;
	}
	public File getMostRecent() {
		if(recent.size()>0) return recent.get(recent.size()-1);
		else return null;
	}

	public void addRecent(File f) {
		this.recent.remove(f);
		this.recent.add(f);
		if(this.recent.size()>MAXRECENT) this.recent.remove(0);
		this.save();
	}

	public void removeRecent(File f) {
		this.recent.remove(f);
		this.save();
	}

	public boolean save() {
		boolean result = false;
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream = null;
		try {
			directory.mkdirs();
			file.createNewFile();
			fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this);
			result = true;
			System.out.println("Appdata saved to "+file.toString());
		} catch (FileNotFoundException e) {
			System.out.println("Location not found: "+file.toString());
		} catch (IOException e1) {
			System.out.println("Failed to save: "+file.toString());
			e1.printStackTrace();
		} finally {
			try {
				objectOutputStream.flush();
				objectOutputStream.close();
			} catch (Exception ex) {}
		}
		return result;
	}

	public static AppData load(File file) {
		FileInputStream fileInputStream;
		AppData loadedRecord = null;
		try {
			fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = null;
			try {
				objectInputStream = new ObjectInputStream(fileInputStream);
				loadedRecord = (AppData) objectInputStream.readObject();
				System.out.println("Appdata loaded from "+file.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					objectInputStream.close();
				} catch (Exception ex) {}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Appdata not found: "+file.toString());
		}

		return loadedRecord;
	}

	public static AppData load() {
		return load(file);
	}

}
