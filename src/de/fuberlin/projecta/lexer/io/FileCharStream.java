package de.fuberlin.projecta.lexer.io;

import java.io.IOException;

import de.fuberlin.projecta.utils.IOUtils;

public class FileCharStream implements ICharStream {

	private StringCharStream stream = null;

	/**
	 * Read the contents of a file
	 * 
	 * Currently reads the entire file into memory and internally uses
	 * StringCharStream
	 * 
	 * @param path
	 *            Path to file
	 */
	public FileCharStream(String path) {

		String data;
		try {
			data = IOUtils.readFile(path);
		} catch (IOException e) {
			// do nothing, data stays empty
			data = "";
		}
		stream = new StringCharStream(data);
	}

	@Override
	public int consumeChars(int numberOfChars) {
		return stream.consumeChars(numberOfChars);
	}

	@Override
	public String getNextChars(int numberOfChars) {
		return stream.getNextChars(numberOfChars);
	}

	@Override
	public int getOffset() {
		return stream.getOffset();
	}

	@Override
	public boolean isEmpty() {
		return stream.isEmpty();
	}

	@Override
	public void resetOffset() {
		stream.resetOffset();
	}

}
