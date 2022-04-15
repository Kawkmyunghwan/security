package com.yedam.app.notice.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*@Getter
@Setter
@ToString*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeVO {
	
	private int id;
	private String title;
	private String content;
	
	@JsonFormat(pattern="yyyy-MM-dd", timezone="Asia/Seoul")	
	private Date wdate;
	private int hit;
	private String fileName;
	private String uuidFile;
}
