package be.rottenrei.android.lib.test.util;

import java.io.InputStream;

import android.content.Context;
import be.rottenrei.android.lib.util.FileUtils;

public class InstrumentationFixture implements Fixture {

	private final String data;

	public InstrumentationFixture(int id, Context context) throws Exception {
		InputStream stream = context.getResources().openRawResource(id);
		data = FileUtils.readWholeFile(stream);
	}

	@Override
	public String getData() {
		return data;
	}

}
