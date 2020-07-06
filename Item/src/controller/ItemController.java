package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ItemService;
import service.ItemServiceImpl;

@WebServlet({ "/", "/item/*" })
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//서비스 인스턴스 참조 변수
	private ItemService itemService;
	
    public ItemController() {
        super();
        
        itemService = ItemServiceImpl.sharedInstance();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//공통된 부분을 제거한 주소를 만듭니다.
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String command = 
			requestURI.substring(contextPath.length());
		//요청이 여기까지 도달하는지 확인
		//여기에 도달하지 않으면 Controller 처리 패턴이 잘못되었거나
		//링크 설정을 잘못한 것입니다.
		//command를 아래 제어문에서 처리하는지 확인
		//command 와 아래 처리하지 않는 URL이 일치하지 않으면 처리가 안됨
		//일치하지 않으면 요청 페이지의 URL을 변경하던지
		//처리구문의 URL을 변경하던지 해야 합니다.
		System.out.println("1:" + command);
		
		//전송방식을 저장
		String method = request.getMethod();
		
		//시작 요청이 온 경우 index.jsp 페이지로 포워딩
		if(command.equals("/")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}else if(command.equals("/item/list")) {
			//전체 데이터를 가져오는 서비스 메소드를 호출
			itemService.list(request, response);
			//결과 페이지로 이동
			//현재 요청이 /item/list 이므로
			//../view/list.jsp 이면
			//WebContent/view/list.jsp 가 됩니다.
			RequestDispatcher dispatcher = request.getRequestDispatcher("../view/list.jsp");
			dispatcher.forward(request, response);
		}
		else if(command.equals("/item/itemlist")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("../view/itemlist.jsp");
			dispatcher.forward(request, response);
		}
		else if(command.equals("/item/insert") && method.equals("GET")) {
			//입력 페이지로 이동
			RequestDispatcher dispatcher = request.getRequestDispatcher("../view/insert.jsp");
			dispatcher.forward(request, response);
		}else if(command.equals("/item/insert") && method.equals("POST")) {
			//삽입을 처리
			itemService.insert(request, response);
			//삽입하고 결과 페이지로 이동
			//작업을 수행했으므로 목록보기로 리다이렉트
			response.sendRedirect("itemlist");
		}else if(command.indexOf("/item/detail") >= 0) {
			//여기까지 오는지 확인 - 1번은 출력되고 이것은 출력안되면
			//요청 페이지의 요청 URL과 비교하는 URL 수정
			System.out.println("2:" + command);
			//상세보기를 처리
			itemService.detail(request, response);
			//결과 페이지로 이동
			//결과 페이지에 jsp 파일이 제대로 생성되었는지 확인
			RequestDispatcher dispatcher = request.getRequestDispatcher("../../view/detail.jsp");
			dispatcher.forward(request, response);
		}else if(command.indexOf("/item/update") >= 0&& method.equals("GET")) {
			//상세보기를 처리
			itemService.detail(request, response);
			//결과 페이지로 이동
			//결과 페이지에 jsp 파일이 제대로 생성되었는지 확인
			RequestDispatcher dispatcher = request.getRequestDispatcher("../../view/update.jsp");
			dispatcher.forward(request, response);
		}else if(command.indexOf("/item/update") >= 0&& method.equals("POST")) {
			//데이터 수정을 처리
			itemService.update(request, response);
			//결과 페이지로 이동
			response.sendRedirect("../itemlist");
		}else if(command.indexOf("/item/delete") >= 0) {
			//데이터 삭제를 처리
			itemService.delete(request, response);
			//결과 페이지로 이동
			response.sendRedirect("../itemlist");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
