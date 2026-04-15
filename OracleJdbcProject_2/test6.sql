-- 04-15 오라클 3일차 (내장 - 단일행 함수 / GROUP BY / JOIN)

-- SELECT ( 검색) => 연산자 / 집계함수 / GROUP BY / JOIN / SubQuery
-- DDL (Table, 데이터형) => INSERT / UPDATE / DELETE 			  =====> 여기까지가 3장

/*
	내장함수 : 라이브러리 (오라클에서 지원하는 함수)
			내장함수 
			      |
		-----------------------------------------
		|			|			|			PL/SQL => 5장
	   집계함수		단일행 함수	사용자 정의 CREATE FUNCTION func_name(매개변수..)
	  -----------		--------------
	column단위	row 단위

	집계 함수
	=> row 개수 : count
	=> row 전체 최댓값, 최솟값 : max, min
	=> row의 총합, 평균 : sum, avg
	=> 순위 : rank, dense_rank
	--------------------------------
	=> row 한개 계산 : rollup
	=> row/column : cube
	-------------------------------- GROUP BY가 존재

	단일행 함수
	-------------
		오라클 단점 : 비절차적 => 오류 발생 => 다음줄로 넘어간다
		1. 문자 함수
			= ***LENGTH / LENGTHB => 문자의 개수 / 문자의 바이트 수
			   LENGTH('ABC') ===> 3
			   LENGTH('홍길동') ===> 3
			   LENGTHB('ABC') ===> 3
			   LENGTHB('홍길동') ===> 9 : 한글은 한글자당 byte => 3
			= LPAD / ***RPAD
			   	      ID ====> ad*****
				LPAD(문자열, 글자수, '변경할 문자')
				LPAD('KING',5,'#') ==> #KING
				LPAD('KING',3,'#') ==> KIN
				RPAD(문자열, 글자수, '변경할 문자')
				RPAD('KING',5,'#') ==> KING#
				RPAD('KING',3,'#') ==> KIN

			=***UPPER / LOWER / INITCAP
			  UPPER(문자열) => 대문자 변경
			  UPPER('abc') => ABC
			  LOWER(문자열) => 소문자 변경
			  LOWER('ABC') => abc
			  INITCAP(문자열) => 첫자만 대문자
			  INITCAP('KING') => King
			
			=***REPLACE : 변경 ======> 크롤링 ==> & => ^
			  REPLACE(문자열, 찾는 문자, 변경할 문자)
			  REPLACE('Hello Java', 'a', 'b') => Hello Jbvb
			  REPLACE('Hello Java', 'Java', 'Oracle') => Hello Oracle

			= TRIM : 공백 / 특정 문자 제거 (자바 => 공백만 제거)
			  LTRIM(), RTRIM(), TRIM()
						    | 좌우 제거
			  		| 오른쪽만 제거
			  | 왼쪽만 제거
			   LTRIM('문자열'), LTRIM('문자열', '제거할 문자')
			  | 왼쪽 공백 제거  | 왼쪽 => 문자 제거

			   RTRIM('문자열'), RTRIM('문자열', '제거할 문자')
			  | 오른쪽 공백 제거  | 오른쪽 => 문자 제거
			
			  TRIM : 좌우 공백 제거
			  TRIM('문자열') => 공백 제거
			  TRIM (문자 FROM 문자열) 
					  -------

			= ASCII / CHR
					| 숫자 => 문자변환
			   | 문자 => 숫자변환
			ASCII('A') => 65
			CHR(65) => 'A'

			=*****SUBSTR / ***INSTR / CONCAT
								 | 문자열 결합 => || => "%'||?||'%' => Oracle
										CONCAT('%',?,'%') => MySQL			
						| 문자 위치 (자바에선  indexOf)
			  | 문자열 자르기 (자바에선 subString)
			
			SUBSTR(문자열, 시작위치, 개수) => 문자번호 => 1번부터 시작
			123456789 
			SUBSTR ('123456789', 4, 3) => 456
			SUBSTR ('Oracle', 1, 3) => Ora
			
			INSTR(문자열, 찾을문자, 시작위치, 몇번째) => indexOf
							 ----------- 양수 => indexOf
							 ----------- 음수 => lastIndexOf
			Hello Java
			12345678910
			INSTR('Hello Java', 'a', 1,2)	
						      --- 양수		
			CONCAT : 문자열 결합 => ||
			CONCAT('문자열', '결합될 문자열')
			CONCAT('Hello ','Oracle') => Hello Oracle
			
		***	=> LENGTH : 문자의 개수
			=> SUBSTR : 문자 자르기
			=> INSTR : 문자 번호 검색
			=> RPAD : 문자 채우는 함수
			=> REPLACE : 문자를 변경
			------------------------------------ 웹 개발자 : String

		2. 숫자 함수 (211page) 
			= MOD : % 나머지
			   MOD(10, 2) 10%2

			= CEIL : 올림 ==> 총페이지 구하기
			   CEIL(9.1) => 10
			---------------------------
			= TRUNC : 버림
			   TRUNC(9.8) => 9
			= ROUND : 반올림
			   Round(9.8) => 10
			  
			--------------------------- 날짜 (LONG)
			------------------------------------ 웹 개발자 : Math
			
		3. 날짜 함수 
		    ***	= SYSDATE : 시스템의 날짜 / 시간 => 숫자
			   SYSDATE-1 / SYSDATE+1
			   등록일을 자동 처리
		     ***= MONTHS_BETWEEN (현재, 과거) => 총 개월 수 => 근무 개월수
			= ADD_MONTH ('26/03/03',7) => 금융권 
			적금 : 3년 / 보험...
			= LAST_DAY (SYSDATE) => 30
			= NEXT_DAY (날짜,'요일')
		4. 변환 함수
			TO_CHAR : 숫자, 날짜 => 문자열 반환
			TO_NUMBER : 문자 => 숫자
			TO_DATE : 문자 => 날짜
			VARCHAR2 / DATE / NUMBER
			CHAR 
			CLOB
			Date date='26/04/15';
			
			=> 날짜 변환 / 숫자 변환(1,000) => 9,999,999
				Date를 사용하면 => SimpleDateFormat => 입력시간 : 0000000
			=> 년도
				월 : YY / YYYY
				일 : MM
				시 : HH / HH24
				분 : MI
				초 : SS
				요일 : DAY

			TO_NUMBER('10') => 정수형 변환 => 많이 사용하지않는다
			SELECT '10'+1 FROM DUAL => 11
			=> 가급적 사용하지 않는다  => 속도가 느려지기 때문 => 튜닝 (SQL)
			TO_DATE() => 문자열 => DATE로 변경
			: 생년월일, 예약 날짜 => 문자열 => DATE 

		5. 기타 함수
			NVL / DECODE / CASE
			NVL = null 값이 있는 경우 다른값으로 변경 (대체)
			NVL(comm,0), NVL(bunji,' ')
			      ------- --	  ------ -- 데이터형이 일치 => ''=>(null)  /   ' ' => (문자)
			INSERT INTO member('',null)
			==>  자동 증가 번호
			SELECT NVL(MAX(no)+1,1) FROM board
			
			DECODE : 선택문 / CASE : 다중 조건문
			| 값 1개 지정 	   | 범위 지정  	
		
			CASE : 조건식 다양하게 사용이 가능(연산자) <,>,BETWEEN~AND 
				  => 실무에서 많이 사용되는 문장
				  => TRIGGER
				  => 부서별 보너스 계산
				  => 호봉 / 연차
			CASE
				WHEN 조건 THEN 값
				WHEN 조건 THEN 값
				WHEN 조건 THEN 값
				ELSE 값
			END as 별칭
			
			-------------------------------------------
				DECODE		CASE
			-------------------------------------------
				=			모든 조건 연산
				가독성이 낮다	가독성이 높다
				단순값 비교	복잡한 조건
			-------------------------------------------
			형식 = 순서 = 라이브러리 = 활용
			=> 사용처를 잘 기억해야한다
			=> SQL
				| DQL (데이터 검색) : SELECT
				  SELECT * | coumn_list
				  FROM table_name
				  [
					WHERE ---------------- 연산자 (true / false)
					GROUP BY ------------ 함수
					HAVING --------------- 집계함수
					ORDER BY ------------- 컬럼 / 컬럼 순서
				  ]	
		작동 순서	:  FROM - WHERE - GROUP BY - HAVING - SELECT - ORDER BY

				연산자 
					= 산술 연산자 => ROW단위 통계 (+, -, /, *)
						+ : 덧셈 , / (정수/정수=실수)
					= 비교 연산자 => =, ( !=, <>, ^=),  < , >, <=, >=
						***숫자, 문자, 날짜 => 연산	
					= 논리 연산자 AND, OR, NOT, => !=(X)
					= BETWEEN ~ AND : 기간, 범위
					= IN (OR를 대체)
					=  NULL (연산처리가 안된다) => IS NULL , IS NOT NULL
					= LIKE : _(한글), % (여러개)
						startsWith : A%, endsWith : %A, contains : %A%
			
				함수 
					= 단일행 함수
						문자함수
							LENGTH
							SUNSTR
							INSTR
							RPAD
						숫자함수
							CEIL
							ROUND
							TRUNC
							MOD
						날짜함수
							SYSDATE
							MONTHS_BETWEEN
						변환함수
							TO_CHAR
							TO_DATE
						기타함수

					= 집계 함수
						COUNT => ROW의 개수
						MAX / MIN => COLUMN의 최댓값, 최솟값
						AVG : COLUMN 전체 평균
						SUM : COLUMN 전체의 합
						RANK / DENSE_RANK(순차적) : 순위
		
*/
CREATE TABLE board (
	no NUMBER PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);
