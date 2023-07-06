package com.yu.math;

import com.yu.math.service.PrimeService;
import org.apache.commons.math3.primes.Primes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PrimeTest {

    @Autowired
    private PrimeService service;

    @Test
    public void testMathPrime(){
        System.out.println(Primes.nextPrime(9));
        List<Integer> integers = Primes.primeFactors(100);
        System.out.println(integers);
    }

    @Test
    public void testIsPrime() {
        int n =1230000751;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            boolean b = service.isPrime(n);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            boolean b1 = Primes.isPrime(n);
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }


}
