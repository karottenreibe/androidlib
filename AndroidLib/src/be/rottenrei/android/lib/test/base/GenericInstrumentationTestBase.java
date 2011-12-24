package be.rottenrei.android.lib.test.base;

import android.content.Context;
import android.test.AndroidTestCase;
import be.rottenrei.android.lib.test.util.FixtureFactory;

public abstract class GenericInstrumentationTestBase extends AndroidTestCase {

	public String fixture(int id) throws Exception {
		Context context = getContext().createPackageContext(getTestPackageName(),
				Context.CONTEXT_IGNORE_SECURITY);
		return FixtureFactory.getFixture(id, context).getData();
	}

	abstract protected String getTestPackageName();

}
