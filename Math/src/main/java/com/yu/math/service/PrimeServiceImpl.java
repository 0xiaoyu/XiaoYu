package com.yu.math.service;


import org.apache.commons.math3.primes.Primes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimeServiceImpl implements PrimeService {

    @Override
    public boolean isPrime(int prime) {
        if (prime < 4)
            return prime > 1;
        if (prime % 6 != 1 && prime % 6 != 5) {
            return false;
        }
        if (prime >= 10000)
        for (int i = 5; i <= Math.sqrt(prime); i += 6) {
            if (prime % i == 0 || prime % (i + 2) == 0)
                return false;
        }
        return true;
    }

    @Override
    public Integer getAllPrime(int start, int end) {
        if (start > end) {
            //log.error("getAllPrime方法参数错误");
            return -1;
        }
        int count = 0;
        if (start % 2 == 0)
            start++;
        while (start <= end) {
            if (this.isPrime(start)) {
                count++;
            }
            start += 2;
        }
        return count;
    }

    @Override
    public Integer getPrimeById(int n) {
        if (n <= 0) {
            //log.error("getPrimeById方法参数为负数或为0");
            return -1;
        }
        if (n == 1)
            return 2;
        int i = 3;
        int count = 1;
        while (count < n) {
            if (isPrime(i)){
                count++;
            }
            i++;
        }
        return i;
    }

    @Override
    public int nextPrime(int n) {
        return Primes.nextPrime(n);
    }

    @Override
    public List<Integer> primeFactors(int n) {
        return Primes.primeFactors(n);
    }
}
