package be.rottenrei.android.lib.test.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

abstract public class DatabaseTestBase extends GenericInstrumentationTestBase {

	private TestDatabaseOpenHelper helper;

	protected class TestDatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

		public TestDatabaseOpenHelper(Context context, String name) {
			super(context, name, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
			// nothing to do
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
			// nothing to do
		}

	}

	public TestDatabaseOpenHelper getHelper() {
		return helper;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		helper = new TestDatabaseOpenHelper(getContext(), getDatabaseName());
		// drop all tables
		for (Class<?> clazz : getDataClasses()) {
			TableUtils.dropTable(helper.getConnectionSource(), clazz, true);
		}
	}

	abstract protected String getDatabaseName();

	abstract protected Class<?>[] getDataClasses();

}
