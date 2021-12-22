package com.ks.pointgame.game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	// ランダム数字生成
	public int randNum() {
		int random = (int) (Math.random() * 10); 
		return random;
	}

	// ランダム数字配列を生成する
	public ArrayList<Integer> createRandNum() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			int temp = randNum();
			if (arr.contains(temp)) { // 配列に重複している数がないかチェック
				i--;
			} else {
				arr.add(temp);
			}
		}
		return arr;
	}
	
	// 今日、最初のゲームである場合
	// 自分が入力した数字と隠された数字を比較して新しい配列に入れること
	// 0番目のindexは数字と位置が一致、1番目のindexは数字はあっても位置は異なる、2番目のindexは隠された数字
	public ArrayList<Integer> gameStart(int input) {
		ArrayList<Integer> gameArr = createRandNum();
		int hidedNum = (gameArr.get(0)*100)+(gameArr.get(1)*10)+gameArr.get(2);
		System.out.println("隠れ数字 : "+gameArr.toString());
		int hit = 0;
		int missed = 0;
		ArrayList<Integer> myArr = myNum(input);
		System.out.println("私が入力した数字の配列 : "+myArr.toString());
		for (int i = 0; i < gameArr.size(); i++) {
			for (int j = 0; j < myArr.size(); j++) {
				if (gameArr.get(i) == myArr.get(j) && i == j) {
					hit++;
				} else if (gameArr.get(i) == myArr.get(j) && i != j) {
					missed++;
				}
			}
		}
		System.out.println("精確 : "+hit);
		System.out.println("数の桁が違う : "+missed);
		ArrayList<Integer> hitAndMissed = new ArrayList<Integer>();
		hitAndMissed.add(hit);
		hitAndMissed.add(missed);
		hitAndMissed.add(hidedNum);
		return hitAndMissed;
	}
	
	// 今日、初ゲームじゃない場合
	// 自分が入力した数字と隠された数字を比較して新しい配列に入れること
	// 0番目のindexは数字と位置が一致、1番目のindexは数字はあっても位置は異なる、2番目のindexは隠された数字
	public ArrayList<Integer> gameStart(int input,int hidedNumber) {
		ArrayList<Integer> gameArr = hidedNumInArr(hidedNumber);
		System.out.println("隠れ数字2 : "+gameArr.toString());
		int hidedNum = hidedNumber;
		int hit = 0;
		int missed = 0;
		ArrayList<Integer> myArr = myNum(input);
		System.out.println("私が入力した数字の配列2 : "+myArr.toString());
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
	
	// 私が入力した数字を配列にする
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
	
	// 隠れ数字の配列にする
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
