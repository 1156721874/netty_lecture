package com.ceaser.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by Administrator on 2017/8/6.
 */
public class NioTest10 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("niofiles/NioTest10.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock fileLock = fileChannel.lock(3, 6, true);

        System.out.println("valid : " + fileLock.isValid());
        System.out.println("lock type : " + fileLock.isShared());
        fileLock.release();
        randomAccessFile.close();
    }

    public void writeProcess(){
        RandomAccessFile randomAccessFile = null;
        FileChannel channel = null;

        try {
            randomAccessFile = new RandomAccessFile("test.txt", "rw");
            channel = randomAccessFile.getChannel();
            FileLock lock = null;

            while (null == lock) {
                try {
                    lock = channel.lock();
                } catch (Exception e) {
                    System.out.println("Write Process : get lock failed");
                }
            }

            System.out.println("Write Process : get lock");

            for (int i = 0; i < 30; i++) {
                randomAccessFile.writeByte(i);
                System.out.println("Write Process : write " + i);

                Thread.sleep(1000);
            }

            lock.release();
            System.out.println("Write Process : release lock");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != randomAccessFile) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != channel) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readProcess() throws IOException {
        RandomAccessFile randomAccessFile = null;
        FileChannel channel = null;

        try {
            Thread.sleep(10000);

            randomAccessFile = new RandomAccessFile("test.txt", "rw");
            channel = randomAccessFile.getChannel();
            FileLock lock = null;

            while (true) {
                lock = channel.tryLock();

                if (null == lock) {
                    System.out.println("Read Process : get lock failed");
                    Thread.sleep(1000);
                } else {
                    break;
                }
            }

            System.out.println("Read Process : get lock");

            System.out.println("Read Process : get " + randomAccessFile.length() + " numbers");

            lock.release();
            System.out.println("Read Process : release lock");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != randomAccessFile) {
                randomAccessFile.close();
            }
            if (null != channel) {
                channel.close();
            }
        }
    }

}

