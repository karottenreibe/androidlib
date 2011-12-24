package be.rottenrei.android.lib.db;

public class DatabaseException extends Exception {

	public DatabaseException(IllegalArgumentException e) {
		super(e);
	}

	public DatabaseException(String message) {
		super(message);
	}

}