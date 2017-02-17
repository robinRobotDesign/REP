
public class OrderEncoder {
	static String charSet = "9876543210";
	
	public static String getCharset() {
		return charSet;
	}

	/**
	 * A base converter.
	 * Based on code retrieved from http://stackoverflow.com/a/26172045
	 * 17/11/2016
	 * @param num the number to convert
	 * @return String encoded in base92
	 */
	public static String buildBase(long num){
		String encodedString = "";
		int j = (int)Math.ceil(Math.log(num)/Math.log(charSet.length()));
		if(num%91==0||num==1){
			j=j+1;
		}
		for(int i = 0; i < j; i++){
			encodedString += charSet.charAt((int) (num%charSet.length()));
			num /= charSet.length();
		}
		System.out.println(encodedString);
		return encodedString;
	}

	/**
	 * A reverter for the base converter.
	 * Based on code retrieved from http://stackoverflow.com/a/26172045
	 * 17/11/2016
	 * @param num the number to convert
	 * @return Long decoded back to base10
	 */
	public static long revertBase(String num) {
		long base10 = 0;
		for(int i = 0; i<num.length(); i++) {
			base10 += Math.pow(charSet.length(), i)*charSet.indexOf(num.charAt(i));
		}
		return base10;
	}

	public static boolean testValues() {
		long j = (long) Math.pow(10, 17);
		for(long i = 0; i<j;i++){
			if(i==Math.pow(10, 10)){
				System.out.println("BLABLABLA");
			}
			if(i!=revertBase(buildBase(i))){
				return false;
			}
		}
		return true;
	}
}
