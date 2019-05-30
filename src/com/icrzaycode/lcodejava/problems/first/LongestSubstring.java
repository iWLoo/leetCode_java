package com.icrzaycode.lcodejava.problems.first;

import java.util.HashMap;

/**
 * @author ybh on 2019/5/29.
 */
public class LongestSubstring {
    /**
     * 无重复字符的最长子串_0003_M
     * Longest Substring Without Repeating Characters (无重复字符的最长子串)
     *
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * 示例 1:
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * 示例 2:
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     *
     * 示例 3:
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     */

    /**
     * 初步方案一，提交leetcode用时165ms 击败16.97%提交者，血崩啊，有没有
     * 老规矩，还是先说解题思路
     * 1、保证循环一次
     * 2、每次添加一个字符的时候先看当前字符串里有没有该字符，用HashMap判断
     * 3、当出现重复字符的时候，将循环的下标重置到重复字符第一次的下标前，再进行循环并且清空map
     * 注：每次的长度计算，实际上都是在map中计算，即出现了重复的时候来会被计算，所以要考虑到，如果一个字符串中没有重复字符的情况，即边界要考虑到
     *
     * 举例说明：abcabcbb
     * 1、拿到a，b，c，都放到map中
     * 2、拿到a，map中已经有a，map长度比较
     * 3、此刻i=3，第二个a的位置，将i设为1，即从b开始循环，清空map
     * （这个做法就是最sb的，b和c已经循环过了，并且不重复，竟然还要再循环一遍，这样才有了方法二的优化）
     *
     * 对于"最后一次子串长度的比较"的说明，比如字符串"aabcdef"，建议debug一下。
     * @param s
     * @return
     */
    private static int lengthOfLongestSubstring01(String s) {
        int finalMaxLength = 0;
        if(s!=null && s.length()>0){
            char[] oldStr = s.toCharArray();
            //用map.size()来计算长度
            HashMap<Character,Integer> tempLongestMap = new HashMap<>(16);
            for(int i=0; i<oldStr.length; i++){
                if(tempLongestMap.containsKey(oldStr[i])){
                    //当一组连续的字串遇到的下一个字符已经存在，当前的子串即为当前最大
                    if(tempLongestMap.size()>finalMaxLength){
                        finalMaxLength = tempLongestMap.size();
                    }
                    /**
                     * 将循环的i重置为上一次出现这个字符的位置的下一个位置进行循环
                     */
                    i = tempLongestMap.get(oldStr[i]);
                    tempLongestMap.clear();
                }else {
                    //找到当前最大子串，判断长度
                    tempLongestMap.put(oldStr[i],i);
                }
            }
            //最后一次子串长度的比较
            if(tempLongestMap.size()>finalMaxLength){
                finalMaxLength = tempLongestMap.size();
            }
        }

        return finalMaxLength;
    }

    /**
     * 优化方案二：36 ms，击败63.70%，虽然也不是很完美，但是已经进步了有木有
     * 知道了第一次多次重复循环的地方，就有了方案二的优化
     * 举例说明：abcacbcbb
     * 1、拿到a，b，c，都放到map中
     * 2、下标=3，取到a时，计算当前字符串长度。int thisLength = (i-1)-thisStartIndex+1; 比较长度。
     *    (i-1)：a是发现重复的，所以截止位置是i-1，起始位置为thisLength，计算有多少个，最好还要+1。
     * 3、更新完最长之后，更新thisStartIndex。int repeatIndex = tempLongestMap.get(oldStr[i]);
     *    方案一计算到这，是清空map，下标从第一次a的下一个再开始循环
     *    方案二，针对清空map，只是此时的字符串的结果，重复字符之前所有的，有点绕？
     *      比如abca，发现了重复a，那么得到的第一个字符串是abc，长度是a。接下来是bcac，所以得到的第二个字符串是bca，长度还是3。
     *      但是接下来的字符串就是acbc。对于第二次，删掉了bc，a作为下一次字符串的起始位置。
     *      针对"下标从第一次a的下一个再开始循环"，i并没有往前移动，一直是循环的，长度控制用thisStartIndex作为开始位置。
     * 4、所以每次比较完长度后，都要更新thisStartIndex的位置。
     * 5、还是边界的问题，引入boolean checkLast来做判断。
     *
     * 对于这个方案，是目前来说我能想到最好的，循环是肯定要循环n次，
     * 本机上测试，当字符串的长度为1000时，也是5ms以下，不确定leetcode的时间是怎么判断出来的。
     * 而且因为数据过大的时候，map的put和remove上也会多少浪费些时间。
     *
     * 以作者目前的水平，这个是能想到的最优的。leetcode上还有2ms的示例，待我研究研究
     * @param s
     * @return
     */
    private static int lengthOfLongestSubstring02(String s) {
        int finalMaxLength = 0;
        if(s!=null && s.length()>0){
            char[] oldStr = s.toCharArray();
            //只是用来判断，是否有重复的字符出现and下标
            HashMap<Character,Integer> tempLongestMap = new HashMap<>(16);
            //当前字符串开始位置
            int thisStartIndex = 0;
            //校验最后边界问题
            boolean checkLast = false;
            for(int i=0; i<oldStr.length; i++){
                if(tempLongestMap.containsKey(oldStr[i])){
                    int thisLength = (i-1)-thisStartIndex+1;
                    //发现了更长的
                    if(thisLength > finalMaxLength){
                        finalMaxLength = thisLength;
                    }
                    //更新完最长之后，更新thisStartIndex
                    int repeatIndex = tempLongestMap.get(oldStr[i]);
                    //删除thisStartIndex到repeatIndex中map中的数据
                    for(int delete=thisStartIndex;delete<=repeatIndex;delete++){
                        tempLongestMap.remove(oldStr[delete]);
                    }
                    //设置新的开始位置
                    thisStartIndex = repeatIndex+1;
                    if(i==oldStr.length-1){
                        checkLast = true;
                    }
                }
                /**
                 * 两个作用
                 * 1、第一次出现字符串，添加到map中
                 * 2、当出现重复字符串，第一次出现的字符串会被删掉，将第二次出现的再放进去
                 */
                tempLongestMap.put(oldStr[i],i);
            }
            if(!checkLast){
                int thisLength = (s.length()-1)-thisStartIndex+1;
                if(thisLength > finalMaxLength){
                    finalMaxLength = thisLength;
                }
            }
        }
        return finalMaxLength;
    }

    public static void main(String[] args) {
        String s01 = "tmmzuxt";
        System.out.println(lengthOfLongestSubstring01(s01));
        System.out.println(lengthOfLongestSubstring02(s01));
//        String s02 = "abcabcbb";
//        System.out.println(lengthOfLongestSubstring01(s02));
//        System.out.println(lengthOfLongestSubstring02(s02));
//        String s03 = "pwwkew";
//        System.out.println(lengthOfLongestSubstring01(s03));
//        System.out.println(lengthOfLongestSubstring02(s03));
//        String s04 = "abcad";
//        System.out.println(lengthOfLongestSubstring01(s04));
//        System.out.println(lengthOfLongestSubstring02(s04));
    }
}
