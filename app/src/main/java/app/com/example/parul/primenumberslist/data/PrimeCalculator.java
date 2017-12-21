package app.com.example.parul.primenumberslist.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.com.example.parul.primenumberslist.util.PrimeConstants;

/**
 * Created by Parul on 12/19/2017.
 */
public class PrimeCalculator implements PrimeConstants {

    public ArrayList<Integer> getPrimeList(int number) {
        ArrayList<Integer> primeList = new ArrayList<Integer>();
        try {
            primeList.add(2);
            int num = 3;
            int status = 1;
            for (int i = 2; i <= number; ) {
                for (int j = 2; j <= Math.sqrt(num); j++) {
                    if (num % j == 0) {
                        status = 0;
                        break;
                    }
                }
                if (status != 0) {
                    primeList.add(num);
                    i++;
                }
                status = 1;
                num++;
            }

        } catch (Exception e) {
            Log.i(LOG_TAG_PRIMECAL, e.toString());
        }
        return primeList;
    }

    public int getNthPrimeNumber(int number) {
        List<Integer> primeList = new ArrayList<Integer>();
        int nthPrimeNumber=0;
        try {
            primeList = getPrimeList(number);
            nthPrimeNumber = primeList.get(primeList.size() - 1);
        }catch (Exception e) {
            Log.i(LOG_TAG_PRIMECAL, e.toString());
        }
        return nthPrimeNumber;
    }
}