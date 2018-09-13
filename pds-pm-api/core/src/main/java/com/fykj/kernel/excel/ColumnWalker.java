package com.fykj.kernel.excel;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:00
 * Description:
 **/
public interface ColumnWalker {

    /**
     * 下一个属性的值
     * next:
     *
     * @return
     * @author   xiongping
     */
    String next();

    /**
     * 是否有下一个值
     * hasNext:
     *
     * @return
     * @author   xiongping
     */
    boolean hasNext();
}
