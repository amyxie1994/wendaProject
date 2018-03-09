/**
 * Created by amyxie in 2018
 * SensitiveWordService.java
 * 22 Feb. 2018
 */
package com.example.snsProject.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.snsProject.controller.HomeController;

/**
 * @author amyxie
 *
 */

@Service
public class SensitiveWordService  implements InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final String DEFAULT_REPLACEMENT = "敏感词";

	
	private class TrieNode{
		
		private boolean end;
		
		private Map<Character, TrieNode> subTree = new HashMap<Character,TrieNode>();
		
		public void addNode(Character key, TrieNode node) {
			
			subTree.put(key, node);
			
		}
		
		public TrieNode getNode(Character key) {
			
			return subTree.get(key);
			
		}
		
		public boolean isKeyWord(Character key) {
			return subTree.get(key).end;
		}
		
		public void setKeywordEnd(boolean end) {
            this.end = end;
        }
		
		
		
		
		public int subTreeSize() {
			return subTree.size();
		}
		
	}
	
	private TrieNode rootNode = new TrieNode();
	
	 @Override
	 public void afterPropertiesSet() throws Exception {
		 
		 rootNode = new TrieNode();
		 
		 try {
			 InputStream is = Thread.currentThread().getContextClassLoader()
	                    .getResourceAsStream("SensitiveWords.txt");
	         InputStreamReader read = new InputStreamReader(is);
	         BufferedReader bufferedReader = new BufferedReader(read);
	         
	         String line;
	         while((line= bufferedReader.readLine())!=null) {
	        	 		line = line.trim();
	                addWord(line);
	         }
	         read.close();
			 
		 }
		 catch(Exception e) {
			 logger.error("read sensitive file fails"+e.getMessage());
		 }
	 }
	 /*
	 private boolean isSymbol(char c) {
	        int ic = (int) c;
	        // 0x2E80-0x9FFF 东亚文字范围
	       // return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
	   }*/
	 
	 public String filter(String text) {
		if(StringUtils.isEmpty(text))
			return text;
		String replacement = DEFAULT_REPLACEMENT;
        StringBuilder result = new StringBuilder();
        
        //int index=0,postion = 0;
        for(int i = 0;i<text.length();i++) {
        		TrieNode temp = rootNode;
        		
        		for(int j = i;j<text.length();j++) {
        			Character c = text.charAt(j);
        			if(temp.getNode(c)==null) {
        				result.append(text.charAt(i));
        				break;
        			}
        			else {
        				if(temp.isKeyWord(c)) {
        					result.append(replacement);
        					i = j;
        					break;
        				}
        				temp = temp.getNode(c);
        				
        			}
        				
        		}
        	
        }
        
        return result.toString();
		 
		 
	 }
	 
	 private void addWord(String line) {
		
		 TrieNode node = rootNode;
		 for(int i = 0;i<line.length();i++) {
			 Character c = line.charAt(i);
			 
			 TrieNode cur = node.getNode(c);
			 if(cur == null) {
				 cur = new TrieNode();
				 node.addNode(c,cur);
			 }
			 
			 node = cur;
			 
			 if(i == line.length()-1)
				 node.setKeywordEnd(true);
				 
		 }
	 }
	 /*
	 public static void main(String[] argv) {
	        SensitiveWordService s = new SensitiveWordService();
	        s.addWord("色情");
	        s.addWord("好色");
	        System.out.print(s.filter("你好X色情XX"));
	    }
	
	*/
}
 