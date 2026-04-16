/*
	JOIN 주의점 
	1. 테이블 두개 필요한 데이터 추출 (조인)
	2. 컬럼명이 동일 / 같은 값을 가지고 있는 경우
	3. 컬럼명이 동일시에는 반드시 테이블.컬럼, 별칭.컬럼
						  ------------------------- 없으면 애매한 정의
	4. NATURAL JOIN, JOIN ~ USING : 같은 컬럼명이 존재, NON-EQUI-JOIN에서 사용이 불가능
	5. 조인 조건 
		=> 같은 데이터를 가지고 있어야 
			영화 = 맛집
				| 지역
			멜론 = 지니뮤직 
				| 곡명 / 가수명
		=> SELECT 문장에서 사용이 가능 
		=> 오라클 / ANSI 조인
			FROM  테이블1, 테이블2
			WHERE 조인 조건 

			FROM 테이블1, JOIN 테이블2
			ON 조인 조건

			=> 조인 조건외 다른 조건이 있는 경우 AND
	6. 한쪽에만 데이터가 존재할때는 => OUTER JOIN 
	7. 데이터 연결 추출 
	-----------------------------------------------------------------
	SQL 문장 여러개 연결 => 서브쿼리 (170 page)
	=> 웹 개발자가 많이 사용하는 문장
	=> 이유 : 네트워크 통신 
			-------------- 전송 1번 , 수신 1번
	=> DML 전체에서 사용이 가능
	    -------------------------------- INSERT / UPDATE / DELETE
	=> 종류 
		1. WHERE 뒤에 일반 서브쿼리  --------------------> 조건문 
		2. SELECT 뒤에 스칼라 서브쿼리*** (JOIN 대체) --> 컬럼
		3. FROM 뒤에 인라인뷰*** -------------------------> 테이블
				   ----------- 페이징

	=> WHERE 뒤에 일반 서브쿼리 
		MainQuery
		WHERE (SubQuery)
			   -------------1 => 결과값을 MainQuery로 전송 => 결과값 출력
		SQL + SQL = 서브쿼리
		데이터 + 데이터 = JOIN 
*/
-- 급여가 전체 평균보다 작은 사원의 정보
-- 1. 평균 구하기 
-- 2. 조건 구하기 
-- 3. SELECT ~ WHERE sal = (평균)
/*
	교재 ------------------------------------
	book : 책정보 ----------> 위키북스 
		bookid : 책 구분자 => 중복이 없다 => PRIMARY KEY
		bookname : 책 제목 
		publisher : 출판사 
		price : 가격
	customer : 회원 --------> 100명 
		custid : id
		name 
		address 
		phone 
	orders : 구매 현황 -----> 직접 저장 
		orderid : 고유번호
		custid
		bookid
		saleprice : 구매 금액
		orderdate : 구매일
	------------------------------------------
	쇼핑몰 회원 가입
	-------------------- 10000개 레시피 store

	1. 가장 비싼 가격의 책제목 출력
	   ------------------   -------
	
		SELECT MAX(price) FROM book;
		SELECT bookname FROM book WHERE price = 35000;
		SELECT booknaem FROM book  WHERE price = (SELECT MAX(price) FROM book);
										| 실행 35000
									price = 35000

		테이블 여러개  = 데이터 통합 : 조인
		SQL 여러개 = SQL 통합 : 서브쿼리

	EXIST : => 값이 있는지 여부 체크 => 속도가 빠르다
	서브쿼리는 테이블로 사용된다
	
*/
/*
SELECT empno, ename, job, hiredate, sal, rownum
FROM emp;
*/
-- EXISTS => ROW 가 존재하는지 
SELECT *
FROM emp e
WHERE EXISTS (
	SELECT 1 FROM emp WHERE deptno=10
);


-- EXISTS는 같은 조건에 맞으면 ROW의 결과를 포함
/*
	IN => 값 비교
	IN(
    	SELECT DISTINCT custid
    	FROM orders)
	
	EXISTS => 존재여부 체크 (성능 최적화) => 조건이 만족 => TRUE => 다음 문장을 수행하지 않는다
	EXISTS (
    	SELECT *
    	FROM orders os
    	WHERE cs.custid=os.custid)

	단일행 서브쿼리  => 비교 연산자 주로 사용 ( =, !=, <, >, <=, >=)
				=> 기준값 한개를 비교할 때 사용 (서브쿼리 결과가 1개)
	다중행 서브쿼리  => 결과가 여러개인 경우
					IN, ANY, ALL, MAX, MIN
					집합 데이터 처리 

	인라인뷰 / 스칼라 서브쿼리
	FROM(SELECT~) => 보안
	SELECT (SELECT~) => 조인 대신

	
					
	 
	
*/
