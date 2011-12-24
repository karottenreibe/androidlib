package be.rottenrei.android.lib.test.base;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import be.rottenrei.android.lib.test.util.FixtureFactory;

public abstract class GenericActivityTestBase<ActivityType extends Activity> extends ActivityInstrumentationTestCase2<ActivityType> {

	public GenericActivityTestBase(String pkg, Class<ActivityType> activityClass) {
		super(pkg, activityClass);
	}

	public String fixture(int id) throws Exception {
		return FixtureFactory.getFixture(id, getActivity()).getData();
	}

}
