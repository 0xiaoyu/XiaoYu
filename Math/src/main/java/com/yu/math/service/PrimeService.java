package com.yu.math.service;

import java.util.List;

public interface PrimeService {

    /**
     * 判断是否是质数
     * @param prime
     * @return
     */
    boolean isPrime(int prime);

    /**
     * 获取start到end内的所有质数的个数
     * 包括start和end
     * @param start
     * @param end
     * @return
     */
    Integer getAllPrime(int start,int end);

    /**
     * 获取第n个质数的值
     * @param n
     * @return
     */
    Integer getPrimeById(int n);

    /**
     * 返回大于或等于 n 的最小素数。
     * @param n
     * @return
     */
    int nextPrime(int n);

    /**
     * 质因数分解
     * @param n
     * @return
     */
    List<Integer> primeFactors(int n);


}
