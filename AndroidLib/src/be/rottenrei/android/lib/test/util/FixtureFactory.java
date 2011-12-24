package be.rottenrei.android.lib.test.util;

import android.content.Context;

public class FixtureFactory {

	public static Fixture getFixture(int id, Context context) throws Exception {
		return new InstrumentationFixture(id, context);
	}

	public static Fixture getFixture(String fileName) throws Exception {
		return new UnitFixture(fileName);
	}

}
