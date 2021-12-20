package com.ks.pointgame;

import java.util.ArrayList;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int num = 46;
		for (int i = 0; i < 3; i++) {
			arr.add(num % 10);
			num /= 10;
		}
		int temp1 = arr.get(2);
		arr.set(2, arr.get(0));
		arr.set(0, temp1);
		
		System.out.println(arr.toString());
	}

}
