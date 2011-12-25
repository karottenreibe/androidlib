package be.rottenrei.android.lib.test.unit;

import be.rottenrei.android.lib.db.TableCreateStatement;
import be.rottenrei.android.lib.db.TableCreateStatement.Type;
import be.rottenrei.android.lib.test.base.UnitTestBase;

public class TableCreateStatementTest extends UnitTestBase {

	public void testEmpty() {
		String stmt = new TableCreateStatement("test").end();
		assertEquals("create table if not exists test (" +
				"_id integer primary key autoincrement);", stmt);
	}

	public void testOneCol() {
		String stmt = new TableCreateStatement("test")
		.col("foo", Type.text, true, "null")
		.end();
		assertEquals("create table if not exists test (" +
				"_id integer primary key autoincrement," +
				" foo text default null);", stmt);
	}

	public void testManyCols() {
		String stmt = new TableCreateStatement("test")
		.col("foo", Type.text, false, "'bar'")
		.col("gooint", Type.integer, true, "null")
		.end();
		assertEquals("create table if not exists test (" +
				"_id integer primary key autoincrement, " +
				"foo text not null default 'bar', " +
				"gooint integer default null);", stmt);
	}

}
