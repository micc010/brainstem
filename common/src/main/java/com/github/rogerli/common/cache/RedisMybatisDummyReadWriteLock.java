/**
 * @文件名称： RedisMybatisDummyReadWriteLock.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/22
 * @作者 ： Roger
 */
package com.github.rogerli.common.cache;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Roger
 * @create 2016/12/22 11:04
 */
public class RedisMybatisDummyReadWriteLock implements ReadWriteLock {

    private Lock lock = new RedisMybatisDummyReadWriteLock.DummyLock();

    public Lock readLock() {
        return lock;
    }

    public Lock writeLock() {
        return lock;
    }

    static class DummyLock implements Lock {

        public void lock() {
            // Not implemented
        }

        public void lockInterruptibly() throws InterruptedException {
            // Not implemented
        }

        public boolean tryLock() {
            return true;
        }

        public boolean tryLock(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
            return true;
        }

        public void unlock() {
            // Not implemented
        }

        public Condition newCondition() {
            return null;
        }
    }
}
