package umb.heartbeatchecker.worker;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HeartBeatCheckWorker extends Thread {

	public static final Logger LOG = LoggerFactory.getLogger(HeartBeatCheckWorker.class);

	private String StopFile = "stop";

	public void init(String confPath) throws HeartBeatCheckInitializeException {

	}


    @Override
    public void run() {

    	boolean loop = true;

    	while(loop) {

    		if(Files.exists(Paths.get(StopFile))) {
    			LOG.info("Stopファイルを発見したため、チェック処理を停止します。");
    			loop = false;
    		} else {
    			LOG.info("チェック処理を開始します。");
    			try {
					check();
				} catch (HeartBeatCheckException e) {
	    			LOG.info("チェック処理処理中に例外が発生しました。", e);
	    			loop = false;
				}
    		}

    		try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
    			LOG.info("割り込みが発生したため、チェック処理を停止します。");
    			loop = false;
			}
    	}
    }

    protected abstract void check() throws HeartBeatCheckException;
}
