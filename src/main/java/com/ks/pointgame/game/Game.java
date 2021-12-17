package com.ks.pointgame.game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public int randNum() {
		int random = (int) ((Math.random() * 9) + 1); // 범위 1~9
		return random;
	}

	// 랜덤숫자 배열 생성하는 메소드
	public ArrayList<Integer> createRandNum() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			int temp = randNum();
			if (arr.contains(temp)) {
				i--;
			} else {
				arr.add(temp);
			}
		}
		return arr;
	}

	public ArrayList<Integer> gameStart(int input) {
		ArrayList<Integer> gameArr = createRandNum();
		int hidedNum = (gameArr.get(0)*100)+(gameArr.get(1)*10)+gameArr.get(2);
		System.out.println("숨겨진 숫자"+hidedNum);
		int hit = 0;
		int missed = 0;
		ArrayList<Integer> myArr = myNum(input);
		System.out.println("내가 입력한 숫자 배열 : "+myArr.toString());
		for (int i = 0; i < gameArr.size(); i++) {
			for (int j = 0; j < myArr.size(); j++) {
				if (gameArr.get(i) == myArr.get(j) && i == j) {
					hit++;
				} else if (gameArr.get(i) == myArr.get(j) && i != j) {
					missed++;
				}
			}
		}
		System.out.println("스트라이크 : "+hit);
		System.out.println("볼 : "+missed);
		ArrayList<Integer> hitAndMissed = new ArrayList<Integer>();
		hitAndMissed.add(hit);
		hitAndMissed.add(missed);
		hitAndMissed.add(hidedNum);
		return hitAndMissed;
	}
	
	public ArrayList<Integer> gameStart(int input,int hidedNumber) {
		ArrayList<Integer> gameArr = hidedNumInArr(hidedNumber);
		int hidedNum = hidedNumber;
		System.out.println(hidedNum);
		int hit = 0;
		int missed = 0;
		ArrayList<Integer> myArr = myNum(input);
		System.out.println(myArr.toString());
		for (int i = 0; i < gameArr.size(); i++) {
			for (int j = 0; j < myArr.size(); j++) {
				if (gameArr.get(i) == myArr.get(j) && i == j) {
					hit++;
				} else if (gameArr.get(i) == myArr.get(j) && i != j) {
					missed++;
				}
			}
		}
		System.out.println(hit);
		System.out.println(missed);
		ArrayList<Integer> hitAndMissed = new ArrayList<Integer>();
		hitAndMissed.add(hit);
		hitAndMissed.add(missed);
		hitAndMissed.add(hidedNum);
		return hitAndMissed;
	}

	public ArrayList<Integer> myNum(int input) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int num = input;
		for (int i = 0; i < 3; i++) {
			arr.add(num % 10);
			num /= 10;
		}
		int temp1 = arr.get(2);
		arr.set(2, arr.get(0));
		arr.set(0, temp1);

		return arr;

	}
	
	public ArrayList<Integer> hidedNumInArr(int hidedNum) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int num = hidedNum;
		for (int i = 0; i < 3; i++) {
			arr.add(num % 10);
			num /= 10;
		}
		int temp1 = arr.get(2);
		arr.set(2, arr.get(0));
		arr.set(0, temp1);

		return arr;

	}
}
