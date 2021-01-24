package umb.heartbeatchecker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import umb.heartbeatchecker.worker.HeartBeatCheckInitializeException;
import umb.heartbeatchecker.worker.HeartBeatCheckWorker;
import umb.heartbeatchecker.worker.impl.FileSystemCheckWorker;

public class HeartBeatChecker {

	public static final Logger LOG = LoggerFactory.getLogger(HeartBeatChecker.class);

	private static final String PROP_PATH = "conf/HeartBeatChecker.properties";

	public static void main(String[] args) {
		LOG.info("hoge");

		try {
			HeartBeatCheckWorker worker = new FileSystemCheckWorker();
			worker.init(PROP_PATH);
			worker.start();
		} catch (HeartBeatCheckInitializeException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
