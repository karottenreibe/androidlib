package be.rottenrei.android.lib.db;

/**
 * Basic interface for any data that can be stored in the database.
 */
public interface IModelType {

	public Long getDbId();

	public void setDbId(Long id);

}
