package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ItemService {
	//Item 테이블의 모든 데이터를 읽어오는 메소드
	public void list (
		HttpServletRequest request, 
		HttpServletResponse response);
	//Item 테이블에 데이터를 저장하는 메소드
	public void insert (
			HttpServletRequest request, 
			HttpServletResponse response);
	//상세보기 처리를 위한 메소드
	public void detail (
			HttpServletRequest request, 
			HttpServletResponse response);
	//수정 처리를 위한 메소드
	public void update (
				HttpServletRequest request, 
				HttpServletResponse response);
	//삭제 처리를 위한 메소드
	public void delete (
				HttpServletRequest request, 
				HttpServletResponse response);
}
