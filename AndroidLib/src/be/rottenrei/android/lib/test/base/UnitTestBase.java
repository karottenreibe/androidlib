package be.rottenrei.android.lib.test.base;

import junit.framework.TestCase;
import be.rottenrei.android.lib.test.util.FixtureFactory;

public class UnitTestBase extends TestCase {

	public String fixture(String fileName) throws Exception {
		return FixtureFactory.getFixture(fileName).getData();
	}

}
