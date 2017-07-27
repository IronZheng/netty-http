package com.rsmser.common.socket;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 发送消息的异步结果
 *
 * @author zhenggm
 * @version 1.0
 */
public class SendMsgFuture {
    private volatile boolean finish;
    private volatile boolean success;
    private volatile String msg;
    private volatile String casue;
    private volatile int count = -1;


    private Lock lock;
    private Condition condition;

    public SendMsgFuture() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public boolean isFinish() {
        return finish;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public String getCasue() {
        return casue;
    }

    public void sync() throws InterruptedException {
        lock.lock();
        try {
            if (!finish) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void sync(long time, TimeUnit unit) throws InterruptedException {
        lock.lock();
        try {
            if (!finish) {
                if (!condition.await(time, unit)) {
                    throw new RuntimeException("超时,等待时间:" + time + "单位:" + unit);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void success(String msg) {
        lock.lock();
        try {
            finish = true;
            success = true;
            this.msg = msg;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void success(int count) {
        lock.lock();
        try {
            finish = true;
            success = true;
            this.count = count;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void fail(String msg, String casue) {
        lock.lock();
        try {
            finish = true;
            success = false;
            this.msg = msg;
            this.casue = casue;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (success) {
            if (count >= 0) {
                sb.append(count);
            }
            if (StringUtils.isNotEmpty(msg)) {
                sb.append(msg);
            }
        } else {
            if (StringUtils.isNotEmpty(msg)) {
                sb.append("MSG:").append(msg);
            }
            if (StringUtils.isNotEmpty(casue)) {
                sb.append("错误:").append(casue);
            }
        }
        return sb.toString();
    }
}
