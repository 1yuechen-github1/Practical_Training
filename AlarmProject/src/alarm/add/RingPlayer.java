package alarm.add;
/*音乐播放线程类*/

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.alarm.log.AlarmLogger;
import com.alarm.ui.AlarmUI;


public class RingPlayer extends Thread{
	private Logger rLogger = AlarmLogger.getLogger(AlarmUI.class.getName());
	private String fileName;//音乐文件
	private boolean isRun;//播放标志
	
	public RingPlayer(String fileName, boolean isRun) {
		this.fileName=fileName;
		this.isRun = isRun;
	}
	
	public void run() {
		//音乐播放方法
		File musicFile =new File(fileName);
		AudioInputStream audioInputstream = null;//创建音频输入流对象
		try {
			audioInputstream = AudioSystem.getAudioInputStream(musicFile);
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		AudioFormat format =null;//创建音频格式对象
		DataLine.Info info = null;//嵌套DataLine.Info 根据指定格式构造信息对象
	
		//源数据进行对象，使得音频数据可以写入数据行，充当混淆器的源
		SourceDataLine dataline =null;
		try {
			format = audioInputstream.getFormat();
			info = new DataLine.Info(SourceDataLine.class,format);
			dataline = (SourceDataLine)AudioSystem.getLine(info);
			dataline.open(format);//按指定格式打开的源数据行
			dataline.start();//允许数据进行IO操作
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		//从输入流中读取数据发送给混音器
			int readBytes = 0;
			byte[] bufbytes = new byte[1024];
			try {
				while((readBytes !=-1) && isRun)
				{
					readBytes = audioInputstream.read(bufbytes);
					if(readBytes > 0) {
						dataline.write(bufbytes, 0 , readBytes);
					}					
				}
			}catch(IOException e) {
				e.printStackTrace();
				rLogger.log(Level.SEVERE, "音频播放错误");
				return;
			}finally {
				dataline.drain();
				dataline.close();
			}			
		}
	
			//停止线程
			public void close(){
				this.isRun = false;
			}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//	
//	}

}
