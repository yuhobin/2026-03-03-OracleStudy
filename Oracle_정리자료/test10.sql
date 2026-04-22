-- 2026-04-17 179page DDL : 데이터 정의어
/*
  SQL 
	==> DML 단위가 ROW 
	DML : 데이터 조작 (처리) => SELECT : 데이터 검색
						INSERT : 데이터 추가
						UPDATE : 데이터 수정
						DELETE : 데이터 삭제 ===> CRUD
	==> DDL 단위가 COLUMN
	DDL : 데이터 정의어 => CREATE : 생성
					  TABLE = 데이터 저장하는 공간***
					  SEQUENCE = 자동 증가 번호 (게시판 => 번호) 
					  VIEW = 가상 테이블 => SELECT 저장
					  INDEX = 검색 최적화 / 정렬
					  FUNCTION / PROCEDURE / TRIGGER : 5장
						==> AutoCommit
				  	  ALTER : 추가 / 수정 / 삭제
						    ADD, MODIFY, DROP
					  DROP : 전체 삭제
					  RENAME : 테이블 이름 변경 = 리팩토링
					  TRUNCATE : 삭제 => 테이블은 유지
								=> 데이터만 삭제 => 잘라내기
	= 테이블 만드는 방법 => 식별자 
	   -------- class =>  멤버변수 : column
	   -------- row => 객체
		1) 문자로 시작한다 (알파벳, 한글) : 운영체제 문제 (한글 => 읽는 형식다름)
										    ---------------------------
										AWS 서버 : 리눅스 = 호스팅
			** 대소문자 구분이 없다 (오라클 자체에 저장 => 대문자로 저장)
			** user_tables에 저장
		2) table명, column명 => 문자의 개수 : 30byte 까지 (한글 10글자)
			==> 7자~15자 (예 freeboard, good_list, cart) : 약간의 의미
			==> column 이름을 table명을 사용해도 된다
		3) 같은 데이터베이스(XE) 에서는 table명은 유일해야 한다
							     -----------------
		4) 키워드를 사용 금지 ; SELECT, ORDER, BY ...
		5) 숫자 사용이 가능 (앞에 사용 금지)
		6) 특수문자 사용이 가능 ( _ , $ ) => _ :
			FileName => file_name
		형식)
			CREATE TABLE table_name (
				컬럼명 데이터형 [제약조건],
					  ---------   -----------
				------- 식별자 동일
				컬럼명 데이터형 [제약조건],
				컬럼명 데이터형 [제약조건],
				[제약조건]
				[제약조건]
			);
		
	1. 오라클에서 지원하는 데이터형
		1) 문자형 : 문자, 문자열 저장 =====> 자바 : String
			CHAR
				= 고정바이트
				= 1~2000byte 까지 사용
				= 일정한 값 ( 남자 / 여자, 우편번호...)
				= 성별 / (user/admin) => y/n
				= 한글 => 3byte
				= CHAR(10) => 'y'
				 -----------------------------
				 y \0 \0 .......
				------------------------------ 메모리 누수
			VARCHAR2
				= 가변 바이트 : 입력한 글자수 만큼 메모리 할당
				= 1~4000byte
				= 문자저장 중에 가장 많이 사용되는 데이터형
				*** 반드시 바이트 수 지정
				= VARCHAR2(100) : 이름, 주소, 전화번호 ... 이미지 (http)	
			CLOB
				= 가변 바이트 
				= 4GA
				= 글자가 많은 경우 : 줄거리, 자기소개, 게시판 ...
				------------------------ 자바에서는 String과 매칭

		2) 숫자형 : 정수, 실수      =======> 자바 : int, double
			NUMBER ======> default NUMBER(8,2) ==> NUMBER(38,128)
							정수 : 8자리 사용이 가능
							실수 : 6자리 => 소수점 2자리
							NUMBER(2,1)
							0~99 / 5.0 4.9
			NUMBER(10) ====> 10자리까지 사용
			NUMBER(10,2) ===> 10자 중에 => 소수점으로 2자리 사용이 가능
			=> int / double 
	
		3) 날짜형 : DATE => SYSDATE ====> java.util.Date
			DATE, TIMESTAMP
				=> 기록경주
			날짜 저장 => 문자형식으로 저장 : yy/mm/dd

		4) 기타 => 이미지, 동영상... : 증명사진 => XE : tablespace => 정해져 있다
			BFILE / BLOB
			------   ------
				  4GA => binary 형식으로 저장 => InputStream
			4GA => File 형식으로 저장
		------------------------------------------------------------------------------------
		자바 매핑 => ~VO
		CHAR, VARCHAR2, CLOB => String
		NUMBER => int, double
		DATE => java.util.Date
		BFile / BLob => InputStream

	2. 데이터 유지 = 제약조건 
		= 정형화된 데이터  : 규격에 맞게 저장 => 바로 사용이 가능
						구분이  잘되어 있다 => 데이터베이스
		= 반정형화된 데이터 : 구분이 된 데이터 => XML, HTML, JSON => 크롤링
		= 비정형화된 데이터 : 규칙도 없고 구분도 없는 데이터 : 트위터, facebook
		   ---------------------> 분석 ==> 필요한 데이터만 정형화된 데이터로 변경
						---------------------------------------------------------
						데이터 수집 => 분석 => 정형화 => 학습...
		= 웹사이트에 출력된  데이터는 정형화된 데이터다
		   ---------- 데이터베이스에서 출력
		
		제약조건
			반드시 입력값을 필요로 하는 경우
			1) NOT NULL
				name VARCHAR2(20) NOT NULL
			중복이 없는 값 추가 => null 허용
	
			2) UNIQUE
				=> email / phone : 후보키
			3) NOT NULL + UNIQUE
				=> ROW구분자. 숫자(자동처리), ID	
				=> https://211.249.220.24/index.html
				=> https://daum.net/index.html
				PRIMARY KEY : 데이터 무결성 => 테이블 제작 시 반드시 1개 이상 추가
			4) 다른 테이블과 연결 : 외래키 / 참조키
				FOREIGN KEY => 반드시 PRIMARY KEY
			5) 지정된 데이터만 출력
				CHECK : radio / select (콤보) => 부서명 / 근무지 / 장르...
			6) default : 미리 데이터 설정
				regdate DATE DEFAULT, SYSDATE
				hit NUMBER DEFAULT 0...
			=> 컬럼에서 제약조건을 한개만 사용하지 않고 여러개 사용이 가능
			=> 테이블 안에서 PRIMARY KEY 한개 설정이 가능, 나중에 설정 

	제약 조건
		 NOT NULL
			=> 컬럼 데이터형 NOT NULL
			=> 컬럼 데이터형 CONSTRAINT 제약조건명 NOT NULL : 유지보수의 문제
				=> 테이블에 값이 없는 경우
		UNIQUE
			=> 컬럼 데이터형 UNIQUE
			=> 컬럼 데이터형, 
				CONSTRAINT 제약조건 명 UNIQUE(컬럼명)
		CHECK
			=> 컬럼 데이터형 CHECK(컬럼명, IN(...))
			=> 컬럼 데이터형, 
				CONSTRAINT 제약조건 명 CHECK(컬럼명,IN(...))
		PRIMARY KEY
			=> 컬럼 데이터형 PRIMARY KEY
			=> 컬럼 데이터형,
				CONSTRAINT 제약조건명 PRIMARY KEY(컬럼명)

		FOREIGN KEY
			=> 컬럼 데이터형,
				CONSTRAINT  제약조건명 FOREIGN KEY(컬럼명)
				REFERENCES 참조 테이블 (참조컬럼)
		DEFAULT 
			=> 컬럼 데이터형 DEFAULT 값
				     ----------
					VARCHAR2(1~4000), CLOB, CHAR(1~2000)
					NUMBER, NUMBER(1,2) => 8,2
					DATE
		형식
			CREATE TABLE table_name (
				컬럼명 데이터형 [제약조건] [제약조건] ... , => NOT NULL, DEFAULT
				컬럼명 데이터형 [제약조건] [제약조건] ... ,
				컬럼명 데이터형 [제약조건] [제약조건] ... ,
				[제약조건], => PK, UK, CK, FK
				[제약조건]
			);		

*/
CREATE TABLE aaa(
	name VARCHAR2(10) CONSTRAINT aaa_name_nn NOT NULL DEFAULT '홍길동'
);