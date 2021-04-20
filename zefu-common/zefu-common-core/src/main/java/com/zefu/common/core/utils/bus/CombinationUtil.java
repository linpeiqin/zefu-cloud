package com.zefu.common.core.utils.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
   *  组合算法
 */
public class CombinationUtil {

    /**
     * 组合算法，本算法是递归算法，比如数组长度是5，怎会分别返回数组长度是1,2,3,4,5的所有下表组合
     * @param src int[] 数组下标数组
     * @return 返回可能组合的列表，每个list就是一个可能的组合
     * */
    public static Map<List<Integer>, Boolean> alg(int[] src){
        int nCnt = src.length;
        int nBit = (0xFFFFFFFF >>> (32 - nCnt));
        Map<List<Integer>, Boolean> map = new HashMap<>();
        for (int i = 1; i <= nBit; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    // System.out.print(str[j]);
                    list.add(j);
                }
            }
            map.put(list, true);
        }
        return map;
    }
}
