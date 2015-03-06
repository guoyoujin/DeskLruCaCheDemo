package com.example.disklrucachedemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import libcore.io.DiskLruCache;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {
	DiskLruCache mDiskLruCache = null;
	public  Context context= null;
	private ImageView img_show,img_show_cache,img_delete_cache;
	private Button btn_downLoad,btn_show_cache,btn_delete_cache;
	public String FILE_NAME="bitmap";
	public String str[]={"http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context=getApplication();
		initView();
		initFileName(FILE_NAME);
		startThreadDonwoldFile(str[0]);
		
	}
	/**
	 * 初始化view
	 */
	public void initView(){
		img_show=(ImageView)findViewById(R.id.img_show);
		img_show_cache=(ImageView)findViewById(R.id.img_show_cache);
		img_delete_cache=(ImageView)findViewById(R.id.img_delete_cache);
		btn_downLoad=(Button)findViewById(R.id.btn_downLoad);
		btn_downLoad.setOnClickListener(this);
		btn_show_cache=(Button)findViewById(R.id.btn_show_cache);
		btn_show_cache.setOnClickListener(this);
		btn_delete_cache=(Button)findViewById(R.id.btn_delete_cache);
		btn_delete_cache.setOnClickListener(this);
	}
	/**
	 * Hanlder线程下载完成发送通知接收类
	 */
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				setImage(img_show);
				break;

			default:
				break;
			}
		};
	};
	
	/**
	 * 
	 * 点击事件
	 * @param arg0
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_downLoad:
			startThreadDonwoldFile(str[0]);
			break;
		case R.id.btn_show_cache:
			setImage(img_show_cache);
			break;
		case R.id.btn_delete_cache:
			removeCache(str[0]);
			setImage(img_show_cache);
			break;
		default:
			break;
		}
		
	}  
	
	/**
	 * 初始化缓存文件
	 */
	public void initFileName(String fileName){
		try {  
		    File cacheDir = getDiskCacheDir(context, fileName);  
		    if (!cacheDir.exists()) {  
		        cacheDir.mkdirs();  
		    }  
		    mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024); 
		} catch (IOException e) {  
		    e.printStackTrace();  
		}
	}
	/**
	 * 下载文件请求网络
	 */
	public void startThreadDonwoldFile(final String url){
		new Thread(new Runnable() {  
		    @Override  
		    public void run() {  
		        try {  
		            String imageUrl = url;  
		            String key = hashKeyForDisk(imageUrl);  
		            DiskLruCache.Editor editor = mDiskLruCache.edit(key);  
		            if (editor != null) {  
		                OutputStream outputStream = editor.newOutputStream(0);  
		                if (downloadUrlToStream(imageUrl, outputStream)) {  
		                    editor.commit();  
		                } else {  
		                    editor.abort();  
		                }  
		            }  
		            mDiskLruCache.flush();
		            Message msg=new Message();
		            msg.what=0;
		            handler.sendMessage(msg);
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        }  
		    }  
		}).start();  
	}
	/**
	 * 读取本地的缓存
	 * @param imageView1
	 */
	public void setImage(ImageView imageView1){
		try {  
		    String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";  
		    String key = hashKeyForDisk(imageUrl);  
		    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);  
		    if (snapShot != null) {  
		        InputStream is = snapShot.getInputStream(0);  
		        Bitmap bitmap = BitmapFactory.decodeStream(is);  
		        imageView1.setImageBitmap(bitmap);  
		    }else{
		    	imageView1.setImageResource(R.drawable.ic_launcher);
		    }
		} catch (IOException e) {  
		    e.printStackTrace();  
		}  
	}
	public void removeCache(String url){
		try {  
		    String key = hashKeyForDisk(url);    
		    mDiskLruCache.remove(key);  
		} catch (IOException e) {  
		    e.printStackTrace();  
		}  
	}

	/**
	 * 下载文件
	 * @param urlString
	 * @param outputStream
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {  
	    HttpURLConnection urlConnection = null;  
	    BufferedOutputStream out = null;  
	    BufferedInputStream in = null;  
	    try {  
	        final URL url = new URL(urlString);  
	        urlConnection = (HttpURLConnection) url.openConnection();  
	        in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);  
	        out = new BufferedOutputStream(outputStream, 8 * 1024);  
	        int b;  
	        while ((b = in.read()) != -1) {  
	            out.write(b);  
	        }  
	        return true;  
	    } catch (final IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (urlConnection != null) {  
	            urlConnection.disconnect();  
	        }  
	        try {  
	            if (out != null) {  
	                out.close();  
	            }  
	            if (in != null) {  
	                in.close();  
	            }  
	        } catch (final IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return false;  
	} 
	/**
	 * md5文件名加密
	 * @param key
	 * @return
	 */
	public String hashKeyForDisk(String key) {  
	    String cacheKey;  
	    try {  
	        final MessageDigest mDigest = MessageDigest.getInstance("MD5");  
	        mDigest.update(key.getBytes());  
	        cacheKey = bytesToHexString(mDigest.digest());  
	    } catch (NoSuchAlgorithmException e) {  
	        cacheKey = String.valueOf(key.hashCode());  
	    }  
	    return cacheKey;  
	}  
	  
	private String bytesToHexString(byte[] bytes) {  
	    StringBuilder sb = new StringBuilder();  
	    for (int i = 0; i < bytes.length; i++) {  
	        String hex = Integer.toHexString(0xFF & bytes[i]);  
	        if (hex.length() == 1) {  
	            sb.append('0');  
	        }  
	        sb.append(hex);  
	    }  
	    return sb.toString();  
	}  
		
	/**
	 * 获取缓存地址，注意必须得添加权限
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	public File getDiskCacheDir(Context context, String uniqueName) {  
	    String cachePath;  
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) {  
	        cachePath = context.getExternalCacheDir().getPath();  
	    } else {  
	        cachePath = context.getCacheDir().getPath();  
	    }  
	    return new File(cachePath + File.separator + uniqueName);  
	}
	/**
	 * 获取apk版本号
	 * @param context
	 * @return
	 */
	public int getAppVersion(Context context) {  
	    try {  
	        PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);  
	        return info.versionCode;  
	    } catch (NameNotFoundException e) {  
	        e.printStackTrace();  
	    }  
	    return 1;  
	}

}
