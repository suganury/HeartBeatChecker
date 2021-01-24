package umb.heartbeatchecker.worker.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import umb.heartbeatchecker.worker.HeartBeatCheckException;
import umb.heartbeatchecker.worker.HeartBeatCheckInitializeException;
import umb.heartbeatchecker.worker.HeartBeatCheckWorker;

public class FileSystemCheckWorker extends HeartBeatCheckWorker {

	public static final Logger LOG = LoggerFactory.getLogger(FileSystemCheckWorker.class);

	private static final String PROP_TARGETS = "Targets";

	private static final String TARGET_PATH_DELIM = ",";

	private static final String TOUCH_FILE = "touch";

	private List<File> TargetList = new ArrayList<>();

	@Override
	public void init(String confPath) throws HeartBeatCheckInitializeException {

		Properties prop = new Properties();

		try {
			prop.load(new FileReader(confPath));

			for(String path : prop.getProperty(PROP_TARGETS).split(TARGET_PATH_DELIM)) {
				TargetList.add(new File(path + "\\" + TOUCH_FILE));
			}

		} catch (Exception e) {
			throw new HeartBeatCheckInitializeException(e);
		}
	}

	@Override
	protected void check() throws HeartBeatCheckException {
		try {
			for(File file : TargetList) {
				if (!file.exists()){
					file.createNewFile();
				} else {
					file.setLastModified(System.currentTimeMillis());
				}
			}
		} catch (IOException e) {
			throw new HeartBeatCheckException(e);
		}
	}
}
