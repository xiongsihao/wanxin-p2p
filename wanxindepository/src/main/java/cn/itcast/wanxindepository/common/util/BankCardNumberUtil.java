package cn.itcast.wanxindepository.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Random;

public class BankCardNumberUtil {

	private static BankCardNumberUtil instance = new BankCardNumberUtil();

	public static String get() {
		BankNameEnum[] nameEnums = BankNameEnum.values();
		return instance.generate(nameEnums[new Random().nextInt(nameEnums.length)], BankCardTypeEnum.DEBIT);
	}

	public static void main(String[] args) {
		while (true) {
			System.out.println(get());
		}
	}

	/**
	 * <pre>
	 * 根据给定前六位生成卡号
	 * </pre>
	 */
	public String generateByPrefix(Integer prefix) {
		Random random = new Random(System.currentTimeMillis());
		String bardNo = prefix + StringUtils.leftPad(random.nextInt(999999999) + "", 9, "0");

		char[] chs = bardNo.trim().toCharArray();
		int luhnSum = getLuhnSum(chs);
		char checkCode = luhnSum % 10 == 0 ? '0' : (char) (10 - luhnSum % 10 + '0');
		return bardNo + checkCode;
	}

	/**
	 * 根据银行名称 及银行卡类型生成对应卡号
	 *
	 * @param bankName 银行名称
	 * @param cardType 银行卡类型
	 * @return 银行卡号
	 */
	public String generate(BankNameEnum bankName, BankCardTypeEnum cardType) {
		Integer[] candidatePrefixes = null;
		if (cardType == null) {
			candidatePrefixes = bankName.getAllCardPrefixes();
		} else {
			switch (cardType) {
				case DEBIT:
					candidatePrefixes = bankName.getDebitCardPrefixes();
					break;
				case CREDIT:
					candidatePrefixes = bankName.getCreditCardPrefixes();
					break;
				default:
			}
		}

		if (candidatePrefixes == null || candidatePrefixes.length == 0) {
			throw new RuntimeException("没有该银行的相关卡号信息");
		}

		Integer prefix = candidatePrefixes[new Random().nextInt(candidatePrefixes.length)];
		return generateByPrefix(prefix);
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhn 校验算法获得校验位
	 * 该校验的过程：
	 * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
	 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，则将其减去9），再求和。
	 * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
	 */
	private int getLuhnSum(char[] chs) {
		int luhnSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhnSum += k;
		}
		return luhnSum;
	}

	/**
	 * <p>
	 * 银行卡类型枚举类
	 * </p>
	 */
	enum BankCardTypeEnum {
		/**
		 * 借记卡/储蓄卡
		 */
		DEBIT("借记卡/储蓄卡"),
		/**
		 * 信用卡/贷记卡
		 */
		CREDIT("信用卡/贷记卡");

		private final String name;

		BankCardTypeEnum(String name) {
			this.name = name;
		}
	}

	/**
	 * <p>
	 * 常见银行名称枚举类
	 * </p>
	 */
	enum BankNameEnum {
		ICBC("102", "中国工商银行", new Integer[]{622200, 622230, 622235, 622210, 622215}),
		BOC("104", "中国银行", new Integer[]{622752}),
		CCB("105", "中国建设银行", new Integer[]{622280}),
		BCOM("301", "交通银行", new Integer[]{622260}),
		CITIC("302", "中信银行", new Integer[]{622690}),
		CEB("303", "中国光大银行", new Integer[]{622655,622650,622658}),
		HXB("304", "华夏银行", new Integer[]{622636, 622637}),
		CMBC("305", "中国民生银行", new Integer[]{622600,622601,622602,622603}),
		CGB("306", "广东发展银行", new Integer[]{622568}),
		PAB("307", "平安银行", new Integer[]{622155,622156}),
		CMB("308", "招商银行", new Integer[]{622578,622576,622581,439228,628262,628362,628362,628262}),
		;

		/**
		 * 银行代码
		 */
		private final String code;

		/**
		 * 银行名称
		 */
		private final String name;

		/**
		 * 银行简称
		 */
		private String abbrName;

		/**
		 * 信用卡卡号前缀数组
		 */
		private Integer[] creditCardPrefixes;

		/**
		 * 借记卡卡号前缀数组
		 */
		private Integer[] debitCardPrefixes;

		/**
		 * 所有卡号前缀数组
		 */
		private Integer[] allCardPrefixes;

		BankNameEnum(String code, String name) {
			this.code = code;
			this.name = name;
		}

		BankNameEnum(String code, String name, String abbrName) {
			this.code = code;
			this.name = name;
			this.abbrName = abbrName;
		}

		BankNameEnum(String code, String name, String abbrName, Integer[] debitCardPrefixes,
				Integer[] creditCardPrefixes) {
			this.code = code;
			this.name = name;
			this.abbrName = abbrName;
			this.creditCardPrefixes = creditCardPrefixes;
			this.debitCardPrefixes = debitCardPrefixes;

			this.allCardPrefixes = (Integer[]) ArrayUtils.addAll(this.creditCardPrefixes, this.debitCardPrefixes);
		}

		BankNameEnum(String code, String name, Integer[] debitCardPrefixes) {
			this.code = code;
			this.name = name;
			this.debitCardPrefixes = debitCardPrefixes;
			this.allCardPrefixes = debitCardPrefixes;
		}

		BankNameEnum(String code, String name, Integer[] debitCardPrefixes, Integer[] creditCardPrefixes) {
			this.code = code;
			this.name = name;
			this.creditCardPrefixes = creditCardPrefixes;
			this.debitCardPrefixes = debitCardPrefixes;

			this.allCardPrefixes = (Integer[]) ArrayUtils.addAll(this.creditCardPrefixes, this.debitCardPrefixes);
		}

		public String getName() {
			return this.name;
		}

		public String getAbbrName() {
			return this.abbrName;
		}

		public Integer[] getCreditCardPrefixes() {
			return this.creditCardPrefixes;
		}

		public Integer[] getDebitCardPrefixes() {
			return this.debitCardPrefixes;
		}

		public Integer[] getAllCardPrefixes() {
			return this.allCardPrefixes;
		}

		public String getCode() {
			return this.code;
		}
	}
}


