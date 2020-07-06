package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import domain.Item;


public class ItemDao {
	
	//데이터베이스 연동에 필요한 변수
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private ItemDao() {
		//드라이버 클래스 로드
		//한번만 수행하면 되기 때문에 생성자에 작성
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static ItemDao itemDao;
	
	public static ItemDao sharedInstance() {
		if(itemDao == null) {
			itemDao = new ItemDao();
		}
		return itemDao;
	}
	
	//연결 메소드와 해제 메소드
	//연결과 해제는 모든 곳에서 사용이 되는 부분이므로 중복해서 
	//코딩하지 않을려고 별도의 메소드로 생성
	//이 메소드는 코드의 중복을 회피할려고 만든 메소드이므로 
	//private 으로 생성해서 외부에서 호출하지 못하도록 생성
	private void connect() {
		try {
			/*
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/sample?useUnicode=true&characterEncoding=utf8",
				"root","*******");
			*/
			
			//DataBase Connection Pool을 이용하는 방식
			//미리 데이터베이스 연결을 만들어두고 사용
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup(
					"java:comp/env/DBConn");
			con = ds.getConnection();

		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void close() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//전체 데이터를 조회하는 메소드
	public List<Item> list(){
		System.out.println("3");
		//List를 리턴할 때는 null 이 리턴될 수 없도록 만들어야 합니다.
		//반복문에 바로 사용했을 때 NullPointerException이 발생하지 않음
		//데이터가 없는 경우의 구분은 size 가 0인지 확인
		List<Item> list = new ArrayList<Item>();
		connect();
		//예외 처리를 하지 않아도 되는 구문이더라도 개발을 할 때는
		//하는 것이 좋습니다.
		//예외가 발생했을 때 발생한 지점이나 오류를 빨리 해석할 수 있기 때문입니다.
		try {
			//SQL을 작성 - 입력받는 데이터는 ?로 작성
			pstmt = con.prepareStatement(
				"select * from item");
			//필요한 파라미터 매핑 - ?가 있으면 매핑
			
			//SQL 실행 - select
			rs = pstmt.executeQuery();
			//여러 행이 리턴되면 while - 1개의 행이 리턴되면 if
			//이 작업도 프레임워크를 사용하면 하지 않음
			while(rs.next()) {
				//List의 1개의 행 객체 생성
				Item item = new Item();
				//값 채워넣기
				item.setCode(rs.getInt("code"));
				item.setCategory(rs.getString("category"));
				item.setTitle(rs.getString("title"));
				//리스트에 삽입
				list.add(item);
			}
			
			//결과 사용
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		close();
		//리턴 값을 출력
		System.out.println(list);
		return list;
	}
	
	//가장 큰 글번호 찾아오는 메소드
	public int maxCode(){
		int result = 0;
		connect();
		try {
			//SQL을 생성
			pstmt = con.prepareStatement(
				"select max(code) from item");
			//SQL을 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("max(code)");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		close();
		return result;
	}
	
	public int insert(Item item) {
		//-1로 초기화해서 -1이 리턴되면 작업 실패
		int result = -1;
		connect();
		try {
			pstmt = con.prepareStatement(
				"insert into item(code, category, title, description) "
				+ "values(?,?,?,?)");
			//?에 값을 바인딩
			pstmt.setInt(1, item.getCode());
			pstmt.setString(2,  item.getCategory());
			pstmt.setString(3, item.getTitle());
			pstmt.setString(4, item.getDescription());
			
			//SQL 실행
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		close();
		return result;
	}

	//상세보기를 위한 메소드
	public Item detail(int code) {
		//이 구문이 안 보이면 메소드 호출을 잘못한 것 - Service에서
		//메소드 이름을 확인
		System.out.println("5:" + "DAO 호출");
		//파라미터 출력 - 파라미터가 제대로 넘어오는지 확인
		//이 데이터가 이상하면 ServiceImpl에서 수정
		System.out.println("code:" + code);
		Item item = null;
		connect();
		
		try {
			//sql 생성
			pstmt = con.prepareStatement(
				"select * from item where code = ?");
			pstmt.setInt(1, code);
			
			//SQL 실행
			rs = pstmt.executeQuery();
			
			//SQL을 만들고 확인
			//이 구문이 안나오면 예외가 발생
			//SQL이 잘못되었거나 ?에 값을 제대로 바인딩하지 않음
			//실행 전이나 후나 별 상관없음
			System.out.println("6:" + "SQL 확인");
			
			//하나의 행이 나오는 쿼리 결과 처리
			if(rs.next()) {
				item = new Item();
				item.setCode(rs.getInt("code"));
				item.setTitle(rs.getString("title"));
				item.setCategory(rs.getString("category"));
				item.setDescription(rs.getString("description"));
			}
			//이 구문이 안나오면 컬럼 이름이 틀렸거나 형 변환 실패
			//String으로 변환하는 것은 무조건 가능
			System.out.println("7:" + "SQL 결과 확인");
			//위의 구문에서 문제가 생기지는 않지만 많이 실수하는 부분은
			//list로 리턴하는 경우 마지막에 list.add 를 호출하지 
			//않아서 데이터가 나오지 않는 경우가 발생
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		close();
		return item;
	}

	public int update(Item item) {
		//-1로 초기화해서 -1이 리턴되면 작업 실패
		int result = -1;
		connect();
		try {
			pstmt = con.prepareStatement(
				"update item set title=?, "
				+ "description=? where code=?");
			//?에 값을 바인딩
			pstmt.setString(1, item.getTitle());
			pstmt.setString(2, item.getDescription());
			pstmt.setInt(3, item.getCode());
			
						
			//SQL 실행
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		close();
		return result;
	}
	
	//데이터 삭제를 위한 메소드
	public int delete(int code) {
		int result = -1;
		connect();
		try {
			pstmt = con.prepareStatement(
				"delete from item where code = ?");
			pstmt.setInt(1,  code);
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		close();
		return result;
	}
	
	//페이징과 더보기 구현을 위한 전체 데이터 개수를 세는 메소드
	public int getCount() {
		//기본 데이터 개수는 0
		int result = 0;
		connect();
		
		try {
			//SQL을 생성
			pstmt = con.prepareStatement(
				"select count(*) from item");
			//SQL을 실행
			rs = pstmt.executeQuery();
			//결과를 저장
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		close();
		return result;
	}
	
	//페이지 번호와 페이지당 데이터 개수를 입력받아서
	//페이지 번호에 해당하는 데이터만 리턴해주는 메소드
	public List<Item> list(
			int pageno, int perpagecnt){
		List<Item> list = new ArrayList<Item>();
		connect();
		try {
			pstmt = con.prepareStatement(
				"select * from item limit ?,?");
			//데이터 시작번호는 페이지번호-1 에 데이터개수를 곱한것
			pstmt.setInt(1, (pageno-1)*perpagecnt);
			//가져올 데이터 개수는 페이지당 출력 할 데이터 개수
			pstmt.setInt(2,  perpagecnt);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Item item = new Item();
				item.setCode(rs.getInt("code"));
				item.setCategory(rs.getString("category"));
				item.setTitle(rs.getString("title"));
				
				list.add(item);
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		close();
		return list;
	}
}






