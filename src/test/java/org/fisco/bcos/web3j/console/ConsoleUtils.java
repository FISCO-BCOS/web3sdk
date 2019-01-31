package org.fisco.bcos.web3j.console;

import org.fisco.bcos.web3j.crypto.EncryptType;

public class ConsoleUtils {

	public static void main(String[] args) {
		String json = "{\"name\":\"chenggang\",\"age\":24}";
		printJson(json);
	}
	public static void printJson(String jsonStr) {
		System.out.println(formatJson(jsonStr));
	}

	public static String formatJson(String jsonStr) {
		if (null == jsonStr || "".equals(jsonStr))
			return "";
		jsonStr = jsonStr.replace("\\n", "");
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		boolean isInQuotationMarks = false;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
			case '"':
				if (last != '\\') {
					isInQuotationMarks = !isInQuotationMarks;
				}
				sb.append(current);
				break;
			case '{':
			case '[':
				sb.append(current);
				if (!isInQuotationMarks) {
					sb.append('\n');
					indent++;
					addIndentBlank(sb, indent);
				}
				break;
			case '}':
			case ']':
				if (!isInQuotationMarks) {
					sb.append('\n');
					indent--;
					addIndentBlank(sb, indent);
				}
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\' && !isInQuotationMarks) {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			case ' ':
				if(',' != jsonStr.charAt(i-1))
				{
					sb.append(current);
				}
				break;
			case '\\':
				break;
			default:
				if(!(current == " ".charAt(0)))
					sb.append(current);
			}
		}

		return sb.toString();
	}

	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append("    ");
		}
	}
	
	public static boolean isInvalidHash(String hash) {
		if (hash.startsWith("0x") && hash.length() == 66) {
			return false;
		} else {
			System.out.println("Please provide a valid hash.");
			System.out.println();
			return true;
		}
	}
	
	public static boolean isInvalidNumber(String number, int flag) {
		String numberStr = number.trim();
		if (!numberStr.matches("^[0-9]*$") || "".equals(numberStr)) {
			if(flag == 0)
				System.out.println("Please provide block number by decimal mode.");
			else
				System.out.println("Please provide transaction index by decimal mode.");
			System.out.println();
			return true;
		}
		else
		{
			return false;
		}
		
	}
	public static boolean isInvalidAddress(String address) {
		 if(!address.startsWith("0x") || (address.length() != 42))
		 {
			 System.out.println("Please provide a valid address.");
			 System.out.println();
			 return true;
		 }
		 else
		 {
			 return false;
		 }
	}
	
	public static void singleLine() {
		System.out.println(
				"---------------------------------------------------------------------------------------------");
	}

	public static void doubleLine() {
		System.out.println(
				"=============================================================================================");
	}
}
