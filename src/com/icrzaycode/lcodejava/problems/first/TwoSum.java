package com.icrzaycode.lcodejava.problems.first;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ybh on 2019/5/28.
 */
public class TwoSum {
    /**
     * 两数之和_0001_E
     *
     * 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
     * 你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用
     *
     * 示例：
     * 给定 array=[1,7,11,15] , target=9
     * array[0]+array[1]=9
     * 返回[0,1]
     *
     * 思路：用hashMap实现
     * 步骤：
     * 1、将数组中的数字放到hashMap中，key为值，value为数组下标
     * 2、遍历数组，找到当前值的剩余值是否在hashMap中，并且下标不能为当前下标
     *      比如，数组为[2,5,8]
     *      目标值为10的话，即找到2和8，避免找出两次5
     * 3、找到结果返回
     *
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     */
    private static int[] getTwoSumIndex(int[] nums,int target){
        int[] indexArray = new int[2];
        //准备数据，将所有的数值都放到map中。
        Map<Integer,Integer> valueIndex = new HashMap<>(16);
        for(int i=0;i<nums.length;i++){
            valueIndex.put(nums[i],i);
        }
        //遍历数组，看看是否有符合的值
        for(int i=0;i<nums.length;i++){
            //当前的值
            int nowValue = nums[i];
            //距离目标值剩下的值
            int otherValue = target - nowValue;
            if(valueIndex.containsKey(otherValue) && !valueIndex.get(otherValue).equals(i)){
                //map中有剩下的值，并且不是当前值，说明找到了
                indexArray[0] = i;
                indexArray[1] = valueIndex.get(otherValue);
                return indexArray;
            }
        }
        return indexArray;
    }

    public static void main(String[] args) {
        int[] array = new int[]{2,5,11,15};
        int target = 16;
        int[] resultIndex = getTwoSumIndex(array,target);
        System.out.println(resultIndex[0]);
        System.out.println(resultIndex[1]);
    }
}
