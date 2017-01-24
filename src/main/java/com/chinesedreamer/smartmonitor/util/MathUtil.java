package com.chinesedreamer.smartmonitor.util;

import java.util.List;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class MathUtil {
	public static int trimmeanAverage(List<Integer> nums) {
		int total = 0;
		int min = 0;
		int max = 0;
		for (Integer num : nums) {
			total += num;
			if (min == 0) {
				min = num;
			}
			if (max == 0) {
				max = num;
			}
			if (num > max) {
				max = num;
			}
			if (num < min) {
				min = num;
			}
		}
		
		total = total - min - max;
		return total / (nums.size() - 2);
	}
	
	public static int getMbyte(Long byteSize) {
		Long kb = byteSize / 1024;
		Long mb = kb / 1024;
		return mb.intValue();
	}
	
	public static int getGbyte(Long byteSize) {
		Long kb = byteSize / 1024;
		Long mb = kb / 1024;
		Long gb = mb /1024;
		return gb.intValue();
	}
}
