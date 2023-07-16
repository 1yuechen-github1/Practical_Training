

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlay {	
	private String fileName;
		
	public MusicPlay(String fileName){
		this.fileName = fileName;		
	}
		
	public void mPlay(){
		File musicFile = new File(fileName);
		//创建音频输入流对象
		AudioInputStream audioInputstream = null;
		try{
			audioInputstream = AudioSystem.getAudioInputStream(musicFile); 
		}catch(UnsupportedAudioFileException ue){				
			ue.printStackTrace();
			return;
		}catch(IOException ioe){				
			ioe.printStackTrace();
			return;
		}
			
		AudioFormat format = null;//创建音频格式对象
		DataLine.Info info = null;//嵌套类DataLine.Info 根据指定格式构造信息对象
		SourceDataLine dataline = null;//源数据行，使得音频数据可入写入该数据行，充当混频器的源
		try{
			format = audioInputstream.getFormat();
			info = new DataLine.Info(SourceDataLine.class, format);
			dataline = (SourceDataLine)AudioSystem.getLine(info);
			dataline.open(format);//按指定格式打开的数据行
			dataline.start();	//允许数据行进行IO操作		
		}catch(Exception e){
			e.printStackTrace();				
			return;
		}
		
		//从输入流中读取数据发送到混音器		
		int readbytes = 0;
		byte[] bufbytes = new byte[1024];
		try{
			while((readbytes = audioInputstream.read(bufbytes)) != -1){									
				dataline.write(bufbytes, 0, readbytes);//利用源数据行将音频数据写入混频器									
			}
		}catch(IOException e){
			e.printStackTrace();				
			return;			
		}finally{
			//清空数据缓冲,并关闭输入
			dataline.drain();
			dataline.close();
		}		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MusicPlay mp = new MusicPlay("soundDownload\\alarm0.wav");
		mp.mPlay();
	}
}
