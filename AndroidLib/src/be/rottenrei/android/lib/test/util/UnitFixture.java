package be.rottenrei.android.lib.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Reads string data from the /fixture directory
 */
public class UnitFixture implements Fixture {

	private final String content;

	public UnitFixture(String fileName) throws Exception {
		File file = new File(new File("fixtures"), fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		StringBuffer buffer = new StringBuffer();
		while (line != null) {
			buffer.append(line);
			line = reader.readLine();
		}
		content = buffer.toString();
	}

	@Override
	public String getData() {
		return content;
	}

}
