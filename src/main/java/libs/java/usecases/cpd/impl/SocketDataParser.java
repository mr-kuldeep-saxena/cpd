package libs.java.usecases.cpd.impl;

import libs.java.usecases.cpd.Parser;

/**
 * Parser implementation
 * 
 * @author Kuldeep
 *
 */
public class SocketDataParser implements Parser<byte[], ParsedBean> {

	public SocketDataParser() {
	}

	public ParsedBean parse(byte[] value) {
		ParsedBean bean = new ParsedBean();
		String splittedFields[] = new String(value).split(",");
		bean.setName(splittedFields[0].substring(splittedFields[0].indexOf(":") + 1, splittedFields[0].length()));
		bean.setEmpId(Integer
				.parseInt(splittedFields[1].substring(splittedFields[1].indexOf(":") + 1, splittedFields[1].length())));
		bean.setAge(Integer
				.parseInt(splittedFields[2].substring(splittedFields[2].indexOf(":") + 1, splittedFields[2].length())));

		return bean;
	}

}
